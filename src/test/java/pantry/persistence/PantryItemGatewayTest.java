package pantry.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import commons.testcases.PersistencyTestCase;
import hrp.pantry.enums.PackagingUnit;
import hrp.pantry.gateways.PantryItemGateway;
import hrp.pantry.persistence.entities.PantryItem;
import hrp.pantry.persistence.repositories.PantryItemRepository;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PantryItemGatewayTest extends PersistencyTestCase{

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
        "1234567890123",
        "Milk "+System.currentTimeMillis(),
        10,
        PackagingUnit.CARTON
    );

    PantryItem persistedItem = gateway.createOrUpdatePantryItem(item);

    assertThat(persistedItem, samePropertyValuesAs(persistedItem));
  }

  @Test
  public void retrieveAllPantryItemsTest(){
    PantryItem item1 = new PantryItem(
        "1234567890123",
        "Cheese " + System.currentTimeMillis(),
        1,
        PackagingUnit.PACKAGE
    );

    PantryItem item2 = new PantryItem(
        "1234567890123",
        "Lime " + System.currentTimeMillis(),
        4,
        PackagingUnit.UNIT
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
        "1234567890123",
        "Bread "+ System.currentTimeMillis(),
        2,
        PackagingUnit.PACKAGE
    );
    item = repository.save(item);

    gateway.deleteItemByUuid(item.getUuid());

    assertThat(gateway.retrieveAllPantryItems(), emptyIterableOf(PantryItem.class));
  }

  @Test
  public void updateEntryTest(){
    PantryItem item = new PantryItem(
        "1234567890123",
        "Test Product",
        10,
        PackagingUnit.UNIT
    );
    item = repository.save(item);
    item.setQuantity(12);

    PantryItem persistedItem = gateway.createOrUpdatePantryItem(item);

    assertThat(repository.count(), is(equalTo(1L)));
    Assert.assertTrue(persistedItem.getUpdatedAt().after(
        persistedItem.getCreatedAt()
    ));


  }

}
