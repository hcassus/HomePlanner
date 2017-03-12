package hrp.tasks.controllers;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import hrp.tasks.gateways.TaskGatewaySpring;
import hrp.tasks.persistence.Task;

@RestController
public class TaskController {

  private TaskGatewaySpring taskGateway;

  @Autowired
  public TaskController(TaskGatewaySpring taskGateway) {
    this.taskGateway = taskGateway;
  }

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