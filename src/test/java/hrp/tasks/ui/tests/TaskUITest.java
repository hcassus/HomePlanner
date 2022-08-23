package hrp.tasks.ui.tests;

import hrp.commons.testcases.LiveServerTestCase;
import hrp.commons.ui.steps.LoginSteps;
import hrp.tasks.persistence.TaskRepository;
import hrp.tasks.ui.steps.TaskSteps;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class TaskUITest extends LiveServerTestCase {

  @LocalServerPort
  private String localPort;

  @Autowired
  private TaskRepository taskRepository;

  private static WebDriver driver;
  private static WebDriverWait wait;
  private static TaskSteps taskSteps;

  @BeforeAll
  public static void setUpClass() {
    driver = new ChromeDriver();
    wait = new WebDriverWait(driver, Duration.of(5, ChronoUnit.SECONDS));
    taskSteps = new TaskSteps(driver, wait);
  }

  @BeforeEach
  public void setUp() {
    taskRepository.deleteAll();
    driver.get("http://localhost:" + localPort + "/login");
    new LoginSteps(driver).performSuccessfulLogin();
  }

  @AfterAll
  public static void tearDown() {
    driver.quit();
  }

  @Test
  public void taskCreateTest() {
    taskSteps
        .navigateToTaskManager()
        .createTask()
        .checkTaskWasCreated();
  }

  @Test
  public void taskCompleteTest() {
    taskSteps
        .navigateToTaskManager()
        .createTask()
        .completeTask()
        .checkTaskWasCompleted();
  }



}
