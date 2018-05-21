package tasks.ui.tests;

import commons.testcases.LiveServerTestCase;
import hrp.tasks.persistence.TaskRepository;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import tasks.ui.steps.TaskSteps;

public class TaskUITest extends LiveServerTestCase {

  @LocalServerPort
  private String localPort;

  @Autowired
  private TaskRepository taskRepository;

  private static WebDriver driver;
  private static WebDriverWait wait;
  private static TaskSteps taskSteps;

  @BeforeClass
  public static void setUpClass() {
    driver = new ChromeDriver();
    wait = new WebDriverWait(driver, 5);
    taskSteps = new TaskSteps(driver, wait);
  }

  @Before
  public void setUp() {
    taskRepository.deleteAll();
    driver.get("http://localhost:" + localPort);
  }

  @AfterClass
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