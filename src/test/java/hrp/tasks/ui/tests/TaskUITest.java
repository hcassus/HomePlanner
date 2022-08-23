package hrp.tasks.ui.tests;

import hrp.commons.testcases.LiveServerTestCase;
import hrp.commons.ui.steps.LoginSteps;
import hrp.tasks.persistence.TaskRepository;
import hrp.tasks.ui.steps.TaskSteps;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.AuditorAware;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class TaskUITest extends LiveServerTestCase {

  @MockBean
  private AuditorAware<String> auditorAware;

  @BeforeEach
  public void setup(){
    Mockito.when(auditorAware.getCurrentAuditor()).thenReturn(Optional.of(VALID_USERNAME));
  }
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
