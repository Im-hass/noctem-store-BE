package noctem.storeService.store.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.storeService.global.common.BaseEntity;
import noctem.storeService.global.enumeration.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_request_id")
    private Long id;
    private Long purchaseId;
    private OrderStatus orderStatus;
    private LocalDateTime orderRequestDttm;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public OrderRequest(Long purchaseId, OrderStatus orderStatus) {
        this.purchaseId = purchaseId;
        this.orderStatus = orderStatus;
        this.orderRequestDttm = LocalDateTime.now();
    }

    public OrderRequest linkToStoreFromOwner(Store store) {
        this.store = store;
        store.linkToOrderRequest(this);
        return this;
    }
}
