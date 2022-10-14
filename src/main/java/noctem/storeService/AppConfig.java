package noctem.storeService;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Argon2PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    public static ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
