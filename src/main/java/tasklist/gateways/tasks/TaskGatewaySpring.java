package tasklist.gateways.tasks;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tasklist.persistence.tasks.Task;
import tasklist.persistence.tasks.TaskRepository;

@Repository
public class TaskGatewaySpring {


  private TaskRepository repository;

  @Autowired
  public TaskGatewaySpring(TaskRepository repository) {
    this.repository = repository;
  }

  public Iterable<Task> getAllTasks() {
    return repository.findAll();
  }

  public Task addTask(String taskContent) {
    Task task = new Task(taskContent);
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
