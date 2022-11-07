package noctem.storeService.store.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseFromUserVo {
    private Long storeId;
    private String menuFullName;
    private Integer totalMenuQty;
    private Integer orderNumber;
    private Long userAccountId;
    private Long purchaseId;
}
