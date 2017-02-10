package tasklist.usecase.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tasklist.gateways.tasks.TaskGatewaySpring;
import tasklist.persistence.tasks.Task;

@Service
public class RetrieveTaskByIdUsecase {

  private TaskGatewaySpring taskGateway;

  @Autowired
  public RetrieveTaskByIdUsecase(TaskGatewaySpring taskGateway){
    this.taskGateway = taskGateway;

  }

  public Task retrieveTaskById(long id){
   return taskGateway.getTaskById(id);
  }

}
