package tasklist.controller.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tasklist.persistence.tasks.Task;
import tasklist.usecase.tasks.AddTaskUsecase;
import tasklist.usecase.tasks.ChangeTaskStatusByIdUsecase;
import tasklist.usecase.tasks.RemoveTaskUsecase;
import tasklist.usecase.tasks.RetrieveAllTasksUsecase;
import tasklist.usecase.tasks.RetrieveTaskByIdUsecase;

@RestController
public class TaskController {

  private AddTaskUsecase addTaskUsecase;
  private RemoveTaskUsecase removeTaskUsecase;
  private RetrieveTaskByIdUsecase retrieveTaskByIdUsecase;
  private RetrieveAllTasksUsecase retrieveAllTasksUsecase;
  private ChangeTaskStatusByIdUsecase changeTaskStatusByIdUsecase;

  @Autowired
  public TaskController(AddTaskUsecase addTaskUsecase,
                        RemoveTaskUsecase removeTaskUsecase,
                        RetrieveTaskByIdUsecase retrieveTaskByIdUsecase,
                        RetrieveAllTasksUsecase retrieveAllTasksUsecase,
                        ChangeTaskStatusByIdUsecase changeTaskStatusByIdUsecase) {

    this.addTaskUsecase = addTaskUsecase;
    this.removeTaskUsecase = removeTaskUsecase;
    this.retrieveAllTasksUsecase = retrieveAllTasksUsecase;
    this.retrieveTaskByIdUsecase = retrieveTaskByIdUsecase;
    this.changeTaskStatusByIdUsecase = changeTaskStatusByIdUsecase;

  }



  @RequestMapping(value = "/task", method = RequestMethod.GET)
  public Iterable<Task> getTasks() {
    return retrieveAllTasksUsecase.retrieveAllTasks();
  }

  @RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
  public Task getTaskById(@PathVariable Long id) {
    return retrieveTaskByIdUsecase.retrieveTaskById(id);
  }

  @RequestMapping(value = "/task", method = RequestMethod.POST)
  public Task addTask(@RequestBody String taskContent) {
    return addTaskUsecase.addTask(taskContent);
  }

  @RequestMapping(value = "/task/{id}", method = RequestMethod.DELETE)
  public void delTask(@PathVariable Long id) {
    removeTaskUsecase.removeTask(id);
  }

  @RequestMapping(value = "/task/{id}", method = RequestMethod.PUT)
  public Task changeTaskStatusById(@PathVariable Long id, @RequestBody int status) {
    return changeTaskStatusByIdUsecase.changeStatusById(id, status);
  }
}