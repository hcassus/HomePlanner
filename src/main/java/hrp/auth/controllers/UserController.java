package hrp.auth.controllers;

import hrp.auth.persistence.entities.User;
import hrp.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController  {

    UserService service;

    @Autowired
    public UserController(UserService service){
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createUser(User user){
        service.createUser(user);
    }

}
