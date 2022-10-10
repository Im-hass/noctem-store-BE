package noctem.storeService.kafkaTest;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class KafkaDto implements Serializable {
    private Schema schema;
    private Payload payload;
}
