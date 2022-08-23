package hrp.auth.usecase;

import hrp.auth.gateways.UserGateway;
import hrp.auth.persistence.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateUserUsecaseTest {

  @InjectMocks
  private CreateUserUsecase createUserUsecase;

  @Mock
  private UserGateway userGateway;

  @Mock
  private PasswordEncoder encoder;

  @Test
  public void createUserUsecaseTest() {
    String password = "FakePass";
    User user = new User("FakeName", password, "fakeMail");

    createUserUsecase.execute(user);

    verify(userGateway, times(1)).createUser(user);
    verify(encoder, times(1)).encode(password);
  }

}
