package noctem.storeService.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WaitingTimeUserResDto {
    private Integer myOrderNumber;
    private Long waitingTime;
}
