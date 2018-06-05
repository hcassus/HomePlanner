package hrp.tasks.controllers;

import hrp.tasks.gateways.TaskGateway;
import hrp.tasks.persistence.Task;
import hrp.tasks.usecase.ChangeCurrentUserTaskStatusUsecase;
import hrp.tasks.usecase.DeleteCurrentUserTasksUsecase;
import hrp.tasks.usecase.GetCurrentUserTasksUsecase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskController {

  private final GetCurrentUserTasksUsecase getCurrentUserTasksUsecase;
  private final DeleteCurrentUserTasksUsecase deleteCurrentUserTasksUsecase;
  private final ChangeCurrentUserTaskStatusUsecase changeCurrentUserTaskStatusUsecase;
  private final TaskGateway taskGateway;

  @RequestMapping(value = "/task", method = RequestMethod.GET)
  public Iterable<Task> getTasks() {
    return getCurrentUserTasksUsecase.execute();
  }

  @RequestMapping(value = "/task", method = RequestMethod.POST)
  public Task addTask(@RequestBody Task task) {
    return taskGateway.addTask(task);
  }

  @RequestMapping(value = "/task/{uuid}", method = RequestMethod.DELETE)
  public void delTask(@PathVariable UUID uuid) {
    deleteCurrentUserTasksUsecase.execute(uuid);
  }

  @RequestMapping(value = "/task/{uuid}", method = RequestMethod.PATCH)
  public Task changeTaskStatusById(@PathVariable UUID uuid, @RequestBody Task task) {
    return changeCurrentUserTaskStatusUsecase.execute(uuid, task);
  }
}