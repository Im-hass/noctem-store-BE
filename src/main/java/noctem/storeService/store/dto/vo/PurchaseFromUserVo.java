package noctem.storeService.store.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseFromUserVo {
    private Long storeId;
    private Integer totalMenuQty;
}
