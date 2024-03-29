package hrp.tasks.controller;

import hrp.commons.testcases.ControllerTestCase;
import hrp.tasks.gateways.TaskGatewaySpring;
import hrp.tasks.persistence.Task;
import hrp.tasks.usecase.ChangeCurrentUserTaskStatusUsecase;
import hrp.tasks.usecase.DeleteCurrentUserTasksUsecase;
import hrp.tasks.usecase.GetCurrentUserTasksUsecase;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TaskControllerTest extends ControllerTestCase {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @MockBean
  TaskGatewaySpring gateway;

  @MockBean
  GetCurrentUserTasksUsecase getCurrentUserTasksUsecase;

  @MockBean
  DeleteCurrentUserTasksUsecase deleteCurrentUserTasksUsecase;

  @MockBean
  ChangeCurrentUserTaskStatusUsecase changeCurrentUserTaskStatusUsecase;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  public void getAllTasksTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/task"));
    verify(getCurrentUserTasksUsecase, times(1)).execute();
  }

  @Test
  public void addTaskTest() throws Exception {
    String task = new JSONObject().put("description", "Test Task").toString();
    mockMvc.perform(
        MockMvcRequestBuilders.post("/task").contentType(MediaType.APPLICATION_JSON).content(task));
    verify(gateway, times(1)).addTask(any(Task.class));
  }

  @Test
  public void deleteTaskTest() throws Exception {
    UUID uuid = UUID.randomUUID();
    mockMvc.perform(MockMvcRequestBuilders.delete("/task/" + uuid));
    verify(deleteCurrentUserTasksUsecase, times(1)).execute(uuid);
  }

  @Test
  public void changeTaskStatusTest() throws Exception {
    UUID uuid = UUID.randomUUID();
    String task = new JSONObject().put("description", "Test Task").put("status", "1").toString();
    mockMvc.perform(
        MockMvcRequestBuilders.patch("/task/" + uuid).contentType(MediaType.APPLICATION_JSON)
            .content(task));
    verify(changeCurrentUserTaskStatusUsecase, times(1)).execute(eq(uuid), any(Task.class));
  }

}
