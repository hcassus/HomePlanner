package hrp.auth.dto;

import lombok.Data;

@Data
public class SignupUserDTO {

  private String username;
  private String email;
  private String password;
  private String passwordConfirmation;

}
