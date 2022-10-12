package noctem.storeService.store.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.storeService.global.common.BaseEntity;
import noctem.storeService.global.enumeration.OrderStatus;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_request_id")
    private Long id;
    private Long purchaseNumber;
    // 주문 메뉴에 대한 정보 or id만 조회?
    private OrderStatus orderStatus;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public OrderRequest(Long purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
        this.orderStatus = OrderStatus.NOT_CONFIRM;
    }

    public OrderRequest linkToStore(Store store) {
        this.store = store;
        store.linkToOrderRequest(this);
        return this;
    }
}
