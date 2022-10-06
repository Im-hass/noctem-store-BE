package noctem.storeService.domain.store.controller;

import lombok.RequiredArgsConstructor;
import noctem.storeService.domain.store.service.StoreService;
import noctem.storeService.global.common.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${global.api.base-path}/search")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/{latitude}/{longitude}")
    public CommonResponse searchStore(@PathVariable Double latitude, @PathVariable Double longitude) {
        return CommonResponse.builder()
                .data(storeService.searchStore(latitude, longitude))
                .build();
    }
}
