package noctem.storeService.store.dto.response;

import lombok.Data;

/***
 * orderNumber: 매장의 주문번호
 * turnNumber: 대기 순서. 자기 앞에 몇명있는지.
 */
@Data
public class WaitingTimeUserResDto {
    private String orderNumber;
    private Integer turnNumber;
    private Long waitingTime;

    public WaitingTimeUserResDto(Integer orderNumber, Integer turnNumber, Long waitingTime) {
        this.orderNumber = orderNumber == null ? null : String.format("A-%d", orderNumber);
        this.turnNumber = turnNumber;
        this.waitingTime = waitingTime;
    }
}
