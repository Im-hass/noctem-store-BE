package noctem.storeService.domain.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchStoreResDto {
    private Integer index;
    private Long storeId;
    private String name;
    private String mainImg;
    private String address;
    private String businessOpenHours;
    private String businessCloseHours;
    private Boolean isParking;
    private Boolean isEcoStore;
    private Boolean isDriveThrough;
    private Integer distance; // 단위 미터(m)

    public SearchStoreResDto(SearchStoreVo vo) {
        this.storeId = vo.getStoreId();
        this.name = vo.getName();
        this.mainImg = vo.getMainImg();
        this.address = vo.getAddress();
        this.businessOpenHours = vo.getBusinessOpenHours();
        this.businessCloseHours = vo.getBusinessCloseHours();
        this.isParking = vo.getIsParking();
        this.isEcoStore = vo.getIsEcoStore();
        this.isDriveThrough = vo.getIsDriveThrough();
        this.distance = vo.getDistance();
    }
}
