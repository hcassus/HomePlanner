package hrp.tasks.ui.pages;

import hrp.commons.ui.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TaskPage extends BasePage {

  @FindBy(id = "task-inpt")
  private WebElement taskInput;

  @FindBy(id = "add-btn")
  private WebElement addButton;

  @FindBy(xpath = "//*[contains(@id,'task_desc_row_')]")
  private WebElement taskRow;

  @FindBy(xpath = "//*[contains(@id,'task_del_row_')]")
  private WebElement delButton;

  private WebDriverWait wait;

  @FindBy(xpath = "//*[contains(@class,'glyphicon-ok')]")
  private WebElement completeBtn;

  @FindBy(xpath = "//*[contains(@class,'glyphicon-repeat')]")
  private WebElement uncompleteBtn;

  public TaskPage(WebDriver driver, WebDriverWait wait) {
    super(driver);
    this.wait = wait;
  }

  public void fillTaskInput(String task) {
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("task-inpt")));
    taskInput.sendKeys(task);
  }

  public void clickAddBtn() {
    addButton.click();
  }

  public String getTaskDescription() {
    wait.until(ExpectedConditions.visibilityOf(taskRow));
    return taskRow.getText();
  }

  public void deleteTask() {
    delButton.click();
  }

  public void clickCompleteBtn() {
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class,'btn-success')]")));
    completeBtn.click();
    wait.until(ExpectedConditions.invisibilityOf(completeBtn));
  }

  public int getTaskStatus() {
    return uncompleteBtn.isDisplayed() ? 1 : 0;
  }
}
