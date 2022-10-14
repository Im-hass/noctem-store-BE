package noctem.storeService.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.global.common.SecurityCustomException;
import noctem.storeService.global.security.dto.SecurityLoginReqDto;
import noctem.storeService.global.security.token.JwtAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper;

    public JwtLoginProcessingFilter(String processUrl) {
        super(new AntPathRequestMatcher(processUrl, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        log.info("{} {} Login Request", request.getMethod(), request.getRequestURI());
        // request type 검증
        if (!isJson(request) || !request.getMethod().equalsIgnoreCase("POST")) {
            log.warn("Login request: Wrong Method or Type");
            throw new SecurityCustomException(2015, HttpStatus.METHOD_NOT_ALLOWED);
        }

        SecurityLoginReqDto loginReqDto = objectMapper.readValue(request.getReader(), SecurityLoginReqDto.class);
        // request parameter 검증
        if (!StringUtils.hasText(loginReqDto.getLoginId()) || !StringUtils.hasText(loginReqDto.getPassword())) {
            log.warn("Login request: Null arguments");
            throw new SecurityCustomException(2016, HttpStatus.BAD_REQUEST);
        }

        // 임시token 생성
        JwtAuthenticationToken tempToken = new JwtAuthenticationToken(loginReqDto);
        Authentication formalToken = getAuthenticationManager().authenticate(tempToken);
        tempToken.eraseCredentials();
        return formalToken;
    }

    private boolean isJson(HttpServletRequest request) {
        String headerType = request.getHeader("Content-Type").toLowerCase();
        return headerType.contains(MediaType.APPLICATION_JSON_VALUE);
    }

    public void setObjectMapper(ObjectMapper mapper) {
        this.objectMapper = mapper;
    }
}
