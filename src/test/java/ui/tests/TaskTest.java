package ui.tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.steps.TaskSteps;

public class TaskTest {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static TaskSteps taskSteps;

    @BeforeClass
    public static void setUp(){
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 5);
        taskSteps = new TaskSteps(driver, wait);
        driver.get("http://localhost:8080");
    }

    @Test
    public void testCreateTest() throws InterruptedException {
        taskSteps.createTask().checkTaskWasCreated();
    }

    @AfterClass
    public static void tearDown(){
        taskSteps.deleteCreatedTask();
        driver.quit();
    }
}