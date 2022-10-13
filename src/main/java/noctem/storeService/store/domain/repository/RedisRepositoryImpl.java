package noctem.storeService.store.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/***
 * waiting time 단위: second
 */
@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {
    private final String WAITING_TIME_KEY_PREFIX = "waitingTime";
    private final RedisTemplate<String, Integer> redisTemplate;

    public Integer increaseWaitingTime(Long storeId) {
        String key = String.format("%s:%d", WAITING_TIME_KEY_PREFIX, storeId);
        redisTemplate.opsForValue().increment(key, 90L);
        return redisTemplate.opsForValue().get(key);
    }

    public Integer decreaseWaitingTime(Long storeId) {
        String key = String.format("%s:%d", WAITING_TIME_KEY_PREFIX, storeId);
        redisTemplate.opsForValue().decrement(key, 90L);
        return redisTemplate.opsForValue().get(key);
    }

    public Integer getWaitingTime(Long storeId) {
        String key = String.format("%s:%d", WAITING_TIME_KEY_PREFIX, storeId);
        return redisTemplate.opsForValue().get(key);
    }

}
