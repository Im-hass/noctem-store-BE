package noctem.storeService.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // 리액트에서 헤더받을 수 있게 설정
        config.setExposedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);
        // AllowCredentials이 true일 때 AllowedOrigin 사용못하므로 AllowedOriginPattern으로 대체
        config.addAllowedOriginPattern("*");
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"));
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
