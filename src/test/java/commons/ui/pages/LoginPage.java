package commons.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(name = "username")
    private WebElement userField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(name = "submit")
    private WebElement submitButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void fillUserField(String user){
        userField.sendKeys(user);
    }

    public void fillPasswordField(String password){
        passwordField.sendKeys(password);
    }

    public void clickSubmitButton() {
        submitButton.click();
    }
}
