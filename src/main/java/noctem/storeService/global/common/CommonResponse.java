package noctem.storeService.global.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class CommonResponse<T> {
    private T data;
    private Integer errorCode;
    private HttpStatus httpStatus;
}
