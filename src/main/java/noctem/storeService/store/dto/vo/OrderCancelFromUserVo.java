package noctem.storeService.store.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCancelFromUserVo {
    private Long storeId;
    private Integer orderNumber;
    private Long userAccountId;
}
