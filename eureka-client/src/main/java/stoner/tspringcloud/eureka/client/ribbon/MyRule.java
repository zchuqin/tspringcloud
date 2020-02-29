package stoner.tspringcloud.eureka.client.ribbon;

import com.alibaba.fastjson.JSON;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

public class MyRule implements IRule {

    private ILoadBalancer iLoadBalancer;

    @Override
    public Server choose(Object o) {
        List<Server> allServers = iLoadBalancer.getAllServers();
        System.out.println(allServers.size()+" ==> "+JSON.toJSONString(allServers));
        System.out.println(o);
//        Server server = iLoadBalancer.chooseServer(o); //这里调用chooseServer，会无限递归，内存溢出
        if (allServers.isEmpty()) return null;
        Server server = allServers.get((int) (Math.random()*allServers.size()));
        System.out.println("server.getHostPort" + server.getHostPort());
        return server;
    }

    @Override
    public void setLoadBalancer(ILoadBalancer iLoadBalancer) {
        this.iLoadBalancer = iLoadBalancer;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.iLoadBalancer;
    }
}
