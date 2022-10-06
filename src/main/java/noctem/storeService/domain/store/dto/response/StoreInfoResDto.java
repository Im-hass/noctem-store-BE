package noctem.storeService.domain.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import noctem.storeService.domain.store.entity.Store;

import java.util.List;

@Data
@AllArgsConstructor
public class StoreInfoResDto {
    private String name;
    private String mainImg;
    private List<String> imgList;
    private String address;
    private String wayToCome; // 오시는 길
    private String businessOpenHours; // 영업시간
    private String businessCloseHours; // 영업시간
    private Boolean isParking;
    private Boolean isEcoStore;
    private Boolean isDriveThrough;
    private String contactNumber; // 매장 연락처

    public StoreInfoResDto(Store store) {
        this.name = store.getName();
        this.mainImg = store.getMainImg();
        this.imgList = store.getImgList();
        this.address = store.getAddress();
        this.wayToCome = store.getWayToCome();
        this.businessOpenHours = store.getBusinessOpenHours();
        this.businessCloseHours = store.getBusinessCloseHours();
        this.isParking = store.getIsParking();
        this.isEcoStore = store.getIsEcoStore();
        this.isDriveThrough = store.getIsDriveThrough();
        this.contactNumber = store.getContactNumber();
    }
}
