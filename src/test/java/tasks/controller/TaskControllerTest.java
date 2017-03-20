package tasks.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import hrp.HomePlannerApp;
import hrp.tasks.gateways.TaskGatewaySpring;
import hrp.tasks.persistence.Task;
import java.util.UUID;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HomePlannerApp.class}, loader = SpringBootContextLoader.class)
@WebAppConfiguration
public class TaskControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @MockBean
  TaskGatewaySpring gateway;

  @Before
  public void setup() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void getAllTasksTest() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/task"));
    verify(gateway, times(1)).getAllTasks();
  }

  @Test
  public void addTaskTest() throws Exception {
    String task = new JSONObject().put("description", "Test Task").toString();
    this.mockMvc.perform(
        MockMvcRequestBuilders.post("/task").contentType(MediaType.APPLICATION_JSON).content(task));
    verify(gateway, times(1)).addTask(any(Task.class));
  }

  @Test
  public void deleteTaskTest() throws Exception {
    UUID uuid = UUID.randomUUID();
    this.mockMvc.perform(MockMvcRequestBuilders.delete("/task/" + uuid));
    verify(gateway, times(1)).deleteTaskByUuid(uuid);
  }

  @Test
  public void changeTaskStatusTest() throws Exception {
    UUID uuid = UUID.randomUUID();
    String task = new JSONObject().put("description", "Test Task").put("status", "1").toString();
    this.mockMvc.perform(
        MockMvcRequestBuilders.patch("/task/" + uuid).contentType(MediaType.APPLICATION_JSON)
            .content(task));
    verify(gateway, times(1)).changeTaskStatus(uuid, 1);
  }

}
