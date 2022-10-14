package noctem.storeService.purchase.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.storeService.global.common.BaseEntity;
import noctem.storeService.global.enumeration.Amount;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchasePersonalOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_personal_option_id")
    private Long id;
    private Long personalOptionId;
    private String personalOptionName;
    @Enumerated(EnumType.STRING)
    private Amount amount;
    private Integer totalSurcharge; // 총 추가금(샷 추가만 amount의 영향을 받음)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "purchase_menu_id")
    private PurchaseMenu purchaseMenu;

    @Builder
    public PurchasePersonalOption(Long personalOptionId, String personalOptionName, Amount amount, Integer totalSurcharge) {
        this.personalOptionId = personalOptionId;
        this.personalOptionName = personalOptionName;
        this.amount = amount;
        this.totalSurcharge = totalSurcharge;
    }

    public PurchasePersonalOption linkToPurchaseMenu(PurchaseMenu purchaseMenu) {
        this.purchaseMenu = purchaseMenu;
        return this;
    }
}
