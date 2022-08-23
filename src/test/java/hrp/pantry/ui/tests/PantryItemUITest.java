package hrp.pantry.ui.tests;

import hrp.commons.testcases.LiveServerTestCase;
import hrp.commons.ui.steps.LoginSteps;
import hrp.pantry.enums.PackagingUnit;
import hrp.pantry.persistence.entities.Product;
import hrp.pantry.persistence.repositories.PantryItemRepository;
import hrp.pantry.persistence.repositories.ProductRepository;
import hrp.pantry.ui.steps.PantryItemSteps;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.AuditorAware;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;

public class PantryItemUITest extends LiveServerTestCase {

  @MockBean
  private AuditorAware<String> auditorAware;

  @BeforeEach
  public void setup(){
    Mockito.when(auditorAware.getCurrentAuditor()).thenReturn(Optional.of(VALID_USERNAME));
  }
  @LocalServerPort
  private String port;

  @Autowired
  private PantryItemRepository pantryRepository;

  @Autowired
  private ProductRepository productRepository;

  private static PantryItemSteps itemSteps;

  private static WebDriver driver;

  @BeforeAll
  public static void setUpClass() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--lang=en");
    driver = new ChromeDriver(options);
    WebDriverWait wait = new WebDriverWait(driver, Duration.of(5, ChronoUnit.SECONDS));
    itemSteps = new PantryItemSteps(driver, wait);
  }

  @BeforeEach
  public void setUp() {
    pantryRepository.deleteAll();
    productRepository.deleteAll();
    driver.get("http://localhost:" + port + "/login");
    new LoginSteps(driver).performSuccessfulLogin();
  }

  @AfterAll
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
    productRepository.saveAll(Arrays.asList(product, product2));

    itemSteps
        .goToPantryManager()
        .fillBarcode(product.getEanCode())
        .prefilledInputsCorrespondToProduct(product);
  }
}
