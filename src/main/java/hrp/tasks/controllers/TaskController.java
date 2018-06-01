package hrp.tasks.controllers;

import hrp.tasks.gateways.TaskGatewaySpring;
import hrp.tasks.persistence.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TaskController {

  private final TaskGatewaySpring taskGateway;

  @RequestMapping(value = "/task", method = RequestMethod.GET)
  public Iterable<Task> getTasks() {
    return taskGateway.getAllTasks();
  }

  @RequestMapping(value = "/task", method = RequestMethod.POST)
  public Task addTask(@RequestBody Task task) {
    return taskGateway.addTask(task);
  }

  @RequestMapping(value = "/task/{uuid}", method = RequestMethod.DELETE)
  public void delTask(@PathVariable UUID uuid) {
    taskGateway.deleteTaskByUuid(uuid);
  }

  @RequestMapping(value = "/task/{uuid}", method = RequestMethod.PATCH)
  public Task changeTaskStatusById(@PathVariable UUID uuid, @RequestBody Task task) {
    return taskGateway.changeTaskStatus(uuid, task.getStatus());
  }
}