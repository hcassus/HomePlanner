package ui.steps;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.pages.TaskPage;

public class TaskSteps {

    private String taskName;
    private WebDriver driver;
    private WebDriverWait wait;
    private TaskPage page;

    public TaskSteps(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.page = new TaskPage(driver, wait);
    }

    public TaskSteps createTask(){
        taskName = "Tarefa" + System.currentTimeMillis();
        page.fillTaskInput(taskName);
        page.clickAddBtn();
        return this;
    }

    public TaskSteps checkTaskWasCreated(){
        By taskRowLocator = By.xpath("//*[contains(@id,'task_desc_row_')]");
        wait.until(ExpectedConditions.presenceOfElementLocated(taskRowLocator));
        String description = page.getTaskDescription();
        Assert.assertEquals(description, taskName);
        return this;
    }

    public TaskSteps deleteCreatedTask(){
        page.deleteTask();
        return this;
    }
}
