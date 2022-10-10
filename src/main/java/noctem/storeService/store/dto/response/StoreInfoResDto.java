package noctem.storeService.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import noctem.storeService.store.domain.entity.Store;

import java.text.SimpleDateFormat;
import java.util.List;

/***
 * wayToCome: 오시는 길
 * businessOpenHours: 영업 시작시간
 * businessCloseHours: 영업 마감시간
 * isOpen: 현재 영업상태 true: 영업중, false: 마감됨
 * contactNumber: 매장 연락처
 */
@Data
@AllArgsConstructor
public class StoreInfoResDto {
    private String name;
    private String mainImg;
    private List<String> imgList;
    private String address;
    private String wayToCome;
    private String businessOpenHours;
    private String businessCloseHours;
    private Boolean isOpen;
    private Boolean isParking;
    private Boolean isEcoStore;
    private Boolean isDriveThrough;
    private String contactNumber;

    public StoreInfoResDto(Store store) {
        this.name = store.getName();
        this.mainImg = store.getMainImg();
        this.imgList = store.getImgList();
        this.address = store.getAddress();
        this.wayToCome = store.getWayToCome();
        this.businessOpenHours = store.getBusinessOpenHours();
        this.businessCloseHours = store.getBusinessCloseHours();
        this.isOpen = isStoreOpen(this.businessOpenHours, this.businessCloseHours);
        this.isParking = store.getIsParking();
        this.isEcoStore = store.getIsEcoStore();
        this.isDriveThrough = store.getIsDriveThrough();
        this.contactNumber = store.getContactNumber();
    }

    private Boolean isStoreOpen(String businessOpenHours, String businessCloseHours) {
        SimpleDateFormat timeForamt = new SimpleDateFormat("HHmm");
        Integer nowTime = Integer.valueOf(timeForamt.format(System.currentTimeMillis()));

        Integer openHour = Integer.valueOf(businessOpenHours.replaceAll(":", ""));
        Integer closeHour = Integer.valueOf(businessCloseHours.replaceAll(":", ""));

        if (openHour <= nowTime && nowTime < closeHour) {
            return true;
        }
        return false;
    }
}
