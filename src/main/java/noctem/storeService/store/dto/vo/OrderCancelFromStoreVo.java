package noctem.storeService.store.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCancelFromStoreVo {
    private Long userAccountId;
    private Integer orderNumber;
    private String orderStatus;
}
