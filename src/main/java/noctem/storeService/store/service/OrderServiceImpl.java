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
import noctem.storeService.store.domain.entity.Store;
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

import java.util.*;
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
        List<OrderRequest> notConfirmOrderList = orderRequestRepository.findAllByOrderStatusAndStoreIdAndIsDeletedFalseAndIsCanceledFalseOrderByOrderRequestDttmAsc(OrderStatus.NOT_CONFIRM, clientInfoLoader.getStoreId());

        List<Purchase> purchaseList = purchaseRepository.findAllByIdIn(
                notConfirmOrderList.stream().map(OrderRequest::getPurchaseId).collect(Collectors.toList()));

        Map<Long, String> orderRequestTimeMap = new HashMap<>();

        notConfirmOrderList.forEach(e -> {
            String orderRequestTime = redisRepository.getOrderRequestTime(e.getPurchaseId());
            if (orderRequestTime != null) {
                orderRequestTimeMap.put(e.getPurchaseId(), orderRequestTime);
            }
        });

        return purchaseList.stream().map(e -> new OrderRequestResDto(e, orderRequestTimeMap.get(e.getId())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderRequestResDto> getMakingOrders() {
        List<OrderRequest> makingOrderList = orderRequestRepository.findAllByOrderStatusAndStoreIdAndIsDeletedFalseAndIsCanceledFalseOrderByOrderRequestDttmAsc(OrderStatus.MAKING, clientInfoLoader.getStoreId());
        List<Purchase> purchaseList = purchaseRepository.findAllByIdIn(
                makingOrderList.stream().map(OrderRequest::getPurchaseId).collect(Collectors.toList()));

        Map<Long, String> orderRequestTimeMap = new HashMap<>();

        makingOrderList.forEach(e -> {
            String orderRequestTime = redisRepository.getOrderRequestTime(e.getPurchaseId());
            if (orderRequestTime != null) {
                orderRequestTimeMap.put(e.getPurchaseId(), orderRequestTime);
            }
        });

        return purchaseList.stream().map(e -> new OrderRequestResDto(e, orderRequestTimeMap.get(e.getId())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderRequestResDto> getCompletedOrders() {
        List<OrderRequest> completedOrderList = orderRequestRepository.findTop5ByOrderStatusAndStoreIdAndIsDeletedFalseOrderByOrderRequestDttmDesc(OrderStatus.COMPLETED, clientInfoLoader.getStoreId());
        List<Purchase> purchaseList = purchaseRepository.findAllByIdIn(
                completedOrderList.stream().map(OrderRequest::getPurchaseId).collect(Collectors.toList()));

        Map<Long, String> orderRequestTimeMap = new HashMap<>();

        completedOrderList.forEach(e -> {
            String orderRequestTime = redisRepository.getOrderRequestTime(e.getPurchaseId());
            if (orderRequestTime != null) {
                orderRequestTimeMap.put(e.getPurchaseId(), orderRequestTime);
            }
        });

        return purchaseList.stream().map(e -> new OrderRequestResDto(e, orderRequestTimeMap.get(e.getId())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public OrderStatusResDto getOrderStatus(Long purchaseId) {
        return new OrderStatusResDto(redisRepository.getOrderStatus(purchaseId));
    }

    @Override
    public Boolean progressToMakingOrderStatus(Long purchaseId) {
        String orderStatus = redisRepository.getOrderStatus(purchaseId);
        if (!OrderStatus.NOT_CONFIRM.getValue().equals(orderStatus) && orderStatus != null) {
            // '제조확인중' 상태가 아닌 경우 실패
            log.info("Not in NOT_CONFIRM state");
            return false;
        }
        String getOrderStatusBeforeSet = redisRepository.getSetOrderStatus(purchaseId, OrderStatus.MAKING);
        if (!OrderStatus.NOT_CONFIRM.getValue().equals(getOrderStatusBeforeSet) && orderStatus != null) {
            // '제조중' 변경 실패 (도중에 취소요청이 들어온 경우)
            log.info("Failed to change to MAKING");
            redisRepository.setOrderStatus(purchaseId, OrderStatus.findByValue(getOrderStatusBeforeSet));
            return false;
        }
        try {
            if (orderRequestRepository.findByOrderStatusAndPurchaseId(OrderStatus.MAKING, purchaseId) != null) {
                throw CommonException.builder().build();
            }
            // '제조중' 변경 성공
            // 매장 본인 확인
            storeIdentificationByPurchaseId(purchaseId);
            // 주문 MAKING으로 저장
            OrderRequest orderRequest = OrderRequest.builder()
                    .purchaseId(purchaseId)
                    .orderStatus(OrderStatus.MAKING)
                    .build();
            Store store = storeRepository.findById(clientInfoLoader.getStoreId()).get()
                    .linkToOrderRequest(orderRequest);
            orderRequestRepository.save(orderRequest);
            // 기존 주문 완료처리
            orderRequestRepository.findByOrderStatusAndPurchaseId(OrderStatus.NOT_CONFIRM, purchaseId)
                    .processDone();
            // 유저에게 push 알림
            return true;
        } catch (NullPointerException e) {
            log.warn("Failed to change MAKING Status, purchaseId={}", purchaseId);
        } catch (CommonException e) {
            log.warn("Failed to change MAKING Status, because the data already exists, purchaseId={}", purchaseId);
        }
        redisRepository.setOrderStatus(purchaseId, OrderStatus.findByValue(getOrderStatusBeforeSet));
        return false;
    }

    @Override
    public Boolean progressToCompletedOrderStatus(Long purchaseId) {
        String orderStatus = redisRepository.getOrderStatus(purchaseId);
        if (!OrderStatus.MAKING.getValue().equals(orderStatus)) {
            // '제조중' 상태가 아닌 경우 실패
            log.info("Not in MAKING state");
            return false;
        }
        try {
            if (orderRequestRepository.findByOrderStatusAndPurchaseId(OrderStatus.COMPLETED, purchaseId) != null) {
                throw CommonException.builder().build();
            }
            // 매장 본인 확인
            Purchase purchase = storeIdentificationByPurchaseId(purchaseId);
            // 주문 COMPLETED로 저장
            OrderRequest orderRequest = OrderRequest.builder()
                    .purchaseId(purchaseId)
                    .orderStatus(OrderStatus.COMPLETED)
                    .build();
            storeRepository.findById(clientInfoLoader.getStoreId()).get()
                    .linkToOrderRequest(orderRequest);
            orderRequestRepository.save(orderRequest);
            // 기존 주문 완료처리
            orderRequestRepository.findByOrderStatusAndPurchaseId(OrderStatus.MAKING, purchaseId)
                    .processDone();
            // 유저 등급 경험치 반영
            increaseUserExp(purchase.getUserAccountId(), purchase.getPurchaseTotalPrice());
            // 대기시간 감소
            redisRepository.decreaseWaitingTime(purchase.getStoreId(), purchase.getPurchaseMenuList().size());
            // '제조완료' 변경
            redisRepository.setOrderStatus(purchaseId, OrderStatus.COMPLETED);
            // 유저에게 push 알림
            return true;
        } catch (NullPointerException e) {
            log.warn("Failed to change COMPLETED Status, purchaseId={}", purchaseId);
        } catch (CommonException e) {
            log.warn("Failed to change COMPLETED Status, because the data already exists, purchaseId={}", purchaseId);
        }
        redisRepository.setOrderStatus(purchaseId, OrderStatus.MAKING);
        return false;
    }

    @Override
    public Boolean cancelOrderByUser(Long purchaseId) {
        // 본인확인
        Purchase purchase = userIdentificationByPurchaseId(purchaseId);
        // OrderStatus 확인
        String orderStatus = redisRepository.getOrderStatus(purchaseId);
        if (!OrderStatus.NOT_CONFIRM.getValue().equals(orderStatus) && orderStatus != null) {
            log.info("Not in NOT_CONFIRM state");
            return false;
        }
        String getOrderStatusBeforeSet = redisRepository.getSetOrderStatus(purchaseId, OrderStatus.CANCELED);
        if (!OrderStatus.NOT_CONFIRM.getValue().equals(getOrderStatusBeforeSet) && orderStatus != null) {
            // 도중에 상태가 변경되어 '결제 취소' 실패
            log.info("Failed to change to CANCELED");
            redisRepository.setOrderStatus(purchaseId, OrderStatus.findByValue(getOrderStatusBeforeSet));
            return false;
        }
        // '주문 취소' 성공
        // 주문 취소처리
        orderRequestRepository.findByOrderStatusAndPurchaseId(OrderStatus.NOT_CONFIRM, purchaseId)
                .orderCancel();
        // 유저의 환불 이벤트 발행 -> 생략
        // 매장에 주문 취소 푸시알림
        // 대기시간 감소
        redisRepository.decreaseWaitingTime(purchase.getStoreId(), purchase.getPurchaseMenuList().size());
        return true;
    }

    @Override
    public Boolean cancelOrderByStore(Long purchaseId) {
        // 매장 확인
        Purchase purchase = storeIdentificationByPurchaseId(purchaseId);
        // OrderStatus 확인
        String orderStatus = redisRepository.getOrderStatus(purchaseId);
        if (!OrderStatus.NOT_CONFIRM.getValue().equals(orderStatus) && orderStatus != null) {
            log.info("Not in NOT_CONFIRM state");
            return false;
        }
        String getOrderStatusBeforeSet = redisRepository.getSetOrderStatus(purchaseId, OrderStatus.CANCELED);
        if (!OrderStatus.NOT_CONFIRM.getValue().equals(getOrderStatusBeforeSet) && orderStatus != null) {
            // 도중에 상태가 변경되어 '결제 취소' 실패
            log.info("Failed to change to CANCELED");
            redisRepository.setOrderStatus(purchaseId, OrderStatus.findByValue(getOrderStatusBeforeSet));
            return false;
        }
        // '주문 취소' 성공
        // 주문 취소처리
        orderRequestRepository.findByOrderStatusAndPurchaseId(OrderStatus.NOT_CONFIRM, purchaseId)
                .orderCancel();
        // 유저의 환불 이벤트 발행
        // 대기시간 감소
        redisRepository.decreaseWaitingTime(purchase.getStoreId(), purchase.getPurchaseMenuList().size());
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public WaitingTimeResDto getWaitingTime(Long storeId) {
        Long waitingTime = redisRepository.getWaitingTime(storeId);
        return waitingTime == null ? new WaitingTimeResDto(0L) : new WaitingTimeResDto(waitingTime);
    }

    // 유저 본인의 주문이 맞는지 확인
    private Purchase userIdentificationByPurchaseId(Long purchaseId) {
        Optional<Purchase> purchase = purchaseRepository.findById(purchaseId);
        if (!purchase.isPresent() ||
                clientInfoLoader.getUserAccountId() != purchase.get().getUserAccountId()) {
            throw CommonException.builder().errorCode(5004).httpStatus(HttpStatus.UNAUTHORIZED).build();
        }
        return purchase.get();
    }

    // 본인 매장의 주문이 맞는지 확인
    private Purchase storeIdentificationByPurchaseId(Long purchaseId) {
        Optional<Purchase> purchase = purchaseRepository.findById(purchaseId);
        if (!purchase.isPresent() ||
                clientInfoLoader.getStoreId() != purchase.get().getStoreId()) {
            throw CommonException.builder().errorCode(5005).httpStatus(HttpStatus.UNAUTHORIZED).build();
        }
        return purchase.get();
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

    // == dev 코드 ==
    @Override
    public void orderBatchProcessing() {
        try {
            List<OrderRequest> orderList = orderRequestRepository.findAllByOrderStatusAndStoreIdAndIsDeletedFalseAndIsCanceledFalseOrderByOrderRequestDttmAsc(OrderStatus.NOT_CONFIRM, clientInfoLoader.getStoreId());
            orderList.forEach(e -> {
                progressToMakingOrderStatus(e.getPurchaseId());
                progressToCompletedOrderStatus(e.getPurchaseId());
            });
        } catch (Exception e) {
            log.warn("Occurred exception from orderBatchProcessing");
        }

    }
}
