package hrp.auth.usecase;

import hrp.auth.gateways.UserGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
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
