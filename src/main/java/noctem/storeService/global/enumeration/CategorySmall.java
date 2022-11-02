package noctem.storeService.global.enumeration;

import noctem.storeService.global.common.CommonException;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CategorySmall {
    REFRESHER("리프레셔"),
    COLD_BREW("콜드 브루"),
    BLONDE("블론드"),
    ESPRESSO("에스프레소"),
    DECAFFEINE("디카페인 커피"),
    PRAPPUCCINO("프라푸치노"),
    BLENDED("블렌디드"),
    FIZZIO("피지오"),
    TEAVANA("티바나");
    private final String value;

    CategorySmall(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, CategorySmall> VALUE_MAP = Stream.of(values()).collect(Collectors.toMap(CategorySmall::getValue, e -> e));

    public static CategorySmall findByValue(String value) {
        if (!VALUE_MAP.containsKey(value)) {
            throw CommonException.builder().errorCode(6003).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        return VALUE_MAP.get(value);
    }
}

