package noctem.storeService.global.enumeration;

import noctem.storeService.global.common.CommonException;
import org.springframework.http.HttpStatus;

public enum Grade {
    TALL("Tall", 0),
    GRANDE("Grande", 100000),
    VENTI("Venti", 400000),
    CONSTRUCT(null, null);

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

    public Grade findInstance(String str) {
        switch (str.strip().toUpperCase()) {
            case "TALL":
                return Grade.TALL;
            case "GRANDE":
                return Grade.GRANDE;
            case "VENTI":
                return Grade.VENTI;
            default:
                throw CommonException.builder().errorCode(2005).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }
}
