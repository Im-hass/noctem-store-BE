package noctem.storeService.global.enumeration;

import noctem.storeService.global.common.CommonException;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MobileCarrier {
    SKT("SKT"),
    KT("KT"),
    LGUPLUS("LGU+");

    private final String value;

    MobileCarrier(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, MobileCarrier> VALUE_MAP = Stream.of(values()).collect(Collectors.toMap(MobileCarrier::getValue, e -> e));

    public static MobileCarrier findByValue(String value) {
        if (!VALUE_MAP.containsKey(value)) {
            throw CommonException.builder().errorCode(2009).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        return VALUE_MAP.get(value);
    }
}
