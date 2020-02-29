package stoner.tspringcloud.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import stoner.tspringcloud.feign.config.FeignConfiguration;

@FeignClient(value="eureka-client", configuration= FeignConfiguration.class)
public interface UserRemoteClient {
    @GetMapping("/article/callHello")
    String hello(@RequestParam String name);
}
