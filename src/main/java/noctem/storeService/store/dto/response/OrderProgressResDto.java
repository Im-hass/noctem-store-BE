package noctem.storeService.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderProgressResDto {
    private Long storeId;
    private Long purchaseId;
    private String orderStatus;
}
