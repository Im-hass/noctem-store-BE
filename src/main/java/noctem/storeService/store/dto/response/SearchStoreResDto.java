package noctem.storeService.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

/***
 * wayToCome: 오시는 길
 * businessOpenHours: 영업 시작시간
 * businessCloseHours: 영업 마감시간
 * isOpen: 현재 영업상태 true: 영업중, false: 마감됨
 * contactNumber: 매장 연락처
 * distance: 유저와 매장과의 거리
 */
@Slf4j
@Data
@AllArgsConstructor
public class SearchStoreResDto {
    private Integer index;
    private Long storeId;
    private String name;
    private String mainImg;
    private String address;
    private String contactNumber;
    private String businessOpenHours;
    private String businessCloseHours;
    private Boolean isOpen;
    private Boolean isParking;
    private Boolean isEcoStore;
    private Boolean isDriveThrough;
    private String distance;

    public SearchStoreResDto(SearchStoreVo vo) {
        this.storeId = vo.getStoreId();
        this.name = vo.getName();
        this.mainImg = vo.getMainImg();
        this.address = vo.getAddress();
        this.contactNumber = vo.getContactNumber();
        this.businessOpenHours = vo.getBusinessOpenHours();
        this.businessCloseHours = vo.getBusinessCloseHours();
        this.isParking = vo.getIsParking();
        this.isEcoStore = vo.getIsEcoStore();
        this.isDriveThrough = vo.getIsDriveThrough();

        Integer distance = vo.getDistance();
        if (distance < 1000) {
            this.distance = String.format("%dm", distance);
        } else {
            this.distance = String.format("%.1fkm", (float) distance / 1000);
        }
        this.isOpen = isStoreOpen(this.businessOpenHours, this.businessCloseHours);
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
