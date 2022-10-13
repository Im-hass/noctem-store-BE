package noctem.storeService.store.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.storeService.global.common.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/***
 * menuTotalPrice: 옵션을 포함한 메뉴가격
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseMenu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_menu_id")
    private Long id;
    private Long sizeId;
    private String menuFullName;
    private String menuShortName;
    private Integer qty;
    private Integer menuTotalPrice;
    @JsonIgnore
    @OneToMany(mappedBy = "purchaseMenu", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchasePersonalOption> purchasePersonalOptionList = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @Builder
    public PurchaseMenu(Long sizeId, String menuFullName, String menuShortName, Integer qty, Integer menuTotalPrice) {
        this.sizeId = sizeId;
        this.menuFullName = menuFullName;
        this.menuShortName = menuShortName;
        this.qty = qty;
        this.menuTotalPrice = menuTotalPrice;
    }

    public PurchaseMenu linkToPurchase(Purchase purchase) {
        this.purchase = purchase;
        return this;
    }

    public PurchaseMenu linkToPersonalOptionList(List<PurchasePersonalOption> purchasePersonalOptionList) {
        this.purchasePersonalOptionList.addAll(purchasePersonalOptionList);
        purchasePersonalOptionList.forEach(e -> e.linkToPurchaseMenu(this));
        return this;
    }
}
