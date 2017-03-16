package pantry.ui.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import hrp.pantry.persistence.entities.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import pantry.ui.pages.PantryItemPage;

@Component
public class PantryItemSteps {

  PantryItemPage itemPage;

  String description;
  String quantity;
  String unit;

  public PantryItemSteps(WebDriver driver, WebDriverWait wait) {
    this.itemPage = new PantryItemPage(driver, wait);
  }

  public PantryItemSteps createItem() {
    this.description = "Item " + System.currentTimeMillis();
    this.quantity = "5";
    this.unit = "Cartons";
    itemPage.fillDescriptionField(description);
    itemPage.fillQuantityField(quantity);
    itemPage.selectUnit(unit);
    itemPage.clickAddButton();
    return this;
  }

  public void checkItemWasCreated() {
    assertThat(itemPage.getItemDescription(), is(equalTo(description)));
    assertThat(itemPage.getItemQuantity(), is(equalTo(quantity)));
    assertThat(itemPage.getItemUnit(), is(equalTo(unit)));
  }

  public PantryItemSteps goToPantryManager() {
    itemPage.navigateTo("Pantry Manager");
    return this;
  }

  public PantryItemSteps fillBarcode(String eanCode) {
    itemPage.fillBarCodeField(eanCode);
    return this;
  }

  public void inputsCorrespondToProduct(Product product) {
    assertThat(itemPage.getDescriptionInputValue(), is(equalTo(product.getName())));
    assertThat(itemPage.getUnitSelectValue(), is(equalTo(product.getUnit().toString())));
  }
}
