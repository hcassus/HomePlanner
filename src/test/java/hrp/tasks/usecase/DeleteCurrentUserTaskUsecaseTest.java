package hrp.tasks.usecase;

import hrp.auth.gateways.SessionDataGateway;
import hrp.tasks.gateways.TaskGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteCurrentUserTaskUsecaseTest {

  @InjectMocks
  private DeleteCurrentUserTasksUsecase deleteCurrentUserTasksUsecase;

  @Mock
  private SessionDataGateway sessionDataGateway;

  @Mock
  private TaskGateway gateway;

  @Test
  public void noBarCodeCreatesNoProductTest(){
    String authenticatedUser = "user";
    UUID taskUuid = UUID.randomUUID();
    when(sessionDataGateway.getAuthenticatedUsername()).thenReturn(authenticatedUser);

    deleteCurrentUserTasksUsecase.execute(taskUuid);

    verify(gateway, times(1)).deleteTaskByUuid(taskUuid, authenticatedUser);
    verify(sessionDataGateway, times(1)).getAuthenticatedUsername();
  }
}
