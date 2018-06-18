package hrp.auth.controllers;

import hrp.auth.persistence.entities.User;
import hrp.auth.usecase.CreateUserUsecase;
import hrp.auth.usecase.EnableUserUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/admin/user")
@RequiredArgsConstructor
public class UserController {

  private final CreateUserUsecase createUserUsecase;
  private final EnableUserUsecase enableUserUsecase;

  @RequestMapping(method = RequestMethod.POST)
  public User createUser(@RequestBody User user) {
    return createUserUsecase.execute(user);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/enable")
  public void createUser(@RequestParam("username") String username) {
    enableUserUsecase.execute(username);
  }

}
