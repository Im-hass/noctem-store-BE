package noctem.storeService.domain.store.service;

import noctem.storeService.domain.store.dto.response.SearchStoreResDto;

import java.util.List;

public interface StoreService {
    List<SearchStoreResDto> searchStore(Double latitude, Double longitude);
}
