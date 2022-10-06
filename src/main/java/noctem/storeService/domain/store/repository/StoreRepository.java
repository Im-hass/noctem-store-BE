package noctem.storeService.domain.store.repository;

import noctem.storeService.domain.store.dto.response.SearchStoreVo;
import noctem.storeService.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query(value = "SELECT s.store_id AS storeId, " +
            "s.name AS name, s.main_img AS mainImg, " +
            "s.address AS address, s.way_to_come AS wayToCome, " +
            "s.business_open_hours AS businessOpenHours, " +
            "s.business_close_Hours AS businessCloseHours, " +
            "s.is_parking AS isParking, s.is_eco_store AS isEcoStore, " +
            "s.is_drive_through AS isDriveThrough, " +
            "(6371000*acos(cos(radians(:latitude))*cos(radians(s.latitude))*cos(radians(s.longitude)-" +
            "radians(:longitude))+sin(radians(:latitude))*sin(radians(s.latitude)))) AS distance " +
            "FROM store AS s HAVING distance <= 5000 ORDER BY distance", nativeQuery = true)
    List<SearchStoreVo> findDtoByNativeProjections(@Param("latitude") Double latitude, @Param("longitude") Double longitude);
}
