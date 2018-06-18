package hrp.auth.usecase;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import hrp.auth.gateways.UserGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class EnableUserUsecaseTest {

  @InjectMocks
  private EnableUserUsecase enableUserUsecase;

  @Mock
  private UserGateway userGateway;

  @Mock
  private PasswordEncoder encoder;

  @Test
  public void createUserUsecaseTest() {
    String user = System.getenv("VALID_USERNAME");

    enableUserUsecase.execute(user);

    verify(userGateway, times(1)).enableUser(user);
  }

}
