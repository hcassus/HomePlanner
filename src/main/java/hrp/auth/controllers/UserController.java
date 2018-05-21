package hrp.auth.controllers;

import hrp.auth.persistence.entities.User;
import hrp.auth.usecase.CreateUserUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController  {

    private CreateUserUsecase createUserUsecase;

    @Autowired
    public UserController(CreateUserUsecase createUserUsecase){
        this.createUserUsecase = createUserUsecase;
    }

    @RequestMapping(method = RequestMethod.POST)
    public User createUser(User user){
       return createUserUsecase.execute(user);
    }

}
