package stoner.tspringcloud.feign.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import stoner.tspringcloud.feign.client.UserRemoteClient;

@Controller
public class TestController {
    @Autowired
    private UserRemoteClient userRemoteClient;

    @GetMapping("/callHello")
    @ResponseBody
    public String callHello(@RequestParam String name) {
        String result = userRemoteClient.hello(name);
        System.out.println(" 调用结果：" + result);
        return result;
    }
}
