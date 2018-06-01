package hrp.tasks.gateways;

import hrp.tasks.persistence.Task;
import hrp.tasks.persistence.TaskRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TaskGatewaySpring {


  private final TaskRepository repository;

  public Iterable<Task> getAllTasks() {
    return repository.findAll();
  }

  public Task addTask(Task task) {
    repository.save(task);
    return task;
  }

  public void deleteTaskByUuid(UUID uuid) {
    repository.deleteTaskByUuid(uuid);
  }

  public Task changeTaskStatus(UUID uuid, int status) {
    Task task = repository.findOneByUuid(uuid);
    task.setStatus(status);
    return repository.save(task);
  }
}
