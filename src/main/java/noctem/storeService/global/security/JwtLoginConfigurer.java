package noctem.storeService.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import noctem.storeService.global.security.filter.JwtLoginProcessingFilter;
import noctem.storeService.global.security.handler.JwtAuthenticationFailureHandler;
import noctem.storeService.global.security.handler.JwtAuthenticationSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class JwtLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractAuthenticationFilterConfigurer<H, JwtLoginConfigurer<H>, JwtLoginProcessingFilter> {

    private AuthenticationManager authenticationManager;
    private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private ObjectMapper objectMapper;
    private final String LOGIN_PROCESSING_METHOD = "POST";

    public JwtLoginConfigurer(String loginProcessingUrl) {
        super(new JwtLoginProcessingFilter(loginProcessingUrl), loginProcessingUrl);
    }

    @Override
    public void configure(H http) throws Exception {
        JwtLoginProcessingFilter jwtFilter = getAuthenticationFilter();
        jwtFilter.setAuthenticationManager(authenticationManager);
        jwtFilter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
        jwtFilter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);
        jwtFilter.setObjectMapper(objectMapper);

        SessionAuthenticationStrategy sessionStrategy = http.getSharedObject(SessionAuthenticationStrategy.class);
        if (sessionStrategy != null) {
            jwtFilter.setSessionAuthenticationStrategy(sessionStrategy);
        }

        http.setSharedObject(JwtLoginProcessingFilter.class, jwtFilter);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, LOGIN_PROCESSING_METHOD);
    }

    public JwtLoginConfigurer<H> loginSuccessHandler(JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler) {
        this.jwtAuthenticationSuccessHandler = jwtAuthenticationSuccessHandler;
        return this;
    }

    public JwtLoginConfigurer<H> loginFailureHandler(JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler) {
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
        return this;
    }

    public JwtLoginConfigurer<H> objectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        return this;
    }

    public JwtLoginConfigurer<H> addAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        return this;
    }
}
