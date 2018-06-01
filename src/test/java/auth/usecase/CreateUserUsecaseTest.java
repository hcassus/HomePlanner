package auth.usecase;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import hrp.auth.gateways.UserGateway;
import hrp.auth.persistence.entities.User;
import hrp.auth.usecase.CreateUserUsecase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class CreateUserUsecaseTest {

    @InjectMocks
    private CreateUserUsecase createUserUsecase;

    @Mock
    private UserGateway userGateway;

    @Mock
    private PasswordEncoder encoder;

    @Test
    public void createUserUsecaseTest(){
        String password = "FakePass";
        User user = new User("FakeName", password);

        createUserUsecase.execute(user);

        verify(userGateway, times(1)).createUser(user);
        verify(encoder, times(1)).encode(password);
    }

}
