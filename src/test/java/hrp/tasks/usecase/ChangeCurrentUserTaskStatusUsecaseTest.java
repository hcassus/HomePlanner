package hrp.tasks.usecase;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hrp.auth.gateways.SessionDataGateway;
import hrp.tasks.gateways.TaskGateway;
import hrp.tasks.persistence.Task;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChangeCurrentUserTaskStatusUsecaseTest {

  @InjectMocks
  private ChangeCurrentUserTaskStatusUsecase changeCurrentUserTaskStatusUsecase;

  @Mock
  private SessionDataGateway sessionDataGateway;

  @Mock
  private TaskGateway gateway;

  @Test
  public void noBarCodeCreatesNoProductTest(){
    String authenticatedUser = "user";
    UUID taskUuid = UUID.randomUUID();
    int status = 1;

    Task task = new Task("Test task");
    task.setStatus(status);
    when(sessionDataGateway.getAuthenticatedUsername()).thenReturn(authenticatedUser);

    changeCurrentUserTaskStatusUsecase.execute(taskUuid, task);

    verify(gateway, times(1)).changeTaskStatus(taskUuid, status, authenticatedUser);
    verify(sessionDataGateway, times(1)).getAuthenticatedUsername();
  }
}