package noctem.storeService.kafkaTest;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final String TOPIC = "store-test";
    private Integer count = 0;

    @KafkaListener(topics = TOPIC, groupId = "store-test-group")
    public void consume(String message) throws InterruptedException {
        if (count % 10 == 0) {
            Thread.sleep(5000);
        }
        System.out.println(String.format("Consumed message : %s (count: %d)", message, count));
        count++;
    }
}
