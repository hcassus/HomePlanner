package hrp.auth.usecase;

import hrp.auth.dto.SignupUserDTO;
import hrp.auth.persistence.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignupUserUsecase {

  private final CreateUserUsecase createUserUsecase;

  public User execute(SignupUserDTO signupUser) {
    if(passwordsMatch(signupUser)){
      User user = new User();
      BeanUtils.copyProperties(signupUser, user);
      return createUserUsecase.execute(user);
    } else {
      throw new HttpMessageNotReadableException("Passwords Mismatch");
    }

  }

  private boolean passwordsMatch(SignupUserDTO signupUser) {
    return signupUser.getPassword().equals(signupUser.getPasswordConfirmation());
  }
}
