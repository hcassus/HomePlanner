package tasklist.usecase.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tasklist.gateways.tasks.TaskGatewaySpring;
import tasklist.persistence.tasks.Task;

@Service
public class AddTaskUsecase {


  private TaskGatewaySpring taskGateway;

  @Autowired
  public AddTaskUsecase(TaskGatewaySpring taskGateway){
    this.taskGateway = taskGateway;

  }

  public Task addTask(String content){
    return taskGateway.addTask(content);
  }

}
