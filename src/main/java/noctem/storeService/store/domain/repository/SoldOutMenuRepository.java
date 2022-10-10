package noctem.storeService.store.domain.repository;

import noctem.storeService.store.domain.entity.SoldOutMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoldOutMenuRepository extends JpaRepository<SoldOutMenu, Long> {
    List<SoldOutMenu> findAllByStoreId(Long storeId);
}
