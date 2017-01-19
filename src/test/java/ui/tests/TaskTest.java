package ui.tests;

import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import taskList.TaskListApp;
import ui.steps.TaskSteps;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes= TaskListApp.class, loader = SpringBootContextLoader.class)
public class TaskTest {

    @LocalServerPort
    private String localPort;
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static TaskSteps taskSteps;

    @BeforeClass
    public static void setUpClass(){
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 5);
        taskSteps = new TaskSteps(driver, wait);
    }

    @Test
    public void testCreateTest(){
        driver.get("http://localhost:"+localPort);
        taskSteps
                .createTask()
                .checkTaskWasCreated();
    }

    @AfterClass
    public static void tearDownClass(){
        taskSteps.deleteCreatedTask();
        driver.quit();
    }
}