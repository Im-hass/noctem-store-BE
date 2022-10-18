package noctem.storeService.store.domain.repository;

import noctem.storeService.global.enumeration.OrderStatus;

public interface RedisRepository {
    Long increaseWaitingTime(Long storeId, Integer orderQty);

    Long decreaseWaitingTime(Long storeId, Integer orderQty);

    Long getWaitingTime(Long storeId);

    String getOrderStatus(Long purchaseId);

    void setOrderStatus(Long purchaseId, OrderStatus orderStatus);

    String getSetOrderStatus(Long purchaseId, OrderStatus orderStatus);

    void setOrderRequestTime(Long purchaseId);

    String getOrderRequestTime(Long purchaseId);
}
