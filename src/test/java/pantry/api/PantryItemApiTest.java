package pantry.api;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import hrp.HomePlannerApp;
import hrp.pantry.enums.PackagingUnit;
import hrp.pantry.persistence.entities.PantryItem;
import hrp.pantry.persistence.repositories.PantryItemRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = HomePlannerApp.class, loader = SpringBootContextLoader.class)
public class PantryItemApiTest {

  @LocalServerPort
  String port;

  @Autowired
  PantryItemRepository repository;

  @Before
  public void setUp() {
    RestAssured.baseURI = "http://localhost:" + port;
    repository.deleteAll();
  }

  @Test
  public void blackBoxItemCreationTest() throws JSONException {
    JSONObject item = new JSONObject()
        .put("name", "Coca Cola 2l")
        .put("quantity", 10)
        .put("unit", "BOTTLE");

    RestAssured
        .given()
        .contentType(ContentType.JSON)
        .body(item.toString())
        .when()
        .post("/pantry/item")
        .then()
        .contentType(ContentType.JSON)
        .statusCode(200)
        .body("name", equalTo(item.get("name")))
        .body("quantity", equalTo(item.get("quantity")))
        .body("unit", equalTo(item.get("unit")))
        .body("uuid", notNullValue())
        .body("createdAt", notNullValue())
        .body("updatedAt", notNullValue());
  }

  @Test
  public void retrieveAllCreatedItemsTest(){
    PantryItem item1 = new PantryItem("Erdinger Kristall 500ml",1, PackagingUnit.BOTTLE);
    PantryItem item2 = new PantryItem("Erdinger Kristall 500ml",2, PackagingUnit.BOTTLE);
    List<PantryItem> items = Arrays.asList(item1,item2);
    repository.save(items);

    RestAssured
        .given()
          .contentType(ContentType.JSON)
        .when()
          .get("/pantry/item")
        .then()
          .statusCode(200)
          .body("get(0).name", is(equalTo(item1.getName())))
          .body("get(0).quantity", equalTo(item1.getQuantity()))
          .body("get(0).unit", equalTo(item1.getUnit().toString()))
          .body("get(0).uuid", notNullValue())
          .body("get(0).createdAt", notNullValue())
          .body("get(0).updatedAt", notNullValue())
          .body("get(1).name", is(equalTo(item2.getName())))
          .body("get(1).quantity", equalTo(item2.getQuantity()))
          .body("get(1).unit", equalTo(item2.getUnit().toString()))
          .body("get(1).uuid", notNullValue())
          .body("get(1).createdAt", notNullValue())
          .body("get(1).updatedAt", notNullValue());
  }

  @Test
  public void deleteItemByUuidTest(){
    PantryItem item = new PantryItem(
        "Coca Cola 500ml"+ System.currentTimeMillis(),
        2,
        PackagingUnit.BOTTLE
    );
    UUID uuid = repository.save(item).getUuid();

    RestAssured
        .given()
          .contentType(ContentType.JSON)
        .when()
          .delete("/pantry/item/" + uuid)
        .then()
          .statusCode(200)
          .body(is(""));

    assertThat(repository.findAll(), emptyIterableOf(PantryItem.class));
  }
}
