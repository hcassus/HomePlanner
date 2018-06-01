package hrp.commons.ui.steps;

import hrp.commons.ui.pages.LoginPage;
import org.openqa.selenium.WebDriver;

public class LoginSteps {

    private final WebDriver driver;
    private LoginPage page;

    private final String VALID_USERNAME = System.getenv("VALID_USERNAME");
    private final String VALID_PASSWORD = System.getenv("VALID_PASSWORD");

    public LoginSteps(WebDriver driver){
        this.driver = driver;
        this.page = new LoginPage(driver);
    }

    public LoginSteps performSuccessfulLogin(){
        page.fillUserField(VALID_USERNAME);
        page.fillPasswordField(VALID_PASSWORD);
        page.clickSubmitButton();
        return this;
    }
}
