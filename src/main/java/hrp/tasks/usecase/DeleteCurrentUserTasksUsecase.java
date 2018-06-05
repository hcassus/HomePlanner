package hrp.tasks.usecase;

import hrp.auth.gateways.SessionDataGateway;
import hrp.tasks.gateways.TaskGateway;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCurrentUserTasksUsecase {

  private final TaskGateway taskGateway;
  private final SessionDataGateway sessionDataGateway;

  public void execute(UUID uuid) {
    taskGateway.deleteTaskByUuid(uuid, sessionDataGateway.getAuthenticatedUsername());
  }
}
