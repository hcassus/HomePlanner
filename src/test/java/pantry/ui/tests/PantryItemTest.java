package pantry.ui.tests;

import hrp.HomePlannerApp;
import hrp.pantry.persistence.PantryItemRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pantry.ui.steps.PantryItemSteps;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = HomePlannerApp.class, loader = SpringBootContextLoader.class)
public class PantryItemTest {

  @LocalServerPort
  private String port;

  @Autowired
  private PantryItemRepository repository;

  private PantryItemSteps itemSteps;

  WebDriver driver;

  @Before
  public void setUp(){
    driver = new FirefoxDriver();
    WebDriverWait wait = new WebDriverWait(driver, 5);
    this.itemSteps = new PantryItemSteps(driver, wait);
    repository.deleteAll();
    driver.get("http://localhost:"+ port);
  }

  @After
  public void tearDown(){
    driver.quit();
  }

  @Test
  public void createNewItem(){
    itemSteps
        .goToPantryManager()
        .createItem()
        .checkItemWasCreated();
  }
}
