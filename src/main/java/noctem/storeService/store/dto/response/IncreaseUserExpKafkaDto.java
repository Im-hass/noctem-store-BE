package noctem.storeService.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IncreaseUserExpKafkaDto {
    private Long userAccountId;
    private Integer purchaseTotalPrice;
}
