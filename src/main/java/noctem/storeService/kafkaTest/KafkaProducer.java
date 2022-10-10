package noctem.storeService.kafkaTest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {
    // yml -> 글로벌
    // bean -> 개별설정 가능
    private final String TOPIC = "store-test";
    private final KafkaTemplate<String, String> stringKafkaTemplate;

    public void sendMessage(String message) {
        System.out.println(String.format("Produce message : %s", message));
        stringKafkaTemplate.send(TOPIC, message);
    }

    public void addSoldOutMenu() {
//        try {
////            Payload payload1 = Payload.builder().menu_id(123L).store_id(12L).build();
//            Payload payload2 = Payload.builder().menu_id(456L).store_id(45L).build();
////            SoldOutMenuKafkaTemplate.send("sold_out_menu",
////                    KafkaDto.builder().schema(schema).payload(payload1).build()
////            );
//            stringKafkaTemplate.send("sold_out_menu",
//                    AppConfig.objectMapper().writeValueAsString(KafkaDto.builder().schema(schema).payload(payload2).build())
//            );
//        } catch (Exception e) {
//            log.info("응 에러 ㅋ = {}", e);
//        }
    }

    public void addTest() {
//        try {
//            stringKafkaTemplate.send("test-topic",
//                    AppConfig.objectMapper().writeValueAsString(KafkaDto.builder()
//                            .schema(schema)
//                            .payload(new Payload(null, "안농 ㅋ", 1234))
//                            .build())
//            );
//        } catch (Exception e) {
//            log.info("응 에러 ㅋ = {}", e);
//        }
    }
}
