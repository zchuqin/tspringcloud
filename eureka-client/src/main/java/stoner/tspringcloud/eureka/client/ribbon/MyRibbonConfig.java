package stoner.tspringcloud.eureka.client.ribbon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRibbonConfig {
    @Bean
    public MyRule rule() {
        return new MyRule();
    }
}
