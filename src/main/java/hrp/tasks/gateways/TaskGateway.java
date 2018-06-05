package hrp.tasks.gateways;

import hrp.tasks.persistence.Task;
import java.util.UUID;

public interface TaskGateway {

  Iterable<Task> getAllTasks(String username);

  Task addTask(Task task);

  void deleteTaskByUuid(UUID uuid, String username);

  Task changeTaskStatus(UUID uuid, int status, String username);
}
