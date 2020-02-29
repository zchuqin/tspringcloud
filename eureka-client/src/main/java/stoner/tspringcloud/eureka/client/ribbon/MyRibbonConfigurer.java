package stoner.tspringcloud.eureka.client.ribbon;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import stoner.tspringcloud.eureka.client.config.RestTemplateConfig;

@RibbonClient(name = "eureka-client", configuration = MyRibbonConfig.class)
public class MyRibbonConfigurer {
}
