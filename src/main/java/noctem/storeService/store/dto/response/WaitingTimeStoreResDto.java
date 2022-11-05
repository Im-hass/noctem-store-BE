package noctem.storeService.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WaitingTimeStoreResDto {
    private Integer waitingOrderCount;
    private Integer waitingTime;
}
