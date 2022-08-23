package hrp.auth.usecase;

import hrp.auth.dto.SignupUserDTO;
import hrp.auth.persistence.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.HttpMessageNotReadableException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SignupUserUsecaseTest {

  @InjectMocks
  private SignupUserUsecase signupUserUsecase;

  @Mock
  private CreateUserUsecase createUserUsecase;

  @Captor
  private ArgumentCaptor<User> userCaptor;


  @Test
  public void testSignupUserNonMatchingConfirmation() {
    String username = "fakeUsername";
    String password = "fakePassword";
    String email = "fakemail@gmail.com";
    SignupUserDTO signupUserDTO = new SignupUserDTO();
    signupUserDTO.setUsername(username);
    signupUserDTO.setEmail(email);
    signupUserDTO.setPassword(password);
    signupUserDTO.setPasswordConfirmation(password+"1");

    assertThrows(
            HttpMessageNotReadableException.class,
            () -> signupUserUsecase.execute(signupUserDTO)
    );

    verify(createUserUsecase, never()).execute(any());
  }

  @Test
  public void testSignupUser() {
    String username = "fakeUsername";
    String password = "fakePassword";
    String email = "fakemail@gmail.com";
    SignupUserDTO signupUserDTO = new SignupUserDTO();
    signupUserDTO.setUsername(username);
    signupUserDTO.setEmail(email);
    signupUserDTO.setPassword(password);
    signupUserDTO.setPasswordConfirmation(password);

    signupUserUsecase.execute(signupUserDTO);

    verify(createUserUsecase, times(1)).execute(userCaptor.capture());

    User capturedUser = userCaptor.getValue();

    assertThat(capturedUser.getUsername(), is(signupUserDTO.getUsername()));
    assertThat(capturedUser.getEmail(), is(signupUserDTO.getEmail()));
    assertThat(capturedUser.getPassword(), is(signupUserDTO.getPassword()));
    assertThat(capturedUser.getPassword(), is(signupUserDTO.getPasswordConfirmation()));
  }



}
