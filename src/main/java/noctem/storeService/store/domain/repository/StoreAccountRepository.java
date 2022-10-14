package noctem.storeService.store.domain.repository;

import noctem.storeService.store.domain.entity.StoreAccount;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreAccountRepository extends JpaRepository<StoreAccount, Long> {
    @EntityGraph(attributePaths = "store")
    StoreAccount findByLoginId(String loginId);
}
