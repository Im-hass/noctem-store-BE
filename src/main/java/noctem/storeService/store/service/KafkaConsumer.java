package noctem.storeService.store.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.AppConfig;
import noctem.storeService.global.common.CommonException;
import noctem.storeService.global.enumeration.OrderStatus;
import noctem.storeService.purchase.domain.entity.Purchase;
import noctem.storeService.purchase.domain.repository.PurchaseRepository;
import noctem.storeService.store.domain.entity.OrderRequest;
import noctem.storeService.store.domain.entity.Store;
import noctem.storeService.store.domain.repository.RedisRepository;
import noctem.storeService.store.domain.repository.StoreRepository;
import noctem.storeService.store.dto.vo.PurchaseFromUserVo;
import noctem.storeService.store.dto.vo.PurchaseToStoreVo;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
public class KafkaConsumer {
    private final String PURCHASE_TO_STORE_TOPIC = "purchase-to-store";
    private final String PURCHASE_FROM_USER_TOPIC = "purchase-from-user-alert";
    private final RedisRepository redisRepository;
    private final PurchaseRepository purchaseRepository;
    private final StoreRepository storeRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = PURCHASE_TO_STORE_TOPIC)
    public void purchaseConsume(String purchaseResultVoToString) {
        try {
            PurchaseToStoreVo vo = AppConfig.objectMapper().readValue(purchaseResultVoToString, PurchaseToStoreVo.class);
            log.info("Receive purchaseId={} through [{}] TOPIC", vo.getPurchaseId(), PURCHASE_TO_STORE_TOPIC);
            // purchase DB에서 주문정보 조회
            Purchase purchase = purchaseRepository.findById(vo.getPurchaseId()).get();
            // mysql에 주문요청 저장
            Store store = storeRepository.findById(purchase.getStoreId()).get();
            LocalDateTime now = LocalDateTime.now();
            store.linkToOrderRequest(
                    OrderRequest.builder()
                            .purchaseId(vo.getPurchaseId())
                            .orderRequestDttm(now)
                            .orderStatus(OrderStatus.NOT_CONFIRM)
                            .build()
            );
            // redis 주문 상태 저장 => 주문취소관련 동시성이슈 회피하기위한 데이터
            redisRepository.setOrderStatus(vo.getPurchaseId(), OrderStatus.NOT_CONFIRM);
            // 유저의 현재 진행중인 주문에 추가
            redisRepository.setOrderInProgress(purchase.getUserAccountId(), purchase.getId());
            // redis 최초 주문요청된 시간 저장
            redisRepository.setOrderRequestTime(purchase.getId(), now);
            // 매장 알림 서버에 전송
            kafkaTemplate.send(PURCHASE_FROM_USER_TOPIC,
                    AppConfig.objectMapper().writeValueAsString(new PurchaseFromUserVo(vo.getStoreId(), vo.getMenuFullName(), vo.getTotalMenuQty(), purchase.getStoreOrderNumber(), purchase.getUserAccountId(), purchase.getId())));
            log.info("Send totalMenuQty through [{}] TOPIC", PURCHASE_FROM_USER_TOPIC);
            log.info("Kafka consume process done. purchaseId={} through [{}] TOPIC", vo.getPurchaseId(), PURCHASE_TO_STORE_TOPIC);
        } catch (JsonProcessingException e) {
            log.warn("JsonProcessingException in purchaseConsume");
            throw CommonException.builder().errorCode(5006).httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
