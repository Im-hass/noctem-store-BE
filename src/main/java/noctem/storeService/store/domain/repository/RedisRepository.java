package noctem.storeService.store.domain.repository;

import noctem.storeService.global.enumeration.OrderStatus;

import java.time.LocalDateTime;

public interface RedisRepository {
    String getOrderStatus(Long purchaseId);

    void setOrderStatus(Long purchaseId, OrderStatus orderStatus);

    String getSetOrderStatus(Long purchaseId, OrderStatus orderStatus);

    void setOrderRequestTime(Long purchaseId, LocalDateTime dateTime);

    String getOrderRequestTime(Long purchaseId);

    Long getPurchaseIdOrderInProgress(Long userAccountId);

    void delOrderInProgress(Long userAccountId);
}
