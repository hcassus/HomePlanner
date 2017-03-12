package pantry.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import hrp.HomePlannerApp;
import hrp.pantry.enums.MeasurementUnits;
import hrp.pantry.gateways.PantryItemGateway;
import hrp.pantry.persistence.PantryItem;
import hrp.pantry.persistence.PantryItemRepository;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = HomePlannerApp.class, loader = SpringBootContextLoader.class)
public class PantryItemGatewayTest {

  @Autowired
  private PantryItemGateway gateway;

  @Autowired
  private PantryItemRepository repository;

  @Before
  public void setUp(){
    repository.deleteAll();
  }

  @Test
  public void createPantryItemTest(){
    PantryItem item = new PantryItem(
        "Milk "+System.currentTimeMillis(),
        10,
        MeasurementUnits.LITER
    );

    PantryItem persistedItem = gateway.createPantryItem(item);

    assertThat(persistedItem, samePropertyValuesAs(persistedItem));
  }

  @Test
  public void retrieveAllPantryItemsTest(){
    PantryItem item1 = new PantryItem(
        "Cheese " + System.currentTimeMillis(),
        1,
        MeasurementUnits.PACKAGE
    );

    PantryItem item2 = new PantryItem(
        "Lime " + System.currentTimeMillis(),
        4,
        MeasurementUnits.UNIT
    );

    repository.save(item1);
    repository.save(item2);

    List<PantryItem> items = (List<PantryItem>) gateway.retrieveAllPantryItems();

    assertThat(items.get(0), samePropertyValuesAs(item1));
    assertThat(items.get(1), samePropertyValuesAs(item2));
  }

  @Test
  public void deleteItemByUuid(){
    PantryItem item = new PantryItem(
        "Bread "+ System.currentTimeMillis(),
        2,
        MeasurementUnits.PACKAGE
    );
    item = repository.save(item);

    gateway.deleteItemByUuid(item.getUuid());

    assertThat(gateway.retrieveAllPantryItems(), emptyIterableOf(PantryItem.class));

  }

}
