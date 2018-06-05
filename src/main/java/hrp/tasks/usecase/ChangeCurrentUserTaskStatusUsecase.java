package hrp.tasks.usecase;

import hrp.auth.gateways.SessionDataGateway;
import hrp.tasks.gateways.TaskGateway;
import hrp.tasks.persistence.Task;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeCurrentUserTaskStatusUsecase {

  private final TaskGateway taskGateway;
  private final SessionDataGateway sessionDataGateway;

  public Task execute(UUID uuid, Task task) {
    return taskGateway.changeTaskStatus(uuid, task.getStatus(), sessionDataGateway.getAuthenticatedUsername());
  }
}
