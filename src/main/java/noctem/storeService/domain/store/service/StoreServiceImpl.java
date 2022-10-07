package noctem.storeService.domain.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.domain.store.dto.response.SearchStoreResDto;
import noctem.storeService.domain.store.dto.response.SoldOutMenuResDto;
import noctem.storeService.domain.store.dto.response.StoreInfoResDto;
import noctem.storeService.domain.store.entity.Store;
import noctem.storeService.domain.store.repository.SoldOutMenuRepository;
import noctem.storeService.domain.store.repository.StoreRepository;
import noctem.storeService.global.common.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final SoldOutMenuRepository soldOutMenuRepository;

    @Override
    public StoreInfoResDto getStoreInfo(Long storeId) {
        Optional<Store> optionalStore = storeRepository.findById(storeId);
        if (optionalStore.isEmpty()) {
            throw CommonException.builder().errorCode(5001).httpStatus(HttpStatus.NOT_FOUND).build();
        }
        return new StoreInfoResDto(optionalStore.get());
    }

    @Override
    public List<SoldOutMenuResDto> getSoldOutMenu(Long storeId) {
        return soldOutMenuRepository.findAllByStoreId(storeId)
                .stream().map(e -> new SoldOutMenuResDto(e.getMenuId())).collect(Collectors.toList());
    }

    @Override
    public List<SearchStoreResDto> searchStore(Double latitude, Double longitude) {
        return storeRepository.findDtoByNativeProjections(latitude, longitude)
                .stream().map(e -> new SearchStoreResDto(e)).collect(Collectors.toList());
    }
}
