package stoner.tspringcloud.histrix.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//fallback=UserRemoteClientFallback.class
@FeignClient(value="eureka-client", fallbackFactory=UserRemoteClientFallbackFactory.class)
public interface UserRemoteClient {

	@GetMapping("/article/callHello")
	String hello(@RequestParam String name);
	
}
