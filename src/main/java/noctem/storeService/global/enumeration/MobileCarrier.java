package noctem.storeService.global.enumeration;

import noctem.storeService.global.common.CommonException;
import org.springframework.http.HttpStatus;

public enum MobileCarrier {
    SKT("SKT"),
    KT("KT"),
    LGUPLUS("LGU+"),
    CONSTRUCT(null);

    private String value;

    MobileCarrier(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public MobileCarrier findInstance(String str) {
        switch (str.strip().toUpperCase()) {
            case "SKT":
                return MobileCarrier.SKT;
            case "KT":
                return MobileCarrier.KT;
            case "LGU+":
            case "LGUPLUS":
                return MobileCarrier.LGUPLUS;
            default:
                throw CommonException.builder().errorCode(2009).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }
}
