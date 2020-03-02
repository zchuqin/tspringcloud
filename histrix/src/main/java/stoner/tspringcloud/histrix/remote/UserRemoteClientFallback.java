package stoner.tspringcloud.histrix.remote;

import org.springframework.stereotype.Component;

@Component
public class UserRemoteClientFallback implements UserRemoteClient {

	@Override
	public String hello(String name) {
		return name + " fail";
	}

}
