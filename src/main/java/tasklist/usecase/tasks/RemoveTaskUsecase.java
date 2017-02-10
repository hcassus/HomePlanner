package tasklist.usecase.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tasklist.gateways.tasks.TaskGatewaySpring;

@Service
public class RemoveTaskUsecase {

  private TaskGatewaySpring taskGateway;

  @Autowired
  public RemoveTaskUsecase(TaskGatewaySpring taskGateway){
    this.taskGateway = taskGateway;

  }

  public void removeTask(long id){
    taskGateway.deleteTask(id);
  }

}
