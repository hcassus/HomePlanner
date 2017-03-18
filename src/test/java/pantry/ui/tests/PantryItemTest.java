package pantry.ui.tests;

import commons.testcases.LiveServerTestCase;
import hrp.pantry.enums.PackagingUnit;
import hrp.pantry.persistence.entities.Product;
import hrp.pantry.persistence.repositories.PantryItemRepository;
import hrp.pantry.persistence.repositories.ProductRepository;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import pantry.ui.steps.PantryItemSteps;

public class PantryItemTest extends LiveServerTestCase{

  @LocalServerPort
  private String port;

  @Autowired
  private PantryItemRepository pantryRepository;

  @Autowired
  private ProductRepository productRepository;

  private PantryItemSteps itemSteps;

  WebDriver driver;

  @Before
  public void setUp(){
    driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, 5);
    this.itemSteps = new PantryItemSteps(driver, wait);
    pantryRepository.deleteAll();
    productRepository.deleteAll();
    driver.get("http://localhost:"+ port);
  }

  @After
  public void tearDown(){
    driver.quit();
  }

  @Test
  public void createNewItemTest(){
    itemSteps
        .goToPantryManager()
        .createItem()
        .checkItemWasCreated();
  }

  @Test
  public void autoCompleteTest(){
    Product product = new Product("1234567890123", "Haagen Dazs Vanilla", PackagingUnit.UNIT);
    product.setCount(10L);
    Product product2 = new Product("1234567890123", "Haagen Dazs Chocolate", PackagingUnit.UNIT);
    product.setCount(5L);
    productRepository.save(Arrays.asList(product,product2));

    itemSteps
        .goToPantryManager()
        .fillBarcode(product.getEanCode())
        .inputsCorrespondToProduct(product);
  }
}
