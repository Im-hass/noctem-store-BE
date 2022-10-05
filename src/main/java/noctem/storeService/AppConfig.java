package noctem.storeService;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    public static ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
