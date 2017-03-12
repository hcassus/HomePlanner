package commons.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {

  WebDriver driver;

  public BasePage(WebDriver driver){
    this.driver = driver;
  }

  public void navigateTo(String linkText){
    driver.findElement(By.linkText(linkText)).click();
  }
}
