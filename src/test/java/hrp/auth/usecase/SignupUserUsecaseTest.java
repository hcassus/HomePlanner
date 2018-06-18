package hrp.auth.usecase;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import hrp.auth.dto.SignupUserDTO;
import hrp.auth.persistence.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.converter.HttpMessageNotReadableException;

@RunWith(MockitoJUnitRunner.class)
public class SignupUserUsecaseTest {

  @InjectMocks
  private SignupUserUsecase signupUserUsecase;

  @Mock
  private CreateUserUsecase createUserUsecase;

  @Captor
  private ArgumentCaptor<User> userCaptor;


  @Test(expected = HttpMessageNotReadableException.class)
  public void testSignupUserNonMatchingConfirmation() {
    String username = "fakeUsername";
    String password = "fakePassword";
    String email = "fakemail@gmail.com";
    SignupUserDTO signupUserDTO = new SignupUserDTO();
    signupUserDTO.setUsername(username);
    signupUserDTO.setEmail(email);
    signupUserDTO.setPassword(password);
    signupUserDTO.setPasswordConfirmation(password+"1");

    signupUserUsecase.execute(signupUserDTO);

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