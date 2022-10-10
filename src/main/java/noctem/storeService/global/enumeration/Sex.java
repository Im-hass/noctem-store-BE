package noctem.storeService.global.enumeration;

import noctem.storeService.global.common.CommonException;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Sex {
    MALE("남자"),
    FEMALE("여자");

    private final String value;

    Sex(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, Sex> VALUE_MAP = Stream.of(values()).collect(Collectors.toMap(Sex::getValue, e -> e));

    public static Sex findByValue(String value) {
        if (!VALUE_MAP.containsKey(value)) {
            throw CommonException.builder().errorCode(2010).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        return VALUE_MAP.get(value);
    }
}
