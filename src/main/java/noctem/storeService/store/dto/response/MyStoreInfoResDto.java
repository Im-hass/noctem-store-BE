package noctem.storeService.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import noctem.storeService.store.domain.entity.Store;

@Data
@AllArgsConstructor
public class MyStoreInfoResDto {
    private Long managerId;
    private Long supervisorId;
    private String name;
    private String mainImg;
    private String address;
    private String wayToCome;
    private String businessOpenHours;
    private String businessCloseHours;
    private Boolean isOpen;
    private Boolean isParking;
    private Boolean isEcoStore;
    private Boolean isDriveThrough;
    private String contactNumber;

    public MyStoreInfoResDto(Store store) {
        this.managerId = store.getManagerId();
        this.supervisorId = store.getSupervisorId();
        this.name = store.getName();
        this.mainImg = store.getMainImg();
        this.address = store.getAddress();
        this.wayToCome = store.getWayToCome();
        this.businessOpenHours = store.getBusinessOpenHours();
        this.businessCloseHours = store.getBusinessCloseHours();
        this.isOpen = store.isOpen();
        this.isParking = store.getIsParking();
        this.isEcoStore = store.getIsEcoStore();
        this.isDriveThrough = store.getIsDriveThrough();
        this.contactNumber = store.getContactNumber();
    }
}
