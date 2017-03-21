package tasks.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import commons.testcases.PersistencyTestCase;
import hrp.tasks.gateways.TaskGatewaySpring;
import hrp.tasks.persistence.Task;
import hrp.tasks.persistence.TaskRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskGatewayTest extends PersistencyTestCase {

  @Autowired
  private TaskGatewaySpring gateway;

  @Autowired
  private TaskRepository repository;

  @Before
  public void setUp(){
    repository.deleteAll();
  }

  @Test
  public void createTaskTest(){
    Task task = new Task("Task " + System.currentTimeMillis());
    Task persistedTask = gateway.addTask(task);

    assertThat(repository.count(), is(equalTo(1L)));
    assertThat(persistedTask, samePropertyValuesAs(task));
  }

  @Test
  public void retrieveTasksTest(){
    Task task = new Task("Task " + System.currentTimeMillis());
    Task task2 = new Task("Task2 " + System.currentTimeMillis());
    repository.save(Arrays.asList(task, task2));

    List<Task> persistedTasks = (List<Task>) gateway.getAllTasks();

    assertThat(repository.count(), is(equalTo(2L)));
    assertThat(persistedTasks.get(0), samePropertyValuesAs(task));
    assertThat(persistedTasks.get(1), samePropertyValuesAs(task2));
  }

  @Test
  public void deleteItemByUuid(){
    Task item = new Task("Task " + System.currentTimeMillis());
    item = repository.save(item);

    gateway.deleteTaskByUuid(item.getUuid());

    assertThat(repository.findAll(), emptyIterableOf(Task.class));
  }

  @Test
  public void completeTasks(){
    Task task = new Task("Task " + System.currentTimeMillis());
    UUID uuid = task.getUuid();
    repository.save(task);
    gateway.changeTaskStatus(uuid, 1);

    assertThat(repository.findOneByUuid(uuid).getStatus(), is(equalTo(1)));
  }

  @Test
  public void uncompleteTasks(){
    Task task = new Task("Task " + System.currentTimeMillis());
    task.setStatus(1);
    repository.save(task);
    UUID uuid = task.getUuid();

    gateway.changeTaskStatus(uuid, 0);

    assertThat(repository.findOneByUuid(uuid).getStatus(), is(equalTo(0)));
  }
}
