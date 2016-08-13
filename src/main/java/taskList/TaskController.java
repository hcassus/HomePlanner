package taskList;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private TaskRepository repository;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public Iterable<Task> getTasks(){
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/addtask", method = RequestMethod.POST)
    public Task addTask(@RequestBody String taskContent){
        Task task = new Task(taskContent);
        repository.save(task);
        return task;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/deltask/{id}", method = RequestMethod.DELETE)
    public void delTask(@PathVariable Long id){
        repository.delete(id);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/changetaskstatus/{id}", method = RequestMethod.PUT)
    public Task completeTask(@PathVariable Long id, @RequestBody int status){
        Task task = repository.findOne(id);
        task.setStatus(status);
        return repository.save(task);
    }



}