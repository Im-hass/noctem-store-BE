package noctem.storeService.global.enumeration;

import noctem.storeService.global.common.CommonException;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Grade {
    POTION("Potion", 0),
    ELIXIR("Elixir", 100000),
    POWER_ELIXIR("Power Elixir", 400000);

    private String value;
    private Integer requiredAccumulateExp; // 해당 등급이 되기위해 필요 누적경험치
    public static Integer divisionRatio = 5000;

    Grade(String value, Integer requiredAccumulateExp) {
        this.value = value;
        this.requiredAccumulateExp = requiredAccumulateExp;
    }

    public String getValue() {
        return value;
    }

    public Integer getRequiredAccumulateExp() {
        return requiredAccumulateExp;
    }

    private static final Map<String, Grade> VALUE_MAP = Stream.of(values()).collect(Collectors.toMap(Grade::getValue, e -> e));

    public static Grade findByValue(String value) {
        if (!VALUE_MAP.containsKey(value)) {
            throw CommonException.builder().errorCode(2005).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        return VALUE_MAP.get(value);
    }
}
