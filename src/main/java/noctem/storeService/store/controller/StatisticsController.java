package noctem.storeService.store.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.global.common.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("${global.api.base-path}/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    // 각종 통계데이터 요청
    // @PreAuthorize("hasRole('STORE')")
    @GetMapping("/{storeId}")
    public CommonResponse getStatistics(@PathVariable Long storeId) {
        return CommonResponse.builder()
                .data(true)
                .build();
    }
}
