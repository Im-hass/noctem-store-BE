package noctem.storeService.store.domain.repository;

import noctem.storeService.global.enumeration.OrderStatus;
import noctem.storeService.store.domain.entity.OrderRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRequestRepository extends JpaRepository<OrderRequest, Long> {
    // 주문확인중, 제조중 조회시 빠른 시간순으로 정렬
    List<OrderRequest> findAllByOrderStatusAndStoreIdAndIsDeletedFalseOrderByOrderRequestDttmAsc(OrderStatus orderStatus, Long storeId);

    // 제조완료 조회시 최근 시간순으로 정렬하여 5개만 가져옴
    List<OrderRequest> findTop5ByOrderStatusAndStoreIdAndIsDeletedFalseOrderByOrderRequestDttmDesc(OrderStatus orderStatus, Long storeId);

    OrderRequest findByOrderStatusAndPurchaseIdAndIsDeletedFalse(OrderStatus orderStatus, Long purchaseId);
}
