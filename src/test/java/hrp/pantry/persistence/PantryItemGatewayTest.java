package hrp.pantry.persistence;

import hrp.commons.testcases.GatewayTestCase;
import hrp.pantry.enums.PackagingUnit;
import hrp.pantry.gateways.PantryItemGateway;
import hrp.pantry.persistence.entities.PantryItem;
import hrp.pantry.persistence.repositories.PantryItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.AuditorAware;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PantryItemGatewayTest extends GatewayTestCase {

  @MockBean
  private AuditorAware<String> auditorAware;

  @BeforeEach
  public void setup(){
    Mockito.when(auditorAware.getCurrentAuditor()).thenReturn(Optional.of(VALID_USERNAME));
  }

  @Autowired
  private PantryItemGateway gateway;

  @Autowired
  private PantryItemRepository repository;

  @BeforeEach
  public void setUp(){
    Mockito.when(auditorAware.getCurrentAuditor()).thenReturn(Optional.of(VALID_USERNAME));
    repository.deleteAll();
  }

  @Test
  public void createPantryItemTest(){
    PantryItem item = new PantryItem(
        "1234567890123",
        "Milk "+System.currentTimeMillis(),
        10,
        PackagingUnit.CARTON,
        new Timestamp(System.currentTimeMillis())
    );

    PantryItem persistedItem = gateway.createOrUpdatePantryItem(item);

    assertThat(persistedItem, samePropertyValuesAs(item));
  }

  @Test
  public void retrieveAllPantryItemsTest(){
    PantryItem item1 = new PantryItem(
        "1234567890123",
        "Cheese " + System.currentTimeMillis(),
        1,
        PackagingUnit.PACKAGE,
        new Timestamp(System.currentTimeMillis())
    );

    PantryItem item2 = new PantryItem(
        "1234567890123",
        "Lime " + System.currentTimeMillis(),
        4,
        PackagingUnit.UNIT,
        new Timestamp(System.currentTimeMillis())
    );

    repository.save(item1);
    repository.save(item2);

    List<PantryItem> items = (List<PantryItem>) gateway.retrieveAllCurrentUserPantryItems(VALID_USERNAME);

    assertThat(items.get(0), samePropertyValuesAs(item1));
    assertThat(items.get(1), samePropertyValuesAs(item2));
  }

  @Test
  public void deleteItemByUuid(){
    PantryItem item = new PantryItem(
        "1234567890123",
        "Bread "+ System.currentTimeMillis(),
        2,
        PackagingUnit.PACKAGE,
        new Timestamp(System.currentTimeMillis())
    );
    item = repository.save(item);

    gateway.deleteCurrentUserItemByUuid(item.getUuid(), VALID_USERNAME);

    assertThat(gateway.retrieveAllCurrentUserPantryItems(VALID_USERNAME), emptyIterableOf(PantryItem.class));
  }

  @Test
  public void updateEntryTest(){
    PantryItem item = new PantryItem(
        "1234567890123",
        "Test Product",
        10,
        PackagingUnit.UNIT,
        new Timestamp(System.currentTimeMillis())
    );
    item = repository.save(item);
    item.setQuantity(12);

    PantryItem persistedItem = gateway.createOrUpdatePantryItem(item);

    assertThat(repository.count(), is(equalTo(1L)));
    assertTrue(persistedItem.getUpdatedAt().after(
        persistedItem.getCreatedAt()
    ));


  }

}
