package noctem.storeService.store.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderStatusChangeFromStoreVo {
    private Long storeId;
    private Long userAccountId;
    private Long purchaseId;
    private Integer orderNumber;
    private String orderStatus;
}
