package hrp.tasks.gateways;

import hrp.tasks.persistence.Task;
import hrp.tasks.persistence.TaskRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TaskGatewaySpring implements TaskGateway {

  private final TaskRepository repository;

  @Override
  public Iterable<Task> getAllTasks(String username) {
    return repository.findByCreatedBy(username);
  }

  @Override
  public Task addTask(Task task) {
    repository.save(task);
    return task;
  }

  @Override
  public void deleteTaskByUuid(UUID uuid, String username) {
    repository.deleteTaskByUuidAndCreatedBy(uuid, username);
  }

  @Override
  public Task changeTaskStatus(UUID uuid, int status, String username) {
    Task task = repository.findOneByUuidAndCreatedBy(uuid, username);
    task.setStatus(status);
    return repository.save(task);
  }
}
