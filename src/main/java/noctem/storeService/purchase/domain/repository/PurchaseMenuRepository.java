package noctem.storeService.purchase.domain.repository;

import noctem.storeService.purchase.domain.entity.PurchaseMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseMenuRepository extends JpaRepository<PurchaseMenu, Long> {
    Long countByPurchase_IdIn(List<Long> idList);
}
