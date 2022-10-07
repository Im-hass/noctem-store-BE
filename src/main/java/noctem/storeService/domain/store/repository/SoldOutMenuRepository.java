package noctem.storeService.domain.store.repository;

import noctem.storeService.domain.store.entity.SoldOutMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoldOutMenuRepository extends JpaRepository<SoldOutMenu, Long> {
    List<SoldOutMenu> findAllByStoreId(Long storeId);
}
