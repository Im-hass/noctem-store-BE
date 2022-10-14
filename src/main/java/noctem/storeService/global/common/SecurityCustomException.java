package noctem.storeService.global.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class SecurityCustomException extends AuthenticationException {
    private Integer errorCode;
    private HttpStatus httpStatus;

    public SecurityCustomException(Integer errorCode, HttpStatus httpStatus) {
        super(null);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
