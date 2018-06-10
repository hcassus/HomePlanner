package hrp.auth.controllers;

import hrp.auth.dto.SignupUserDTO;
import hrp.auth.persistence.entities.User;
import hrp.auth.usecase.SignupUserUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/signup")
@RequiredArgsConstructor
public class SignupController {

    private final SignupUserUsecase signupUserUsecase;

    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody SignupUserDTO user){
       return signupUserUsecase.execute(user);
    }

}
