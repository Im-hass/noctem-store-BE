package noctem.storeService.purchase.domain.repository;

import noctem.storeService.purchase.domain.entity.Purchase;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @EntityGraph(attributePaths = {"purchaseMenuList"})
    @Override
    Optional<Purchase> findById(Long purchaseId);

    @EntityGraph(attributePaths = {"purchaseMenuList"})
    List<Purchase> findAllByIdIn(List<Long> purchaseIds);
}
