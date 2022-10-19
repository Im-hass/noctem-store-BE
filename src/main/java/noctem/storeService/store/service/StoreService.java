package noctem.storeService.store.service;

import noctem.storeService.store.dto.response.*;

import java.util.List;

public interface StoreService {
    StoreInfoResDto getStoreInfo(Long storeId);

    MyStoreInfoResDto getMyStoreInfo();

    StoreSimpleInfoResDto getStoreSimpleInfo(Long storeId);

    List<SoldOutMenuResDto> getSoldOutMenu(Long storeId);

    Boolean editSoldOutMenu(Long menuId);

    List<SearchStoreResDto> searchNearbyStore(Double latitude, Double longitude);
}
