package noctem.storeService.global.enumeration;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Amount {
    STORE_CUP("매장컵"),
    PERSONAL_CUP("개인컵"),
    DISPOSABLE_CUP("일회용컵"),
    WITHOUT("없이"),
    LESS("적게"),
    DEFAULT("보통"),
    A_LOT_OF("많이"),
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9");

    private String value;

    Amount(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, Amount> VALUE_MAP = Stream.of(values()).collect(Collectors.toMap(Amount::getValue, e -> e));

    public static Amount findByValue(String value) {
        return VALUE_MAP.get(value);
    }
}
