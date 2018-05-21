package commons.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

  private WebDriver driver;

  public BasePage(WebDriver driver){
    this.driver = driver;
    PageFactory.initElements(driver, this);

  }

  public void navigateTo(String linkText){
    driver.findElement(By.linkText(linkText)).click();
  }
}
