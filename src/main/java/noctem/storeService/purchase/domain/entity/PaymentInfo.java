package noctem.storeService.purchase.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.storeService.global.common.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/***
 * = 결제 정보 =
 * tid: 외부 API의 결제 고유 번호
 * cardCorp: 카드사 한글명 -> 나중에 enum으로 처리하기
 * cardPaymentPrice: 카드 결제 금액
 * vat: 부가세 금액(원래는 외부 API로부터 받는 값)
 * approvedAt: 결제 승인 시각(원래는 외부 API로부터 받는 값)
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_info_id")
    private Long id;
    @Column(unique = true)
    private String tid;
    private String cardCorp;
    private Integer cardPaymentPrice;
    private Integer vat;
    private LocalDateTime approvedAt;

    @JsonIgnore
    @OneToOne(mappedBy = "paymentInfo", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Purchase purchase;

    @Builder
    public PaymentInfo(String cardCorp, Integer cardPaymentPrice) {
        this.tid = UUID.randomUUID().toString();
        this.cardCorp = cardCorp;
        this.cardPaymentPrice = cardPaymentPrice;
        this.vat = cardPaymentPrice / 10;
        this.approvedAt = LocalDateTime.now();
    }

    public PaymentInfo linkToPurchase(Purchase purchase) {
        this.purchase = purchase;
        return this;
    }
}
