package tasks.ui.tests;

import commons.testcases.LiveServerTestCase;
import hrp.tasks.persistence.TaskRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import tasks.ui.steps.TaskSteps;

public class TaskTest extends LiveServerTestCase{

    @LocalServerPort
    private String localPort;

    @Autowired
    private TaskRepository taskRepository;

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static TaskSteps taskSteps;

    @Before
    public void setUp(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
        taskSteps = new TaskSteps(driver, wait);
        taskRepository.deleteAll();
        driver.get("http://localhost:"+localPort);
    }

    @Test
    public void testCreateTest(){
        taskSteps
            .navigateToTaskManager()
            .createTask()
            .checkTaskWasCreated();
    }

    @Test
    public void testCompleteTest(){
        taskSteps
            .navigateToTaskManager()
            .createTask()
            .completeTask()
            .checkTaskWasCompleted();
    }



    @After
    public void tearDown(){
        driver.quit();
    }
}