package noctem.storeService.domain.store.controller;

import lombok.RequiredArgsConstructor;
import noctem.storeService.domain.store.dto.response.SearchStoreResDto;
import noctem.storeService.domain.store.service.StoreService;
import noctem.storeService.global.common.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/search/{latitude}/{longitude}")
    public CommonResponse searchStore(@PathVariable Double latitude, @PathVariable Double longitude) {
        List<SearchStoreResDto> dtoList = storeService.searchStore(latitude, longitude);
        dtoList.forEach(e -> e.setIndex(dtoList.indexOf(e)));
        return CommonResponse.builder()
                .data(dtoList)
                .build();
    }
}
