package noctem.storeService.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.global.enumeration.OrderStatus;
import noctem.storeService.purchase.domain.entity.Purchase;
import noctem.storeService.purchase.domain.repository.PurchaseRepository;
import noctem.storeService.store.domain.entity.OrderRequest;
import noctem.storeService.store.domain.repository.RedisRepository;
import noctem.storeService.store.domain.repository.StoreRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
public class FromPurchaseKafkaConsumer {
    private final String PURCHASE_TO_STORE_TOPIC = "purchase-to-store";
    private final RedisRepository redisRepository;
    private final PurchaseRepository purchaseRepository;
    private final StoreRepository storeRepository;

    @KafkaListener(topics = PURCHASE_TO_STORE_TOPIC)
    public void purchaseConsume(String purchaseId) {
        long longPurchaseId = Long.parseLong(purchaseId);
        log.info("Receive purchaseId through [{}] TOPIC", PURCHASE_TO_STORE_TOPIC);
        // redis 주문 상태 저장
        redisRepository.getSetOrderStatus(longPurchaseId, OrderStatus.NOT_CONFIRM);
        // redis 최초 주문요청된 시간 저장
        redisRepository.setOrderRequestTime(longPurchaseId);
        // purchase DB에서 주문정보 조회
        Purchase purchase = purchaseRepository.findById(longPurchaseId).get();
        // mysql에 주문요청 저장
        OrderRequest.builder()
                .purchaseId(longPurchaseId)
                .orderStatus(OrderStatus.NOT_CONFIRM)
                .build()
                .linkToStoreFromOwner(storeRepository.findById(purchase.getStoreId()).get());
        // 매장에 푸시알림
        // 유저에 푸시알림
        // redis에 예상시간 추가. 메뉴 1개당 +90초
        redisRepository.increaseWaitingTime(purchase.getStoreId(), purchase.getPurchaseMenuList().size());
    }
}
