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
    private String temperature;
    private String size;
    private String cupType;
    private Integer qty;
    private Long menuTotalPrice;

    public OrderMenuInProgressResDto(PurchaseMenu purchaseMenu) {
        this.sizeId = purchaseMenu.getSizeId();
        this.categorySmall = purchaseMenu.getCategorySmall().getValue();
        this.menuFullName = purchaseMenu.getMenuFullName();
        this.menuShortName = purchaseMenu.getMenuShortName();
        this.temperature = purchaseMenu.getTemperature();
        this.size = purchaseMenu.getSize();
        this.cupType = purchaseMenu.getCupType().getValue();
        this.qty = purchaseMenu.getQty();
        this.menuTotalPrice = purchaseMenu.getMenuTotalPrice().longValue();
    }
}
