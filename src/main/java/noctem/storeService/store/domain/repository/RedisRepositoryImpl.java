package noctem.storeService.store.domain.repository;

import lombok.RequiredArgsConstructor;
import noctem.storeService.global.enumeration.OrderStatus;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/***
 * waiting time 단위: second
 */
@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {
    private final String WAITING_TIME_KEY_PREFIX = "waitingTime";
    private final String ORDER_STATUS_KEY_PREFIX = "orderStatus";
    private final String ORDER_REQUEST_TIME_KEY_PREFIX = "orderRequestTime";
    private final RedisTemplate<String, Long> redisLongTemplate;
    private final RedisTemplate<String, String> redisStringTemplate;

    public Long increaseWaitingTime(Long storeId, Integer orderQty) {
        String key = String.format("%s:%d", WAITING_TIME_KEY_PREFIX, storeId);
        redisLongTemplate.opsForValue().increment(key, orderQty * 90L);
        return redisLongTemplate.opsForValue().get(key);
    }

    public Long decreaseWaitingTime(Long storeId, Integer orderQty) {
        String key = String.format("%s:%d", WAITING_TIME_KEY_PREFIX, storeId);
        redisLongTemplate.opsForValue().decrement(key, orderQty * 90L);
        return redisLongTemplate.opsForValue().get(key);
    }

    public Long getWaitingTime(Long storeId) {
        String key = String.format("%s:%d", WAITING_TIME_KEY_PREFIX, storeId);
        return redisLongTemplate.opsForValue().get(key);
    }

    public String getOrderStatus(Long purchaseId) {
        String key = String.format("%s:%d", ORDER_STATUS_KEY_PREFIX, purchaseId);
        return redisStringTemplate.opsForValue().get(key);
    }

    @Override
    public void setOrderStatus(Long purchaseId, OrderStatus orderStatus) {
        String key = String.format("%s:%d", ORDER_STATUS_KEY_PREFIX, purchaseId);
        redisStringTemplate.opsForValue().set(key, orderStatus.getValue());
    }

    public String getSetOrderStatus(Long purchaseId, OrderStatus orderStatus) {
        String key = String.format("%s:%d", ORDER_STATUS_KEY_PREFIX, purchaseId);
        return redisStringTemplate.opsForValue().getAndSet(key, orderStatus.getValue()); // getSet deprecated됨
    }

    // redis 최초 주문요청된 시간 저장
    @Override
    public void setOrderRequestTime(Long purchaseId) {
        String key = String.format("%s:%d", ORDER_REQUEST_TIME_KEY_PREFIX, purchaseId);
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        redisStringTemplate.opsForValue().set(key, time);
    }

    @Override
    public String getOrderRequestTime(Long purchaseId) {
        String key = String.format("%s:%d", ORDER_REQUEST_TIME_KEY_PREFIX, purchaseId);
        return redisStringTemplate.opsForValue().get(key);
    }
}
