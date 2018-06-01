package hrp.auth.controllers;

import hrp.auth.persistence.entities.User;
import hrp.auth.usecase.CreateUserUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController  {

    private final CreateUserUsecase createUserUsecase;

    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody User user){
       return createUserUsecase.execute(user);
    }

}
