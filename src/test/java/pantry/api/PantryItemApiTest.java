package pantry.api;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import hrp.HomePlannerApp;
import hrp.pantry.persistence.PantryItem;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = HomePlannerApp.class, loader = SpringBootContextLoader.class)
public class PantryItemApiTest {

  @Autowired
  WebApplicationContext context;

  @LocalServerPort
  String port;

  String endpoint;

  TestRestTemplate restTemplate;

  @Captor
  ArgumentCaptor<PantryItem> itemCaptor;

  @Before
  public void setUp(){
    endpoint = "http://localhost:"+ port +"/pantry/item";
    restTemplate = new TestRestTemplate();
  }

  @Test
  public void createPantryItemTest() throws Exception {
    PantryItem item = new PantryItem("Yogurt "+ System.currentTimeMillis(), null, null);

    PantryItem returnedItem = restTemplate.postForObject(endpoint, item, PantryItem.class);

    assertThat(returnedItem.getName(), is(equalTo(item.getName())));
    assertThat(returnedItem.getUuid(), is(notNullValue()));
  }
}
