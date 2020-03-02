package stoner.tspringcloud.histrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stoner.tspringcloud.histrix.remote.UserRemoteClient;

@RestController
public class DemoController {

	@Autowired
	private UserRemoteClient userRemoteClient;

	@GetMapping("/callHello")
	public String callHello(@RequestParam String name) {
		System.err.println("进来了。。。。。");
		//System.err.println(2/0);
		String result = userRemoteClient.hello(name);
		System.out.println(" 调用结果：" + result);
		return result;
	}

}
