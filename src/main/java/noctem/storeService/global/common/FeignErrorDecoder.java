package noctem.storeService.global.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.AppConfig;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Slf4j
@Component
public class FeignErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = AppConfig.objectMapper();

    @Override
    public CommonException decode(String methodKey, Response response) {
        try {
            CommonResponse commonRequest = objectMapper.readValue(
                    CharStreams.toString(response.body().asReader(Charset.defaultCharset())),
                    CommonResponse.class);
            return CommonException.builder()
                    .errorCode(commonRequest.getErrorCode())
                    .httpStatus(HttpStatus.valueOf(response.status()))
                    .build();
        } catch (Exception e) {
            return CommonException.builder()
                    .errorCode(5001)
                    .httpStatus(HttpStatus.valueOf(response.status()))
                    .build();
        }
    }
}
