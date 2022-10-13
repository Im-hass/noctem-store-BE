package noctem.storeService.store.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.global.common.CommonResponse;
import noctem.storeService.store.service.BeverageMakingService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("${global.api.base-path}/beverageMaking")
@RequiredArgsConstructor
public class BeverageMakingController {
    private final BeverageMakingService beverageMakingService;

    // 메뉴 제조상태 조회 - redis
    @GetMapping("/{purchaseId}")
    public CommonResponse getMakingStatus(@PathVariable Long purchaseId) {
        return CommonResponse.builder()
                .data(true)
                .build();
    }

    // 매장의 메뉴 제조상태 변경. '확인중' -> '제조중' -> '제조완료' 순서로 변경. - redis
    // @PreAuthorize("hasRole('STORE')")
    @PatchMapping("/{purchaseId}")
    public CommonResponse patchMakingStatus(@PathVariable Long purchaseId) {
        return CommonResponse.builder()
                .data(true)
                .build();
    }

    // 유저의 메뉴 취소요청. '확인중' 상태일 때만 가능. 본인 확인 필.
    // @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{purchaseId}")
    public CommonResponse cancelPurchase(@PathVariable Long purchaseId) {
        return CommonResponse.builder()
                .data(true)
                .build();
    }
}
