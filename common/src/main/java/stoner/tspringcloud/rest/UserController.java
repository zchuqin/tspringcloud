package stoner.tspringcloud.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import stoner.tspringcloud.bean.User;
import stoner.tspringcloud.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/get/{name}")
    @ResponseBody
    public User getUser(@PathVariable String name) {
        return userService.getUser(name);
    }
}
