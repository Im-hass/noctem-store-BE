package noctem.storeService.kafkaTest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payload {
    private Long test_id;
    private String name;
    private Integer number;
}
