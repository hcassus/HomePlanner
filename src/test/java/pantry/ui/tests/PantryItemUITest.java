package pantry.ui.tests;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;

import java.util.Arrays;
import commons.testcases.LiveServerTestCase;
import hrp.pantry.enums.PackagingUnit;
import hrp.pantry.persistence.entities.Product;
import hrp.pantry.persistence.repositories.PantryItemRepository;
import hrp.pantry.persistence.repositories.ProductRepository;
import pantry.ui.steps.PantryItemSteps;

public class PantryItemUITest extends LiveServerTestCase {

  @LocalServerPort
  private String port;

  @Autowired
  private PantryItemRepository pantryRepository;

  @Autowired
  private ProductRepository productRepository;

  private static PantryItemSteps itemSteps;

  private static WebDriver driver;

  @BeforeClass
  public static void setUpClass() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--lang=en");
    driver = new ChromeDriver(options);
    WebDriverWait wait = new WebDriverWait(driver, 5);
    itemSteps = new PantryItemSteps(driver, wait);
  }

  @Before
  public void setUp() {
    pantryRepository.deleteAll();
    productRepository.deleteAll();
    driver.get("http://localhost:" + port);
  }

  @AfterClass
  public static void tearDownClass() {
    driver.quit();
  }

  @Test
  public void createNewItemTest() {
    itemSteps
        .goToPantryManager()
        .createItem()
        .checkItemWasCreated();
  }

  @Test
  public void autoCompleteTest() {
    Product product = new Product("1234567890123", "Haagen Dazs Vanilla", PackagingUnit.UNIT);
    product.setCount(10L);
    Product product2 = new Product("1234567890123", "Haagen Dazs Chocolate", PackagingUnit.UNIT);
    product.setCount(5L);
    productRepository.save(Arrays.asList(product, product2));

    itemSteps
        .goToPantryManager()
        .fillBarcode(product.getEanCode())
        .prefilledInputsCorrespondToProduct(product);
  }
}
