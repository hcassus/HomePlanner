package pantry.ui.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import hrp.pantry.persistence.entities.Product;
import pantry.ui.pages.PantryItemPage;

@Component
public class PantryItemSteps {

  PantryItemPage itemPage;

  private String description;
  private String quantity;
  private String unit;
  private String expiresAt;

  public PantryItemSteps(WebDriver driver, WebDriverWait wait) {
    this.itemPage = new PantryItemPage(driver, wait);
  }

  public PantryItemSteps createItem() {
    description = "Item " + System.currentTimeMillis();
    quantity = "5";
    unit = "Cartons";
    expiresAt = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
    itemPage.fillDescriptionField(description);
    itemPage.fillQuantityField(quantity);
    itemPage.selectUnit(unit);
    itemPage.fillExpirationDate(expiresAt);
    itemPage.clickAddButton();
    return this;
  }

  public void checkItemWasCreated() {
    assertThat(itemPage.getItemDescription(), is(equalTo(description)));
    assertThat(itemPage.getItemQuantity(), is(equalTo(quantity)));
    assertThat(itemPage.getItemUnit(), is(equalTo(unit)));
    assertThat(itemPage.getExpirationDate(), is(equalTo(expiresAt)));
  }

  public PantryItemSteps goToPantryManager() {
    itemPage.navigateTo("Pantry Manager");
    return this;
  }

  public PantryItemSteps fillBarcode(String eanCode) {
    itemPage.fillBarCodeField(eanCode);
    return this;
  }

  public void prefilledInputsCorrespondToProduct(Product product) {
    assertThat(itemPage.getDescriptionInputValue(), is(equalTo(product.getName())));
    assertThat(itemPage.getUnitSelectValue(), is(equalTo(product.getUnit().toString())));
  }
}
