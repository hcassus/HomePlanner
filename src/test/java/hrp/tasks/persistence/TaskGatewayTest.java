package hrp.tasks.persistence;

import hrp.commons.testcases.GatewayTestCase;
import hrp.tasks.gateways.TaskGatewaySpring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskGatewayTest extends GatewayTestCase {

  private static final int COMPLETE_STATUS = 1;
  private static final int INCOMPLETE_STATUS = 0;

  @Autowired
  private TaskGatewaySpring gateway;

  @Autowired
  private TaskRepository repository;

  @BeforeEach
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
    Task task3 = new Task("Task3 " + System.currentTimeMillis());
    task.setCreatedBy(VALID_USERNAME);
    task2.setCreatedBy(VALID_USERNAME);
    task3.setCreatedBy("anotherUser");
    repository.saveAll(Arrays.asList(task, task2));

    List<Task> persistedTasks = (List<Task>) gateway.getAllTasks(VALID_USERNAME);

    assertThat(repository.count(), is(equalTo(2L)));
    assertThat(persistedTasks.get(0), samePropertyValuesAs(task));
    assertThat(persistedTasks.get(1), samePropertyValuesAs(task2));
  }

  @Test
  public void deleteItemByUuid(){
    Task item = new Task("Task " + System.currentTimeMillis());
    item = repository.save(item);

    gateway.deleteTaskByUuid(item.getUuid(),VALID_USERNAME);

    assertThat(repository.findAll(), emptyIterableOf(Task.class));
  }

  @Test
  public void completeTasks(){
    Task task = new Task("Task " + System.currentTimeMillis());
    UUID uuid = task.getUuid();
    repository.save(task);
    gateway.changeTaskStatus(uuid, COMPLETE_STATUS, VALID_USERNAME);

    Task persistedTask = repository.findOneByUuidAndCreatedBy(uuid, VALID_USERNAME);
    assertThat(persistedTask.getStatus(), is(equalTo(1)));
    assertTrue(persistedTask.getUpdatedAt().after(persistedTask.getCreatedAt()));
  }

  @Test
  public void uncompleteTasks(){
    Task task = new Task("Task " + System.currentTimeMillis());
    task.setStatus(COMPLETE_STATUS);
    repository.save(task);
    UUID uuid = task.getUuid();

    gateway.changeTaskStatus(uuid, INCOMPLETE_STATUS, VALID_USERNAME);
    gateway.changeTaskStatus(uuid, 0, VALID_USERNAME);
    Task persistedTask = repository.findOneByUuidAndCreatedBy(uuid, VALID_USERNAME);

    assertThat(persistedTask.getStatus(), is(equalTo(0)));
    assertTrue(persistedTask.getUpdatedAt().after(persistedTask.getCreatedAt()));
  }
}
