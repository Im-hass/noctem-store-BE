package noctem.storeService.global.enumeration;

import noctem.storeService.global.common.CommonException;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum OrderStatus {
    NOT_CONFIRM("주문확인중"),
    MAKING("제조중"),
    COMPLETED("제조완료"),
    CANCELED("취소됨");

    private String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, OrderStatus> VALUE_MAP = Stream.of(values()).collect(Collectors.toMap(OrderStatus::getValue, e -> e));

    public static OrderStatus findByValue(String value) {
        if (!VALUE_MAP.containsKey(value)) {
            throw CommonException.builder().errorCode(5003).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        return VALUE_MAP.get(value);
    }
}
