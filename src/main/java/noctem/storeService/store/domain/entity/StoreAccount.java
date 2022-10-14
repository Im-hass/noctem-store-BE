package noctem.storeService.store.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.storeService.global.common.BaseEntity;
import noctem.storeService.global.enumeration.Role;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreAccount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_account_id")
    private Long id;
    @Column(unique = true)
    private String loginId;
    private String password;
    private LocalDateTime lastAccessTime;
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_STORE;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "store_id")
    public Store store;

    @Builder
    public StoreAccount(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public StoreAccount linkToStoreInfoFromOwner(Store store) {
        this.store = store;
        store.linkToStoreAccount(this);
        return this;
    }

    public void updateLastAccessTime() {
        this.lastAccessTime = LocalDateTime.now();
    }
}
