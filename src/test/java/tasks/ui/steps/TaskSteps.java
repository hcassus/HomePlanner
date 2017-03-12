package tasks.ui.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tasks.ui.pages.TaskPage;

public class TaskSteps {

  private String taskName;
  private WebDriverWait wait;
  private TaskPage page;

  public TaskSteps(WebDriver driver, WebDriverWait wait) {
    this.wait = wait;
    this.page = new TaskPage(driver, wait);
  }

  public TaskSteps createTask() {
    taskName = "Tarefa" + System.currentTimeMillis();
    page.fillTaskInput(taskName);
    page.clickAddBtn();
    return this;
  }

  public TaskSteps checkTaskWasCreated() {
    By taskRowLocator = By.xpath("//*[contains(@id,'task_desc_row_')]");
    wait.until(ExpectedConditions.presenceOfElementLocated(taskRowLocator));
    String description = page.getTaskDescription();
    Assert.assertEquals(description, taskName);
    return this;
  }

  public TaskSteps deleteCreatedTask() {
    page.deleteTask();
    return this;
  }

  public TaskSteps navigateToTaskManager() {
    page.navigateTo("Task Manager");
    return this;
  }

  public TaskSteps completeTask() {
    page.clickCompleteBtn();
    return this;
  }

  public void checkTaskWasCompleted() {
    assertThat(page.getTaskStatus(), equalTo(1));
  }
}
