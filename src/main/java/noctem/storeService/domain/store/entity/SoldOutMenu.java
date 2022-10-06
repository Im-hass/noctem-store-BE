package noctem.storeService.domain.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SoldOutMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_menu_id")
    private Long id;
    private Long menuId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public SoldOutMenu(Long menuId) {
        this.menuId = menuId;
    }

    public SoldOutMenu linkToStore(Store store) {
        this.store = store;
        store.linkToSoldOutMenu(this);
        return this;
    }
}
