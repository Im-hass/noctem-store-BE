package noctem.storeService.global.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class CommonException extends RuntimeException {
    private Integer errorCode;
    private HttpStatus httpStatus;
}
