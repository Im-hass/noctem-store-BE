package noctem.storeService.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseKafkaConsumer {
    private final String PURCHASE_TO_STORE_TOPIC = "purchase-to-store";

    @KafkaListener(topics = PURCHASE_TO_STORE_TOPIC, groupId = "purchase-to-store")
    public void purchaseConsume(Long purchaseId) {
        // DB에서 주문정보 조회
        // 매장에 푸시알림
        // 유저에 푸시알림
        // redis에 확인중 상태저장
        // redis에 예상시간 +90초
    }
}
