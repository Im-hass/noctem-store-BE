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
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDateTime orderRequestDttm;
    private Boolean isCanceled;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public OrderRequest(Long purchaseId, OrderStatus orderStatus) {
        this.purchaseId = purchaseId;
        this.orderStatus = orderStatus;
        this.orderRequestDttm = LocalDateTime.now();
        this.isCanceled = false;
    }

    public OrderRequest linkToStore(Store store) {
        this.store = store;
        return this;
    }

    public OrderRequest processDone() {
        delete();
        return this;
    }

    public OrderRequest orderCancel() {
        this.isCanceled = true;
        delete();
        return this;
    }

}
