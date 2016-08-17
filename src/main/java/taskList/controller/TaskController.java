package taskList.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taskList.gateways.TaskGatewaySpring;
import taskList.persistence.Task;

@RestController
public class TaskController {

    @Autowired
    private TaskGatewaySpring gateway;

    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public Iterable<Task> getTasks() {
        return gateway.getAllTasks();
    }

    @RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
    public Task getTasks(@PathVariable Long id) {
        return gateway.getTaskById(id);
    }

    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public Task addTask(@RequestBody String taskContent) {
        return gateway.addTask(taskContent);
    }

    @RequestMapping(value = "/task/{id}", method = RequestMethod.DELETE)
    public void delTask(@PathVariable Long id) {
        gateway.deleteTask(id);
    }

    @RequestMapping(value = "/task/{id}", method = RequestMethod.PUT)
    public Task completeTask(@PathVariable Long id, @RequestBody int status) {
        return gateway.changeTaskStatus(id, status);
    }


}