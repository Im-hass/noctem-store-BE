package noctem.storeService.domain.store.service;

import noctem.storeService.domain.store.dto.response.SearchStoreResDto;
import noctem.storeService.domain.store.dto.response.SoldOutMenuResDto;
import noctem.storeService.domain.store.dto.response.StoreInfoResDto;

import java.util.List;

public interface StoreService {
    StoreInfoResDto getStoreInfo(Long storeId);

    List<SoldOutMenuResDto> getSoldOutMenu(Long storeId);

    List<SearchStoreResDto> searchStore(Double latitude, Double longitude);
}
