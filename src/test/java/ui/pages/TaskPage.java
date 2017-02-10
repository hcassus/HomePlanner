package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TaskPage {
    @FindBy(id = "tasks-inpt")
    private WebElement taskInput;

    @FindBy(id = "add-btn")
    private WebElement addButton;

    @FindBy(xpath = "//*[contains(@id,'task_desc_row_')]")
    private WebElement  taskRow;

    @FindBy(xpath = "//*[contains(@id,'task_del_row_')]")
    private WebElement delButton;

    private WebDriverWait wait;

    public TaskPage(WebDriver driver, WebDriverWait wait){
        PageFactory.initElements(driver, this);
        this.wait = wait;
    }

    public void fillTaskInput(String task){
        taskInput.sendKeys(task);
    }

    public void clickAddBtn(){
        addButton.click();
    }

    public String getTaskDescription(){
        wait.until(ExpectedConditions.visibilityOf(taskRow));
        return taskRow.getText();
    }

    public void deleteTask(){
        delButton.click();
    }
}
