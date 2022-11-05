package noctem.storeService.store.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.global.common.CommonResponse;
import noctem.storeService.store.dto.response.OrderMenuInProgressResDto;
import noctem.storeService.store.dto.response.OrderRequestResDto;
import noctem.storeService.store.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${global.api.base-path}/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 자기 매장의 '주문확인중' 목록 조회
    @PreAuthorize("hasRole('STORE')")
    @GetMapping("/notConfirm")
    public CommonResponse getNotConfirmOrders() {
        List<OrderRequestResDto> dtoList = orderService.getNotConfirmOrders();
        dtoList.forEach(e -> e.setIndex(dtoList.indexOf(e)));
        return CommonResponse.builder()
                .data(dtoList)
                .build();
    }

    // 자기 매장의 '제조중' 목록 조회
    @PreAuthorize("hasRole('STORE')")
    @GetMapping("/making")
    public CommonResponse getMakingOrders() {
        List<OrderRequestResDto> dtoList = orderService.getMakingOrders();
        dtoList.forEach(e -> e.setIndex(dtoList.indexOf(e)));
        return CommonResponse.builder()
                .data(dtoList)
                .build();
    }

    // 자기 매장의 '제조완료' 목록 조회 (5개)
    @PreAuthorize("hasRole('STORE')")
    @GetMapping("/completed")
    public CommonResponse getCompletedOrders() {
        List<OrderRequestResDto> dtoList = orderService.getCompletedOrders();
        Collections.reverse(dtoList);
        dtoList.forEach(e -> e.setIndex(dtoList.indexOf(e)));
        return CommonResponse.builder()
                .data(dtoList)
                .build();
    }

    // 결제된 건의 주문상태 조회
    @GetMapping("/{purchaseId}")
    public CommonResponse getOrderStatus(@PathVariable Long purchaseId) {
        return CommonResponse.builder()
                .data(orderService.getOrderStatus(purchaseId))
                .build();
    }

    // 주문상태 '주문확인중' -> '제조중'으로 변경
    @PreAuthorize("hasRole('STORE')")
    @PatchMapping("/{purchaseId}/making")
    public CommonResponse progressToMakingOrderStatus(@PathVariable Long purchaseId) {
        return CommonResponse.builder()
                .data(orderService.progressToMakingOrderStatus(purchaseId))
                .build();
    }

    // 주문상태 '제조중' -> '제조완료'로 변경
    @PreAuthorize("hasRole('STORE')")
    @PatchMapping("/{purchaseId}/completed")
    public CommonResponse progressToCompletedOrderStatus(@PathVariable Long purchaseId) {
        return CommonResponse.builder()
                .data(orderService.progressToCompletedOrderStatus(purchaseId))
                .build();
    }

    // 유저의 주문 취소요청. '주문확인중' 상태일 때만 가능. 본인 확인 필.
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/user/{purchaseId}/cancel")
    public CommonResponse cancelOrderByUser(@PathVariable Long purchaseId) {
        return CommonResponse.builder()
                .data(orderService.cancelOrderByUser(purchaseId))
                .build();
    }

    // 매장의 주문 반려. '주문확인중' 상태일 때만 가능. 본인 확인 필.
    @PreAuthorize("hasRole('STORE')")
    @PatchMapping("/store/{purchaseId}/cancel")
    public CommonResponse cancelOrderByStore(@PathVariable Long purchaseId) {
        return CommonResponse.builder()
                .data(orderService.cancelOrderByStore(purchaseId))
                .build();
    }

    // 매장의 예상 대기시간
    @GetMapping("/waitingTime/{storeId}")
    public CommonResponse storeWaitingTime(@PathVariable Long storeId) {
        return CommonResponse.builder()
                .data(orderService.getStoreWaitingTime(storeId))
                .build();
    }

    // 유저의 예상 대기시간
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/waitingTime/user")
    public CommonResponse userWaitingTime() {
        return CommonResponse.builder()
                .data(orderService.getUserWaitingTime())
                .build();
    }

    // 유저의 진행중인 주문 조회
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/orderInProgress")
    public CommonResponse getOrderInProgress() {
        return CommonResponse.builder()
                .data(orderService.getOrderInProgress())
                .build();
    }

    // 유저의 진행중인 주문의 메뉴 조회
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/orderMenuInProgress")
    public CommonResponse getOrderMenuInProgress() {
        List<OrderMenuInProgressResDto> dtoList = orderService.getOrderMenuInProgress();
        dtoList.forEach(e -> e.setIndex(dtoList.indexOf(e)));
        return CommonResponse.builder()
                .data(dtoList)
                .build();
    }

    // == dev 코드 ==
    @PreAuthorize("hasRole('STORE')")
    @PatchMapping("/batchProccess")
    public CommonResponse batchProccess() {
        orderService.orderBatchProcessing();
        return CommonResponse.builder()
                .data(true)
                .build();
    }
}