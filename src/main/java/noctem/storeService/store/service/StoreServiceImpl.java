package noctem.storeService.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.global.common.CommonException;
import noctem.storeService.global.security.bean.ClientInfoLoader;
import noctem.storeService.store.domain.entity.SoldOutMenu;
import noctem.storeService.store.domain.entity.Store;
import noctem.storeService.store.domain.repository.SoldOutMenuRepository;
import noctem.storeService.store.domain.repository.StoreRepository;
import noctem.storeService.store.dto.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final SoldOutMenuRepository soldOutMenuRepository;
    private final ClientInfoLoader clientInfoLoader;

    @Transactional(readOnly = true)
    @Override
    public StoreInfoResDto getStoreInfo(Long storeId) {
        Optional<Store> optionalStore = storeRepository.findById(storeId);
        if (optionalStore.isEmpty()) {
            throw CommonException.builder().errorCode(5001).httpStatus(HttpStatus.NOT_FOUND).build();
        }
        return new StoreInfoResDto(optionalStore.get());
    }

    @Transactional(readOnly = true)
    @Override
    public MyStoreInfoResDto getMyStoreInfo() {
        return new MyStoreInfoResDto(storeRepository.findById(clientInfoLoader.getStoreId()).get());
    }

    @Transactional(readOnly = true)
    @Override
    public StoreSimpleInfoResDto getStoreSimpleInfo(Long storeId) {
        Store store = storeRepository.findById(storeId).get();
        return new StoreSimpleInfoResDto(store.getName(), store.getAddress(), store.getContactNumber());
    }

    @Transactional(readOnly = true)
    @Override
    public List<SoldOutMenuResDto> getSoldOutMenu(Long storeId) {
        return soldOutMenuRepository.findAllByStoreId(storeId)
                .stream().map(e -> new SoldOutMenuResDto(e.getMenuId())).collect(Collectors.toList());
    }

    @Override
    public Boolean editSoldOutMenu(Long menuId) {
        Long storeId = clientInfoLoader.getStoreId();
        Store store = storeRepository.findById(storeId).get();
        // 본인확인
        identificationByStoreId(storeId);

        Map<Long, SoldOutMenu> soldOutMenuMap = store.getSoldOutMenuList().stream()
                .collect(Collectors.toMap(SoldOutMenu::getMenuId, e -> e));
        if (soldOutMenuMap.containsKey(menuId)) {
            store.delSoldOutMenu(soldOutMenuMap.get(menuId));
        } else {
            soldOutMenuRepository.save(SoldOutMenu.builder().menuId(menuId).build()
                    .linkToStore(store));
        }
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public List<SearchStoreResDto> searchNearbyStore(Double latitude, Double longitude) {
        return storeRepository.findDtoByNativeProjections(latitude, longitude)
                .stream().map(SearchStoreResDto::new).collect(Collectors.toList());
    }

    private void identificationByStoreId(Long storeId) {
        if (clientInfoLoader.getStoreId() != storeId) {
            throw CommonException.builder().errorCode(5004).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }
}
