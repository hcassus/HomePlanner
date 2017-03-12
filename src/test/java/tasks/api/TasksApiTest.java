package tasks.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import hrp.HomePlannerApp;
import hrp.tasks.persistence.Task;
import hrp.tasks.persistence.TaskRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = HomePlannerApp.class, loader = SpringBootContextLoader.class)
public class TasksApiTest {

  @Autowired
  TaskRepository repository;

  @LocalServerPort
  String port;

  @Before
  public void setUp(){
    RestAssured.baseURI = "http://localhost:" + port;
    repository.deleteAll();
  }

  @Test
  public void createTaskTest() throws JSONException {
    JSONObject task = new JSONObject()
        .put("description", "Task "+ System.currentTimeMillis());

    RestAssured
        .given()
          .contentType(ContentType.JSON)
          .body(task.toString())
        .when()
          .post("/task")
        .then()
          .statusCode(200)
          .body("status", is(equalTo(0)))
          .body("uuid", notNullValue())
          .body("description", is(equalTo(task.get("description"))));

    assertThat(repository.count(), is(equalTo(1L)));
  }

  @Test
  public void getAllTasksTest(){

    Task task = repository.save(new Task("Task " + System.currentTimeMillis()));
    Task task2 = repository.save(new Task("Task2 " + System.currentTimeMillis()));

    RestAssured
        .given()
          .contentType(ContentType.JSON)
        .when()
          .get("/task")
        .then()
          .statusCode(200)
          .body("get(0).content", is(equalTo(task.getDescription())))
          .body("get(0).status", is(equalTo(task.getStatus())))
          .body("get(0).uuid", is(equalTo(task.getUuid().toString())))
          .body("get(1).content", is(equalTo(task2.getDescription())))
          .body("get(1).status", is(equalTo(task2.getStatus())))
          .body("get(1).uuid", is(equalTo(task2.getUuid().toString())));
  }

  @Test
  public void deleteTaskByUuid(){
    Task task = repository.save(new Task("Task " + System.currentTimeMillis()));

    RestAssured
        .given()
          .contentType(ContentType.JSON)
        .when()
          .delete("/task/" + task.getUuid())
        .then()
          .statusCode(200)
          .body(is(""));

    assertThat(repository.count(), is(equalTo(0L)));
  }

}
