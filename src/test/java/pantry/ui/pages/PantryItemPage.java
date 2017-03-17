package pantry.ui.pages;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;

import commons.ui.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
public class PantryItemPage extends BasePage{

  public static final String DESCRIPTION_INPUT_LOCATOR = "inpt-description";
  public static final String ITEM_DESCRIPTION_TEXT_LOCATOR = "item-description";
  @FindBy(id = DESCRIPTION_INPUT_LOCATOR)
  private WebElement descriptionInput;

  @FindBy(id = "inpt-qty")
  private WebElement quantityInput;

  @FindBy(id = "slct-unit")
  private WebElement unitSelectElement;

  @FindBy(className = ITEM_DESCRIPTION_TEXT_LOCATOR)
  private WebElement itemDescription;

  @FindBy(className = "item-qty")
  private WebElement itemQuantity;

  @FindBy(className = "item-unit")
  private WebElement itemUnit;

  @FindBy(id = "btn-add")
  private WebElement addButton;

  @FindBy(id = "ean-code-field")
  private WebElement barCodeField;

  private WebDriverWait wait;

  public PantryItemPage(WebDriver driver, WebDriverWait wait){
    super(driver);
    this.wait = wait;
    PageFactory.initElements(driver, this);
  }

  public void fillDescriptionField(String itemDescription) {
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id(DESCRIPTION_INPUT_LOCATOR)));
    descriptionInput.sendKeys(itemDescription);
  }

  public void fillQuantityField(String quantity) {
    quantityInput.sendKeys(quantity);
  }

  public void selectUnit(String unit) {
    Select unitSelect = new Select(unitSelectElement);
    unitSelect.selectByVisibleText(unit);
  }

  public String getItemDescription() {
    wait.until(ExpectedConditions.presenceOfElementLocated(By.className(
        ITEM_DESCRIPTION_TEXT_LOCATOR)));
    return itemDescription.getText();
  }

  public String getItemQuantity() {
    return itemQuantity.getText();
  }

  public String getItemUnit() {
    return itemUnit.getText();
  }

  public void clickAddButton() {
    addButton.click();
  }

  public void fillBarCodeField(String eanCode) {
    barCodeField.sendKeys(eanCode);
  }

  public String getDescriptionInputValue() {
    wait.until(not(attributeToBe(descriptionInput, "value", "")));
    return descriptionInput.getAttribute("value");
  }

  public String getUnitSelectValue() {
    Select unitSelect = new Select(unitSelectElement);
    return unitSelect.getFirstSelectedOption().getAttribute("value");
  }
}
