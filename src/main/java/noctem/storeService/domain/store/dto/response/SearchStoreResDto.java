package noctem.storeService.domain.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import noctem.storeService.domain.store.entity.Store;

@Data
@AllArgsConstructor
public class SearchStoreResDto {
    private Long storeId;
    private String name;
    private String mainImg;
    private String address;
    private Boolean isParking;
    private Boolean isEcoStore;
    private Boolean isDriveThrough;
    private Integer distance; // 단위 미터(m)

    public SearchStoreResDto(Store store, Integer distance) {
        this.storeId = store.getId();
        this.name = store.getName();
        this.mainImg = store.getMainImg();
        this.address = store.getAddress();
        this.isParking = store.getIsParking();
        this.isEcoStore = store.getIsEcoStore();
        this.isDriveThrough = store.getIsDriveThrough();
        this.distance = distance;
    }
}
