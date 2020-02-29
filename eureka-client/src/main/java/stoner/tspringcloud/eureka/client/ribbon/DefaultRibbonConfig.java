package stoner.tspringcloud.eureka.client.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class DefaultRibbonConfig {
    @Bean
    public IRule ribbonRule() {
        return new RandomRule();
    }
}
