package noctem.storeService.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import noctem.storeService.purchase.domain.entity.PurchaseMenu;

@Data
@AllArgsConstructor
public class OrderMenuInProgressResDto {
    private Long sizeId;
    private Long cartId = 0L;
    private String categorySmall;
    private String menuFullName;
    private String menuShortName;
    private String imgUrl = null;
    private String menuTemperature;
    private String menuSize;
    private String menuCupType;
    private Integer qty;
    private Long menuTotalPrice;

    public OrderMenuInProgressResDto(PurchaseMenu purchaseMenu) {
        this.sizeId = purchaseMenu.getSizeId();
        this.categorySmall = purchaseMenu.getCategorySmall().getValue();
        this.menuFullName = purchaseMenu.getMenuFullName();
        this.menuShortName = purchaseMenu.getMenuShortName();
        this.menuTemperature = purchaseMenu.getTemperature();
        this.menuSize = purchaseMenu.getSize();
        this.menuCupType = purchaseMenu.getCupType().getValue();
        this.qty = purchaseMenu.getQty();
        this.menuTotalPrice = purchaseMenu.getMenuTotalPrice().longValue();
    }
}
