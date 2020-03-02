package stoner.tspringcloud.histrix.auth;


import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {
	
	public FeignBasicAuthRequestInterceptor() {

	}

	public void apply(RequestTemplate template) {
		System.err.println("进入拦截器了");
	}

	
}
