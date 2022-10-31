package noctem.storeService.store.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseToStoreVo {
    private Long storeId;
    private Long purchaseId;
    private String menuFullName;
    private Integer totalMenuQty;
}
