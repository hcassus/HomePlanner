package pantry.api;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.core.Is.is;

import hrp.HomePlannerApp;
import hrp.pantry.enums.MeasurementUnits;
import hrp.pantry.persistence.PantryItem;
import hrp.pantry.persistence.PantryItemRepository;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = HomePlannerApp.class, loader = SpringBootContextLoader.class)
public class PantryItemApiTest {

  @Autowired
  PantryItemRepository repo;

  @LocalServerPort
  String port;

  String endpoint;

  TestRestTemplate restTemplate;


  @Before
  public void setUp(){
    endpoint = "http://localhost:"+ port +"/pantry/item";
    restTemplate = new TestRestTemplate();
    repo.deleteAll();
  }

  @Test
  public void createPantryItemTest() throws Exception {
    PantryItem item = new PantryItem(
        "Yogurt "+ System.currentTimeMillis(),
        null,
        null
    );

    ResponseEntity response = restTemplate.postForEntity(endpoint, item, PantryItem.class);
    PantryItem returnedItem = (PantryItem) response.getBody();

    assertThat(returnedItem, samePropertyValuesAs(item));
    assertThat(returnedItem.getUuid(), is(notNullValue()));
  }

  @Test
  public void retrieveAllCreatedItems(){
    PantryItem item1 = new PantryItem(
        "Coffee " + System.currentTimeMillis(),
        1L,
        MeasurementUnits.PACKAGE
    );
    PantryItem item2 = new PantryItem(
        "Onion " + System.currentTimeMillis(),
        3L,
        MeasurementUnits.UNIT
    );

    repo.save(item1);
    repo.save(item2);

    ResponseEntity<List> response = restTemplate.getForEntity(endpoint, List.class);
    List returnedItems = response.getBody();

    assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    assertThat(returnedItems.size(), is(equalTo(2)));
    // TODO: check content

  }

  @Test
  public void deletePantryItemByUuid(){
    PantryItem item = new PantryItem(
        "Coca Cola 500ml"+ System.currentTimeMillis(),
        2L,
        MeasurementUnits.BOTTLE
    );
    UUID uuid = repo.save(item).getUuid();

    restTemplate.delete(endpoint+ "/" + uuid);

    assertThat(repo.findAll(), emptyIterableOf(PantryItem.class));
  }

}
