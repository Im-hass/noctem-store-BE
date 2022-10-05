package noctem.storeService.global.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class CommonRequest<T> {
    private T data;
    private Integer errorCode;
    private HttpStatus httpStatus;
}
