package noctem.storeService.store.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.global.common.CommonResponse;
import noctem.storeService.store.dto.response.SearchStoreResDto;
import noctem.storeService.store.dto.response.SoldOutMenuResDto;
import noctem.storeService.store.service.StoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("${global.api.base-path}/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/{storeId}")
    public CommonResponse getStoreInfo(@PathVariable Long storeId) {
        return CommonResponse.builder()
                .data(storeService.getStoreInfo(storeId))
                .build();
    }

    @GetMapping("/{storeId}/soldOut")
    public CommonResponse getSoldOutMenu(@PathVariable Long storeId) {
        List<SoldOutMenuResDto> dtoList = storeService.getSoldOutMenu(storeId);
        dtoList.forEach(e -> e.setIndex(dtoList.indexOf(e)));
        return CommonResponse.builder()
                .data(dtoList)
                .build();
    }

    // @PreAuthorize("hasRole('STORE')")
    @PatchMapping("/{storeId}/soldOut/{menuId}")
    public CommonResponse editSoldOutMenu(@PathVariable Long storeId, @PathVariable Long menuId) {
        return CommonResponse.builder()
                .data(storeService.editSoldOutMenu(storeId, menuId))
                .build();
    }

    @GetMapping("/search/{latitude}/{longitude}")
    public CommonResponse searchNearbyStore(@PathVariable Double latitude, @PathVariable Double longitude) {
        List<SearchStoreResDto> dtoList = storeService.searchNearbyStore(latitude, longitude);
        dtoList.forEach(e -> e.setIndex(dtoList.indexOf(e)));
        return CommonResponse.builder()
                .data(dtoList)
                .build();
    }
}
