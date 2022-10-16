package noctem.storeService.purchase.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.storeService.global.common.BaseEntity;

import javax.persistence.*;

/***
 * giftCardId: 사용한 기프트카드 id
 * paidPrice: 기프트카드로 지불한 금액
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsedGiftCard extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "used_gift_card_id")
    private Long id;
    private Long giftCardId;
    private Integer paidPrice;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @Builder
    public UsedGiftCard(Long giftCardId, Integer paidPrice) {
        this.giftCardId = giftCardId;
        this.paidPrice = paidPrice;
    }

    public UsedGiftCard linkToPurchase(Purchase purchase) {
        this.purchase = purchase;
        purchase.linkToUsedGiftCard(this);
        return this;
    }
}
