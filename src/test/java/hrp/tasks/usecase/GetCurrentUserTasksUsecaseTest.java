package hrp.tasks.usecase;

import hrp.auth.gateways.SessionDataGateway;
import hrp.tasks.gateways.TaskGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCurrentUserTasksUsecaseTest {

  @InjectMocks
  private GetCurrentUserTasksUsecase getCurrentUserTasksUsecase;

  @Mock
  private SessionDataGateway sessionDataGateway;

  @Mock
  private TaskGateway gateway;

  @Test
  public void noBarCodeCreatesNoProductTest(){
    String authenticatedUser = "user";
    when(sessionDataGateway.getAuthenticatedUsername()).thenReturn(authenticatedUser);

    getCurrentUserTasksUsecase.execute();

    verify(gateway, times(1)).getAllTasks(authenticatedUser);
    verify(sessionDataGateway, times(1)).getAuthenticatedUsername();
  }
}
