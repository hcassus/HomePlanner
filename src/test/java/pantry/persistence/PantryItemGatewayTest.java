package pantry.persistence;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import hrp.HomePlannerApp;
import hrp.pantry.enums.MeasurementUnits;
import hrp.pantry.gateways.PantryItemGateway;
import hrp.pantry.persistence.PantryItem;
import hrp.pantry.persistence.PantryItemRepository;

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
        10L,
        MeasurementUnits.LITER
    );

    PantryItem persistedItem = gateway.createPantryItem(item);

    Assert.assertThat(persistedItem.getName(), is(equalTo(item.getName())));
    Assert.assertThat(persistedItem.getQuantity(), is(equalTo(item.getQuantity())));
    Assert.assertThat(persistedItem.getUnit(), is(equalTo(item.getUnit())));
    Assert.assertThat(persistedItem.getUuid(), is(notNullValue()));
  }

  @Test
  public void retrieveAllPantryItemsTest(){
    PantryItem item1 = new PantryItem(
        "Cheese " + System.currentTimeMillis(),
        1L,
        MeasurementUnits.PACKAGE
    );

    PantryItem item2 = new PantryItem(
        "Lime " + System.currentTimeMillis(),
        4L,
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
        2L,
        MeasurementUnits.PACKAGE
    );
    item = repository.save(item);

    gateway.deleteItemByUuid(item.getUuid());

    assertThat(gateway.retrieveAllPantryItems(), emptyIterableOf(PantryItem.class));

  }

}
