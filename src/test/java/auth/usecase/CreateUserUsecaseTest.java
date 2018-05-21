package auth.usecase;

import hrp.auth.gateways.UserGateway;
import hrp.auth.persistence.entities.User;
import hrp.auth.usecase.CreateUserUsecase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateUserUsecaseTest {

    @InjectMocks
    private CreateUserUsecase createUserUsecase;

    @Mock
    private UserGateway userGateway;

    @Test
    public void createUserUsecaseTest(){
        User user = new User("FakeName", "FakePass");

        createUserUsecase.execute(user);

        verify(userGateway, times(1)).createUser(user);
    }

}
