package hrp.tasks.usecase;

import hrp.auth.gateways.SessionDataGateway;
import hrp.tasks.gateways.TaskGateway;
import hrp.tasks.persistence.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class GetCurrentUserTasksUsecase {

  private final TaskGateway taskGateway;
  private final SessionDataGateway sessionDataGateway;

  public Iterable<Task> execute() {
    return taskGateway.getAllTasks(sessionDataGateway.getAuthenticatedUsername());
  }
}
