package hrp.pantry.api;

import hrp.commons.testcases.LiveServerTestCase;
import hrp.pantry.enums.PackagingUnit;
import hrp.pantry.persistence.entities.PantryItem;
import hrp.pantry.persistence.entities.Product;
import hrp.pantry.persistence.repositories.PantryItemRepository;
import hrp.pantry.persistence.repositories.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.AuditorAware;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PantryItemApiTest extends LiveServerTestCase {

    private static final String PANTRY_ITEM_PATH = "/pantry/item/";

    @MockBean
    private AuditorAware<String> auditorAware;

    @LocalServerPort
    String port;

    @Autowired
    PantryItemRepository itemRepository;

    @Autowired
    ProductRepository productRepository;

    private final String VALID_USERNAME = System.getenv("VALID_USERNAME");
    private final String VALID_PASSWORD = System.getenv("VALID_PASSWORD");

    private SessionFilter sessionFilter;
    private String xsrfToken;

    @BeforeEach
    public void setUp() {
        Mockito
                .when(auditorAware.getCurrentAuditor())
                .thenReturn(Optional.of(VALID_USERNAME));

        RestAssured.baseURI = "http://localhost:" + port;

        sessionFilter = new SessionFilter();

        xsrfToken = given()
                .auth()
                .basic(VALID_USERNAME, VALID_PASSWORD)
                .filter(sessionFilter)
                .log()
                    .all()
        .when()
                .get(PANTRY_ITEM_PATH)
                .prettyPeek()
        .then()
                .extract()
                    .cookie("XSRF-TOKEN");

        itemRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void blackBoxItemCreationTest() throws JSONException {
        JSONObject item = new JSONObject()
                .put("eanCode", "1234567890123")
                .put("name", "Coca Cola 2l")
                .put("quantity", 10)
                .put("unit", "BOTTLE")
                .put("expiresAt", System.currentTimeMillis());

        given()
                .contentType(ContentType.JSON)
                .filter(sessionFilter)
                .cookie("XSRF-TOKEN", xsrfToken)
                .header("X-XSRF-TOKEN", xsrfToken)
                .body(item.toString())
                .log()
                .all()
        .when()
                .post(PANTRY_ITEM_PATH)
                .prettyPeek()
        .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("name", equalTo(item.get("name")))
                .body("quantity", equalTo(item.get("quantity")))
                .body("unit", equalTo(item.get("unit")))
                .body("uuid", notNullValue())
                .body("createdAt", notNullValue())
                .body("updatedAt", notNullValue())
                .body(matchesJsonSchemaInClasspath("json_schemas/pantry/pantry-item-schema.json"));

        List<Product> retrievedProducts = (List<Product>) productRepository.findProductsByEanCode((String) item.get("eanCode"));
        List<PantryItem> retrievedItems = (List<PantryItem>) itemRepository.findAll();
        assertThat(retrievedItems.size(), is(equalTo(1)));
        assertThat(retrievedProducts.size(), is(equalTo(1)));
        assertThat(retrievedProducts.get(0).getName(), is(equalTo((String) item.get("name"))));
        assertThat(retrievedProducts.get(0).getUnit().toString(), is(equalTo(item.get("unit"))));
        assertThat(retrievedProducts.get(0).getEanCode(), is(equalTo((String) item.get("eanCode"))));
    }

  @Test
  public void retrieveAllCreatedItemsTest(){
    PantryItem item1 = new PantryItem(
        "1234567890123",
        "Erdinger Kristall 500ml",
        1,
        PackagingUnit.BOTTLE,
        new Timestamp(System.currentTimeMillis())
    );
    PantryItem item2 = new PantryItem(
        "1234567890123",
        "Erdinger Kristall 500ml",
        2,
        PackagingUnit.BOTTLE,
        new Timestamp(System.currentTimeMillis())
    );
    List<PantryItem> items = Arrays.asList(item1,item2);
    itemRepository.saveAll(items);

    given()
            .contentType(ContentType.JSON)
            .filter(sessionFilter)
            .cookie("XSRF-TOKEN", xsrfToken)
            .header("X-XSRF-TOKEN", xsrfToken)
        .when()
            .get(PANTRY_ITEM_PATH)
            .prettyPeek()
        .then()
          .statusCode(200)
          .body("get(0).name", is(equalTo(item1.getName())))
          .body("get(0).quantity", equalTo(item1.getQuantity()))
          .body("get(0).unit", equalTo(item1.getUnit().toString()))
          .body("get(0).updatedAt", equalTo(getUtcDateTimeString(item1.getExpiresAt())))
          .body("get(0).uuid", notNullValue())
          .body("get(0).createdAt", equalTo(getUtcDateTimeString(item1.getCreatedAt())))
          .body("get(0).updatedAt", equalTo(getUtcDateTimeString(item1.getUpdatedAt())))
          .body("get(1).name", is(equalTo(item2.getName())))
          .body("get(1).quantity", equalTo(item2.getQuantity()))
          .body("get(1).expiresAt", equalTo(getUtcDateTimeString(item2.getExpiresAt())))
          .body("get(1).unit", equalTo(item2.getUnit().toString()))
          .body("get(1).uuid", notNullValue())
          .body("get(1).createdAt", equalTo(getUtcDateTimeString(item2.getCreatedAt())))
          .body("get(1).updatedAt", equalTo(getUtcDateTimeString(item2.getUpdatedAt())))
          .body(matchesJsonSchemaInClasspath("json_schemas/pantry/pantry-item-list-schema.json"));
  }

    @Test
    public void deleteItemByUuidTest() {
        PantryItem item = new PantryItem(
                "1234567890123",
                "Coca Cola 500ml" + System.currentTimeMillis(),
                2,
                PackagingUnit.BOTTLE,
                new Timestamp(System.currentTimeMillis())
        );
        UUID uuid = itemRepository.save(item).getUuid();

        given()
            .contentType(ContentType.JSON)
            .filter(sessionFilter)
            .cookie("XSRF-TOKEN", xsrfToken)
            .header("X-XSRF-TOKEN", xsrfToken)
        .when()
            .delete(PANTRY_ITEM_PATH + uuid)
            .then()
            .statusCode(200)
            .body(is(""));

    assertThat(itemRepository.findAll(), emptyIterableOf(PantryItem.class));
  }

  private String getUtcDateTimeString(Timestamp date){
      DateTime dateTime = new DateTime(date).withZone(DateTimeZone.UTC);
      DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
      return fmt.print(dateTime);
  }


}

