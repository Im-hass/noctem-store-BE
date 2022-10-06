package noctem.storeService.domain.store.repository;

import noctem.storeService.domain.store.dto.response.SearchStoreDto;
import noctem.storeService.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query(value = "SELECT *, " +
            "(6371000*acos(cos(radians(:latitude))*cos(radians(:latitude))*cos(radians(:longitude)\n" +
            "-radians(:longitude))+sin(radians(:latitude))*sin(radians(:latitude)))) AS distance" +
            "FROM store " +
            "HAVING distance <= 5000 " +
            "ORDER BY distance", nativeQuery = true)
    List<SearchStoreDto> findAllByLatitudeAndLongitude(@Param("latitude") Double latitude, @Param("longitude") Double longitude);
}
