package noctem.storeService.global.enumeration;

import noctem.storeService.global.common.CommonException;
import org.springframework.http.HttpStatus;

public enum Sex {
    MALE("MALE", "남자"),
    FEMALE("FEMALE", "여자"),
    CONSTRUCT(null, null);

    private String enValue;
    private String koValue;

    Sex(String enValue, String koValue) {
        this.enValue = enValue;
        this.koValue = koValue;
    }

    public String getEnValue() {
        return enValue;
    }

    public String getKoValue() {
        return koValue;
    }

    public Sex findInstance(String str) {
        switch (str.strip().toUpperCase()) {
            case "M":
            case "MALE":
            case "남자":
                return Sex.MALE;
            case "F":
            case "FEMALE":
            case "여자":
                return Sex.FEMALE;
            default:
                throw CommonException.builder().errorCode(2010).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }
}
