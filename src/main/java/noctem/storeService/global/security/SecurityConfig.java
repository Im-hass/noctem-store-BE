package noctem.storeService.global.security;

import lombok.RequiredArgsConstructor;
import noctem.storeService.global.security.filter.JwtRequestProcessingFilter;
import org.springframework.context.annotation.Configuration;
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
    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
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
    }
}
