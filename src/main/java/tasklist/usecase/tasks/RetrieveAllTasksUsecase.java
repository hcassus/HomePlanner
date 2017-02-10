package tasklist.usecase.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tasklist.gateways.tasks.TaskGatewaySpring;
import tasklist.persistence.tasks.Task;

@Service
public class RetrieveAllTasksUsecase {

  private TaskGatewaySpring taskGateway;

  @Autowired
  public RetrieveAllTasksUsecase(TaskGatewaySpring taskGateway){
    this.taskGateway = taskGateway;

  }

  public Iterable<Task> retrieveAllTasks(){
    return taskGateway.getAllTasks();
  }

}
