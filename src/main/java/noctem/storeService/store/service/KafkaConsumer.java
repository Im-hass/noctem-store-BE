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
import noctem.storeService.store.dto.vo.PurchaseResultVo;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
public class KafkaConsumer {
    private final String PURCHASE_TO_STORE_TOPIC = "purchase-to-store";
    private final RedisRepository redisRepository;
    private final PurchaseRepository purchaseRepository;
    private final StoreRepository storeRepository;

    @KafkaListener(topics = PURCHASE_TO_STORE_TOPIC)
    public void purchaseConsume(String purchaseResultVoToString) {
        PurchaseResultVo purchaseResultVo;
        try {
            purchaseResultVo = AppConfig.objectMapper().readValue(purchaseResultVoToString, PurchaseResultVo.class);
        } catch (JsonProcessingException e) {
            log.warn("JsonProcessingException in purchaseConsume");
            throw CommonException.builder().errorCode(5006).httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        log.info("Receive purchaseId={} through [{}] TOPIC", purchaseResultVo.getPurchaseId(), PURCHASE_TO_STORE_TOPIC);
        // redis 주문 상태 저장
        redisRepository.setOrderStatus(purchaseResultVo.getPurchaseId(), OrderStatus.NOT_CONFIRM);
        // redis 최초 주문요청된 시간 저장
        redisRepository.setOrderRequestTime(purchaseResultVo.getPurchaseId());
        // purchase DB에서 주문정보 조회
        Purchase purchase = purchaseRepository.findById(purchaseResultVo.getPurchaseId()).get();
        // mysql에 주문요청 저장
        Store store = storeRepository.findById(purchase.getStoreId()).get();
        store.linkToOrderRequest(
                OrderRequest.builder()
                        .purchaseId(purchaseResultVo.getPurchaseId())
                        .orderStatus(OrderStatus.NOT_CONFIRM)
                        .build()
        );
        // redis에 예상시간 추가. 메뉴 1개당 +90초
        redisRepository.increaseWaitingTime(purchase.getStoreId(), purchase.getPurchaseMenuList().size());
        // 매장에 푸시알림
        // 유저에 푸시알림
        log.info("Kafka consume process done. purchaseId={} through [{}] TOPIC", purchaseResultVo.getPurchaseId(), PURCHASE_TO_STORE_TOPIC);
    }
}
