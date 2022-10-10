package noctem.storeService.store.service;

import noctem.storeService.store.dto.response.SearchStoreResDto;
import noctem.storeService.store.dto.response.SoldOutMenuResDto;
import noctem.storeService.store.dto.response.StoreInfoResDto;

import java.util.List;

public interface StoreService {
    StoreInfoResDto getStoreInfo(Long storeId);

    List<SoldOutMenuResDto> getSoldOutMenu(Long storeId);

    List<SearchStoreResDto> searchNearbyStore(Double latitude, Double longitude);
}
