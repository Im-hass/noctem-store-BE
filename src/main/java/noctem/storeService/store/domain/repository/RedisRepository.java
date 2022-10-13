package noctem.storeService.store.domain.repository;

public interface RedisRepository {
    Integer increaseWaitingTime(Long storeId);

    Integer decreaseWaitingTime(Long storeId);

    Integer getWaitingTime(Long storeId);
}
