package stoner.tspringcloud.eureka.client.loadbalance;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class MyLoadBalancerAutoConfiguration {
	@Resource(name = "restTemplateLoadBalanced")
	private RestTemplate restTemplate;

	@Bean
	public MyLoadBalancerInterceptor myLoadBalancerInterceptor(LoadBalancerClient loadBalancerClient,
                                                               LoadBalancerRequestFactory requestFactory) {
		return new MyLoadBalancerInterceptor(loadBalancerClient,requestFactory);
	}

	@Bean
	public SmartInitializingSingleton myLoadBalancedRestTemplateInitializer(LoadBalancerClient loadBalancerClient,
                                                                            LoadBalancerRequestFactory requestFactory) {
		return new SmartInitializingSingleton() {
			public void afterSingletonsInstantiated() {
				List<ClientHttpRequestInterceptor> list = new ArrayList<>(restTemplate.getInterceptors());
				list.add(myLoadBalancerInterceptor(loadBalancerClient, requestFactory));
				restTemplate.setInterceptors(list);
			}
		};
	}
}
