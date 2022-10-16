package noctem.storeService.store.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.AppConfig;
import noctem.storeService.global.common.CommonException;
import noctem.storeService.global.enumeration.OrderStatus;
import noctem.storeService.global.security.bean.ClientInfoLoader;
import noctem.storeService.purchase.domain.entity.Purchase;
import noctem.storeService.purchase.domain.repository.PurchaseRepository;
import noctem.storeService.store.domain.entity.OrderRequest;
import noctem.storeService.store.domain.repository.OrderRequestRepository;
import noctem.storeService.store.domain.repository.RedisRepository;
import noctem.storeService.store.domain.repository.StoreRepository;
import noctem.storeService.store.dto.response.IncreaseUserExpKafkaDto;
import noctem.storeService.store.dto.response.OrderRequestResDto;
import noctem.storeService.store.dto.response.OrderStatusResDto;
import noctem.storeService.store.dto.response.WaitingTimeResDto;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ClientInfoLoader clientInfoLoader;
    private final RedisRepository redisRepository;
    private final PurchaseRepository purchaseRepository;
    private final OrderRequestRepository orderRequestRepository;
    private final StoreRepository storeRepository;
    private final String STORE_TO_USER_GRADE_EXP_TOPIC = "store-to-user-grade-exp";
    private final KafkaTemplate<String, String> stringKafkaTemplate;

    @Transactional(readOnly = true)
    @Override
    public List<OrderRequestResDto> getNotConfirmOrders() {
        List<OrderRequest> notConfirmOrderList = orderRequestRepository.findAllByOrderStatusAndStoreIdOrderByCreatedAtAsc(OrderStatus.NOT_CONFIRM, clientInfoLoader.getStoreId());

        List<Purchase> purchaseList = purchaseRepository.findAllByIdIn(
                notConfirmOrderList.stream().map(OrderRequest::getPurchaseId).collect(Collectors.toList()));

        Map<Long, String> orderRequestTimeMap = notConfirmOrderList.stream().collect(Collectors.toMap(OrderRequest::getPurchaseId, e -> redisRepository.getOrderRequestTime(e.getPurchaseId())));

        return purchaseList.stream().map(e -> new OrderRequestResDto(e, orderRequestTimeMap.get(e.getId()))).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderRequestResDto> getMakingOrders() {
        List<OrderRequest> makingOrderList = orderRequestRepository.findAllByOrderStatusAndStoreIdOrderByCreatedAtAsc(OrderStatus.MAKING, clientInfoLoader.getStoreId());

        List<Purchase> purchaseList = purchaseRepository.findAllByIdIn(
                makingOrderList.stream().map(OrderRequest::getPurchaseId).collect(Collectors.toList()));

        Map<Long, String> orderRequestTimeMap = makingOrderList.stream().collect(Collectors.toMap(OrderRequest::getPurchaseId, e -> redisRepository.getOrderRequestTime(e.getPurchaseId())));

        return purchaseList.stream().map(e -> new OrderRequestResDto(e, orderRequestTimeMap.get(e.getId()))).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderRequestResDto> getCompletedOrders() {
        List<OrderRequest> completedOrderList = orderRequestRepository.findAllByOrderStatusAndStoreIdOrderByCreatedAtAsc(OrderStatus.COMPLETED, clientInfoLoader.getStoreId());

        List<Purchase> purchaseList = purchaseRepository.findAllByIdIn(
                completedOrderList.stream().map(OrderRequest::getPurchaseId).collect(Collectors.toList()));

        Map<Long, String> orderRequestTimeMap = completedOrderList.stream().collect(Collectors.toMap(OrderRequest::getPurchaseId, e -> redisRepository.getOrderRequestTime(e.getPurchaseId())));

        return purchaseList.stream().map(e -> new OrderRequestResDto(e, orderRequestTimeMap.get(e.getId()))).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public OrderStatusResDto getOrderStatus(Long purchaseId) {
        return new OrderStatusResDto(redisRepository.getOrderStatus(purchaseId));
    }

    @Override
    public Boolean progressToMakingOrderStatus(Long purchaseId) {
        String orderStatus = redisRepository.getOrderStatus(purchaseId);
        if (OrderStatus.NOT_CONFIRM.getValue().equals(orderStatus)) {
            String getOrderStatusBeforeSet = redisRepository.getSetOrderStatus(purchaseId, OrderStatus.MAKING);
            if (OrderStatus.NOT_CONFIRM.getValue().equals(getOrderStatusBeforeSet)) {
                // '제조중' 변경 성공
                // 주문요청 저장
                OrderRequest.builder()
                        .purchaseId(purchaseId)
                        .orderStatus(OrderStatus.MAKING)
                        .build()
                        .linkToStoreFromOwner(storeRepository.findById(clientInfoLoader.getStoreId()).get());
                // 유저에게 push 알림
                return true;
            } else {
                // '제조중' 변경 실패 (도중에 취소요청이 들어온 경우)
                redisRepository.setOrderStatus(purchaseId, OrderStatus.findByValue(getOrderStatusBeforeSet));
            }
        }
        return false;
    }

    @Override
    public Boolean progressToCompletedOrderStatus(Long purchaseId) {
        String orderStatus = redisRepository.getOrderStatus(purchaseId);
        if (OrderStatus.MAKING.getValue().equals(orderStatus)) {
            // '제조완료' 변경
            redisRepository.setOrderStatus(purchaseId, OrderStatus.COMPLETED);
            // 주문요청 저장
            Purchase purchase = purchaseRepository.findById(purchaseId).get();
            OrderRequest.builder()
                    .purchaseId(purchaseId)
                    .orderStatus(OrderStatus.COMPLETED)
                    .build()
                    .linkToStoreFromOwner(storeRepository.findById(purchase.getStoreId()).get());
            // 대기시간 감소
            redisRepository.decreaseWaitingTime(purchase.getStoreId(), purchase.getPurchaseMenuList().size());
            // 유저 등급 경험치 반영
            increaseUserExp(purchase.getUserAccountId(), purchase.getPurchaseTotalPrice());
            // 유저에게 push 알림
            return true;
        }
        // '제조중' 상태가 아닌 경우 실패
        return false;
    }

    @Override
    public Boolean cancelOrderByUser(Long purchaseId) {
        // 본인확인
        Purchase purchase = purchaseRepository.findById(purchaseId).get();
        if (purchase.getUserAccountId() != clientInfoLoader.getUserAccountId()) {
            throw CommonException.builder().errorCode(5004).httpStatus(HttpStatus.UNAUTHORIZED).build();
        }
        String orderStatus = redisRepository.getOrderStatus(purchaseId);
        if (OrderStatus.NOT_CONFIRM.getValue().equals(orderStatus)) {
            String getOrderStatusBeforeSet = redisRepository.getSetOrderStatus(purchaseId, OrderStatus.CANCELED);
            if (OrderStatus.NOT_CONFIRM.getValue().equals(getOrderStatusBeforeSet)) {
                // '결제 취소' 성공
                // 유저의 환불 이벤트 발행
                // 매장에 주문 취소 이벤트 발행
                // 대기시간 감소
                redisRepository.decreaseWaitingTime(purchase.getStoreId(), purchase.getPurchaseMenuList().size());
                return true;
            } else {
                // '결제 취소' 실패
                redisRepository.setOrderStatus(purchaseId, OrderStatus.findByValue(getOrderStatusBeforeSet));
            }
        }
        return false;
    }

    @Transactional(readOnly = true)
    @Override
    public WaitingTimeResDto getWaitingTime(Long storeId) {
        return new WaitingTimeResDto(redisRepository.getWaitingTime(storeId));
    }

    private void increaseUserExp(Long userAccountId, Integer purchaseTotalPrice) {
        try {
            log.info("Send userExpDto through [{}] TOPIC", STORE_TO_USER_GRADE_EXP_TOPIC);
            stringKafkaTemplate.send(STORE_TO_USER_GRADE_EXP_TOPIC,
                    AppConfig.objectMapper().writeValueAsString(new IncreaseUserExpKafkaDto(userAccountId, purchaseTotalPrice)));
        } catch (JsonProcessingException e) {
            log.warn("Occurred JsonProcessingException. [{}]", OrderServiceImpl.class.getSimpleName());
        }
    }
}
