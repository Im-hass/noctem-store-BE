package noctem.storeService.store.domain.repository;

import noctem.storeService.global.enumeration.OrderStatus;

public interface RedisRepository {
    String getOrderStatus(Long purchaseId);

    void setOrderStatus(Long purchaseId, OrderStatus orderStatus);

    String getSetOrderStatus(Long purchaseId, OrderStatus orderStatus);

    void setOrderRequestTime(Long purchaseId);

    String getOrderRequestTime(Long purchaseId);

    void setOrderInProgress(Long userAccountId, Long purchaseId);

    Long getPurchaseIdOrderInProgress(Long userAccountId);

    void delOrderInProgress(Long userAccountId);
}
