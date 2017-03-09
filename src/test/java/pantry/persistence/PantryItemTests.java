package pantry.persistence;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import hrp.HomePlannerApp;
import hrp.pantry.enums.MeasurementUnits;
import hrp.pantry.gateways.PantryItemGateway;
import hrp.pantry.persistence.PantryItem;
import hrp.pantry.persistence.PantryItemRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = HomePlannerApp.class, loader = SpringBootContextLoader.class)
public class PantryItemTests {

  @Autowired
  private PantryItemGateway gateway;

  @Autowired
  private PantryItemRepository repository;

  @Test
  public void createPantryItemTest(){
    String itemName = "Milk "+System.currentTimeMillis();
    Long quantity = 10L;
    MeasurementUnits unit = MeasurementUnits.LITER;

    PantryItem item = new PantryItem(itemName, quantity, unit);
    PantryItem persistedItem = gateway.createPantryItem(item);
    Assert.assertThat(persistedItem.getName(), is(equalTo(itemName)));
    Assert.assertThat(persistedItem.getQuantity(), is(equalTo(quantity)));
    Assert.assertThat(persistedItem.getUnit(), is(equalTo(unit)));
    Assert.assertThat(persistedItem.getUuid(), is(notNullValue()));

  }

}
