package stoner.tspringcloud.eureka.client.loadbalance;

import org.apache.commons.codec.binary.Base64;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class MyLoadBalancerInterceptor implements ClientHttpRequestInterceptor {

	
	private LoadBalancerClient loadBalancer;
	private LoadBalancerRequestFactory requestFactory;

	public MyLoadBalancerInterceptor(LoadBalancerClient loadBalancer, LoadBalancerRequestFactory requestFactory) {
		this.loadBalancer = loadBalancer;
		this.requestFactory = requestFactory;
	}

	public MyLoadBalancerInterceptor(LoadBalancerClient loadBalancer) {
		this(loadBalancer, new LoadBalancerRequestFactory(loadBalancer));
	}

	@Override
	public ClientHttpResponse intercept(final HttpRequest request, final byte[] body,
                                        final ClientHttpRequestExecution execution) throws IOException {
		final URI originalUri = request.getURI();
		String serviceName = originalUri.getHost();
		System.out.println("进入自定义的请求拦截器中" + serviceName);
		String auth = "user:234";
		byte[] encodedAuth = Base64.encodeBase64((auth.getBytes(StandardCharsets.US_ASCII))); // 进行一个加密的处理
		// 在进行授权的头信息内容配置的时候加密的信息一定要与“Basic”之间有一个空格
		String authHeader = "Basic " + new String(encodedAuth);
		request.getHeaders().add("Authorization",authHeader);
		Assert.state(serviceName != null, "Request URI does not contain a valid hostname: " + originalUri);
		return execution.execute(request, body);
//		return this.loadBalancer.execute(serviceName, requestFactory.createRequest(request, body, execution));
	}

}
