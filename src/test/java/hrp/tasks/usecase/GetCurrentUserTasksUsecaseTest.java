package hrp.tasks.usecase;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hrp.auth.gateways.SessionDataGateway;
import hrp.tasks.gateways.TaskGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
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