package noctem.storeService.store.dto.response;

public interface SearchStoreVo {
    Long getStoreId();

    String getName();

    String getMainImg();

    String getAddress();
    String getContactNumber();

    String getBusinessOpenHours();

    String getBusinessCloseHours();

    Boolean getIsParking();

    Boolean getIsEcoStore();

    Boolean getIsDriveThrough();

    Integer getDistance();
}
