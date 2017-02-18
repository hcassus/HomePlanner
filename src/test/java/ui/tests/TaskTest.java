package ui.tests;

import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import hrp.HomePlannerApp;
import hrp.tasks.persistence.TaskRepository;
import ui.steps.TaskSteps;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes= HomePlannerApp.class, loader = SpringBootContextLoader.class)
public class TaskTest {

    @LocalServerPort
    private String localPort;

    @Autowired
    private TaskRepository taskRepository;

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static TaskSteps taskSteps;

    @Before
    public void setUp(){
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 5);
        taskSteps = new TaskSteps(driver, wait);
        taskRepository.deleteAll();
        driver.get("http://localhost:"+localPort);
    }

    @Test
    public void testCreateTest(){
        taskSteps
                .createTask()
                .checkTaskWasCreated();
    }

    @After
    public void tearDown(){
        driver.quit();
    }
}