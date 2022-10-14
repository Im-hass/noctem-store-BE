package noctem.storeService.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import noctem.storeService.global.common.CommonResponse;
import noctem.storeService.global.security.auth.UserDetailsImpl;
import noctem.storeService.global.security.token.JwtAuthenticationToken;
import noctem.storeService.store.service.StoreAccountService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;
    private final StoreAccountService storeAccountService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader(JwtAuthenticationToken.JWT_HEADER, token.getJwt());
        objectMapper.writeValue(response.getWriter(), CommonResponse.builder().data(true).build());

        // 로그인 시간 갱신
        UserDetailsImpl userDetails = (UserDetailsImpl) token.getPrincipal();
        storeAccountService.updateLastAccessTime(userDetails.getId());
    }
}
