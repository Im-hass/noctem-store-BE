package noctem.storeService.domain.store.dto.response;

public interface SearchStoreVo {
    Long getStoreId();

    String getName();

    String getMainImg();

    String getAddress();

    String getBusinessOpenHours(); // 영업 시작시간

    String getBusinessCloseHours(); // 영업 마강시간

    Boolean getIsParking();

    Boolean getIsEcoStore();

    Boolean getIsDriveThrough();

    Integer getDistance(); // 단위 미터(m)
}
