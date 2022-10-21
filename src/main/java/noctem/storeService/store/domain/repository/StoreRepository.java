package noctem.storeService.store.domain.repository;

import noctem.storeService.store.domain.entity.Store;
import noctem.storeService.store.dto.response.SearchStoreVo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query(value = "SELECT s.store_id AS storeId, " +
            "s.name AS name, " +
            "s.main_img AS mainImg, " +
            "s.address AS address, " +
            "s.contact_number AS contactNumber, " +
            "s.way_to_come AS wayToCome, " +
            "s.business_open_hours AS businessOpenHours, " +
            "s.business_close_Hours AS businessCloseHours, " +
            "s.is_parking AS isParking, " +
            "s.is_eco_store AS isEcoStore, " +
            "s.is_drive_through AS isDriveThrough, " +
            "(6371000*acos(cos(radians(:latitude))*cos(radians(s.latitude))*cos(radians(s.longitude)-" +
            "radians(:longitude))+sin(radians(:latitude))*sin(radians(s.latitude)))) AS distance " +
            "FROM store AS s " +
            "ORDER BY distance " +
            "LIMIT 10", nativeQuery = true)
    List<SearchStoreVo> findDtoByNativeProjections(@Param("latitude") Double latitude,
                                                   @Param("longitude") Double longitude);

    @Override
    @EntityGraph(attributePaths = {"storeAccount", "orderRequestList"})
    Optional<Store> findById(Long id);
}
