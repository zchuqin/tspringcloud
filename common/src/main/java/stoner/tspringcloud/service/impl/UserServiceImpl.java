package stoner.tspringcloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stoner.tspringcloud.bean.User;
import stoner.tspringcloud.service.UserService;
import stoner.tspringcloud.task.TaskAsyncConfigurer;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TaskAsyncConfigurer taskAsyncConfigurer;

    @Override
    public User getUser(String name) {
        taskAsyncConfigurer.getAsyncExecutor().execute(()->{
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ": i = " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return new User(name);
    }
}
