package noctem.storeService.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import noctem.storeService.purchase.domain.entity.Purchase;
import noctem.storeService.store.dto.InnerDto;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class OrderRequestResDto {
    private Integer index;
    private Long purchaseId;
    private Integer orderTotalQty; // 총 메뉴 개수
    private List<InnerDto.MenuResDto> menuList;
    private String userNickname;
    private Integer orderNumber; // storeOrderNumber
    private String orderRequestTime; // 매장에 주문이 최초로 들어간 시간

    public OrderRequestResDto(Purchase purchase, String orderRequestTime) {
        this.purchaseId = purchase.getId();
        this.orderTotalQty = 0;
        purchase.getPurchaseMenuList().forEach(e -> orderTotalQty += e.getQty());
        this.menuList = purchase.getPurchaseMenuList().stream().map(InnerDto.MenuResDto::new).collect(Collectors.toList());
        menuList.forEach(e -> e.setIndex(menuList.indexOf(e)));
        this.userNickname = purchase.getUserNickname();
        this.orderNumber = purchase.getStoreOrderNumber();
        this.orderRequestTime = orderRequestTime;
    }
}
