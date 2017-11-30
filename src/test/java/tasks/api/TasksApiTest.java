package tasks.api;

import commons.testcases.LiveServerTestCase;
import hrp.tasks.persistence.Task;
import hrp.tasks.persistence.TaskRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;

import java.security.Timestamp;

import static io.restassured.RestAssured.preemptive;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TasksApiTest extends LiveServerTestCase{

  private static final int INCOMPLETE_STATUS = 0;
  private static final int COMPLETE_STATUS = 1;
  private static final int HTTP_OK = 200;

  @Autowired
  TaskRepository repository;

  @LocalServerPort
  String port;



  @Before
  public void setUp(){
    RestAssured.baseURI = "http://localhost:" + port;
    RestAssured.authentication = preemptive().basic("admin","123123");

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
          .statusCode(HTTP_OK)
          .body("status", is(equalTo(INCOMPLETE_STATUS)))
          .body("uuid", notNullValue())
          .body("description", is(equalTo(task.get("description"))))
          .body("updatedAt", notNullValue(Timestamp.class))
          .body("createdAt", notNullValue(Timestamp.class))
          .body(matchesJsonSchemaInClasspath("json_schemas/tasks/task-schema.json"));

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
          .statusCode(HTTP_OK)
          .body("get(0).description", is(equalTo(task.getDescription())))
          .body("get(0).status", is(equalTo(task.getStatus())))
          .body("get(0).uuid", is(equalTo(task.getUuid().toString())))
          .body("get(0).updatedAt", notNullValue(Timestamp.class))
          .body("get(0).createdAt", notNullValue(Timestamp.class))
          .body("get(1).description", is(equalTo(task2.getDescription())))
          .body("get(1).status", is(equalTo(task2.getStatus())))
          .body("get(1).uuid", is(equalTo(task2.getUuid().toString())))
          .body("get(1).updatedAt", notNullValue(Timestamp.class))
          .body("get(1).createdAt", notNullValue(Timestamp.class))
          .body(matchesJsonSchemaInClasspath("json_schemas/tasks/task-list-schema.json"));
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
          .statusCode(HTTP_OK)
          .body(is(""));

    assertThat(repository.count(), is(equalTo(0L)));
  }


  @Test
  public void completeTask() throws JSONException {
    Task task = repository.save(new Task("Task " + System.currentTimeMillis()));

    JSONObject taskPatch = new JSONObject()
        .put("description", task.getDescription())
        .put("status", COMPLETE_STATUS);

    RestAssured
        .given()
          .contentType(ContentType.JSON)
          .body(taskPatch.toString())
        .when()
          .patch("/task/" + task.getUuid())
        .then()
          .statusCode(HTTP_OK)
          .body("status", is(equalTo(COMPLETE_STATUS)))
          .body("uuid", notNullValue())
          .body("description", is(equalTo(task.getDescription())))
          .body("updatedAt", notNullValue(Timestamp.class))
          .body("createdAt", notNullValue(Timestamp.class))
          .body("updatedAt", response -> greaterThan(response.path("createdAt")))
          .body(matchesJsonSchemaInClasspath("json_schemas/tasks/task-schema.json"));

    assertThat(repository.count(), is(equalTo(1L)));
  }

  @Test
  public void uncompleteTask() throws JSONException {
    Task task = new Task("Task " + System.currentTimeMillis());
    task.setStatus(COMPLETE_STATUS);
    task = repository.save(task);

    JSONObject taskPatch = new JSONObject()
        .put("description", task.getDescription())
        .put("status", INCOMPLETE_STATUS);

    RestAssured
        .given()
          .contentType(ContentType.JSON)
          .body(taskPatch.toString())
        .when()
          .patch("/task/" + task.getUuid())
        .then()
          .statusCode(HTTP_OK)
          .body("status", is(equalTo(INCOMPLETE_STATUS)))
          .body("uuid", notNullValue())
          .body("description", is(equalTo(task.getDescription())))
          .body("updatedAt", notNullValue(Timestamp.class))
          .body("createdAt", notNullValue(Timestamp.class))
          .body("updatedAt", response -> greaterThan(response.path("createdAt")))
          .body(matchesJsonSchemaInClasspath("json_schemas/tasks/task-schema.json"));
    assertThat(repository.count(), is(equalTo(1L)));
  }
}