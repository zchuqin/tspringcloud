package stoner.tspringcloud.eureka.client.rest;

import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.*;
import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import stoner.tspringcloud.eureka.client.bean.User;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class BeanController {
    @Resource(name = "restTemplateLoadBalanced")
    private RestTemplate restTemplateLoadBalanced;

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    Environment environment;


    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private EurekaClient eurekaClient;

    @GetMapping("/serviceUrl")
    @ResponseBody
    public Object serviceUrl() {
//        return eurekaClient.getInstancesByVipAddress("eureka-client", false);
        return discoveryClient.getInstances("eureka-client");
    }

    @GetMapping("/article/callHello")
    @ResponseBody
    public JSONObject callHello(@RequestParam String name) {
        System.out.println(restTemplate == restTemplateLoadBalanced);
        System.out.println("进来了 /article/callHello?name="+name);
        return restTemplateLoadBalanced.exchange(
//                "http://127.0.0.1:8080/get/peter", HttpMethod.GET, getHttpEntity(), JSONObject.class).getBody();
                "http://eureka-client/article/callHello2?name={name}", HttpMethod.GET, null, JSONObject.class,name).getBody();
    }

    @GetMapping("/article/callHello2")
    @ResponseBody
    public JSONObject callHello2(@RequestParam String name) {
        System.out.println(environment.getProperty("eureka.instance.instance-id"));
        System.out.println(restTemplate == restTemplateLoadBalanced);
        System.out.println("name = " + name);
        return restTemplate.exchange(
                "http://127.0.0.1:8080/get/{name}", HttpMethod.GET, null, JSONObject.class, name).getBody();
    }

    private static HttpEntity getHttpEntity() {
        // 要进行一个Http头信息配置
        HttpHeaders headers = new HttpHeaders();
        String auth = "user:234";
        byte[] encodedAuth = Base64.encodeBase64((auth.getBytes(StandardCharsets.US_ASCII))); // 进行一个加密的处理
        // 在进行授权的头信息内容配置的时候加密的信息一定要与“Basic”之间有一个空格
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        return new HttpEntity(headers);
    }

    private static LoadingCache<String, User> getUserCache() {
        return CacheBuilder.newBuilder()
                .concurrencyLevel(8)
                .expireAfterWrite(8, TimeUnit.SECONDS)
                .refreshAfterWrite(1, TimeUnit.SECONDS)
                .initialCapacity(5)
                .maximumSize(100)
                .recordStats()
                .removalListener((RemovalListener<String, User>) removalNotification -> System.out.println(removalNotification.getKey() + " 被移除了，原因： " + removalNotification.getCause()))
                .build(new CacheLoader<String, User>() {
                    @Override
                    public User load(String s) throws Exception {
                        System.out.println("缓存没有时，从数据库加载" + s);
                        return new User("tony-" + s);
                    }
                });
    }

}
