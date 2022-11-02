package noctem.storeService.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import noctem.storeService.purchase.domain.entity.PurchaseMenu;

@Data
@AllArgsConstructor
public class OrderMenuInProgressResDto {
    private Integer index;
    private Long sizeId;
    private String menuFullName;
    private String menuOptions;

    public OrderMenuInProgressResDto(PurchaseMenu purchaseMenu) {
        this.sizeId = purchaseMenu.getSizeId();
        this.menuFullName = purchaseMenu.getMenuFullName();
        this.menuOptions = String.format("%s | %s | %s", purchaseMenu.getTemperature(), purchaseMenu.getSize(), purchaseMenu.getCupType().getValue());
    }
}
