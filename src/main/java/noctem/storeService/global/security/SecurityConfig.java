package noctem.storeService.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import noctem.storeService.AppConfig;
import noctem.storeService.global.security.filter.JwtRequestProcessingFilter;
import noctem.storeService.global.security.handler.JwtAuthenticationFailureHandler;
import noctem.storeService.global.security.handler.JwtAuthenticationSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final String LOGIN_PROCESSING_URL = "/api/store-service/login";
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final ObjectMapper objectMapper = AppConfig.objectMapper();
    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();
        http.logout().disable();
        http.httpBasic().disable();
        http.formLogin().disable();
        http.headers().frameOptions().disable();
        http.rememberMe().disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("${global.api.base-path}/**").permitAll();

        http.addFilterAfter(new JwtRequestProcessingFilter("${global.api.base-path}/*"), SecurityContextPersistenceFilter.class);
        http.addFilterBefore(corsFilter, SecurityContextPersistenceFilter.class);
        applyJwtConfigurer(http);
    }

    private void applyJwtConfigurer(HttpSecurity http) throws Exception {
        http.apply(new JwtLoginConfigurer<>(LOGIN_PROCESSING_URL))
                .addAuthenticationManager(authenticationManager())
                .loginSuccessHandler(jwtAuthenticationSuccessHandler)
                .loginFailureHandler(jwtAuthenticationFailureHandler)
                .objectMapper(objectMapper);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider);
    }
}
