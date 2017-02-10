package tasklist.usecase.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tasklist.gateways.tasks.TaskGatewaySpring;
import tasklist.persistence.tasks.Task;

@Service
public class ChangeTaskStatusByIdUsecase {

  private TaskGatewaySpring taskGateway;

  @Autowired
  public ChangeTaskStatusByIdUsecase(TaskGatewaySpring taskGateway){
    this.taskGateway = taskGateway;
  }

  public Task changeStatusById(long id, int status){
    return taskGateway.changeTaskStatus(id, status);
  }

}
