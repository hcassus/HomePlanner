package hrp.pantry.api;

import hrp.commons.testcases.LiveServerTestCase;
import hrp.pantry.enums.PackagingUnit;
import hrp.pantry.persistence.entities.Product;
import hrp.pantry.persistence.repositories.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

import static io.restassured.RestAssured.preemptive;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class ProductApiTest extends LiveServerTestCase {

  @MockBean
  private AuditorAware<String> auditorAware;

  @BeforeEach
  public void setup(){
    Mockito.when(auditorAware.getCurrentAuditor()).thenReturn(Optional.of(VALID_USERNAME));
  }
  @Autowired
  ProductRepository repository;

  @LocalServerPort
  String port;

  private final String VALID_USERNAME = System.getenv("VALID_USERNAME");
  private final String VALID_PASSWORD = System.getenv("VALID_PASSWORD");

  @BeforeEach
  public void setUp() {
    RestAssured.baseURI = "http://localhost:" + port;
    RestAssured.authentication = preemptive().basic(VALID_USERNAME,VALID_PASSWORD);

    repository.deleteAll();
  }

  @Test
  public void retrieveProductDataByEanTest(){
    Product product = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);
    repository.save(product);

    RestAssured
        .given()
          .contentType(ContentType.JSON)
        .when()
          .get("/product/" + product.getEanCode())
          .prettyPeek()
        .then()
          .contentType(ContentType.JSON)
          .statusCode(200)
          .body("name", equalTo(product.getName()))
          .body("unit", equalTo(product.getUnit().toString()))
          .body("eanCode", equalTo(product.getEanCode()))
          .body("createdAt", notNullValue())
          .body("updatedAt", notNullValue())
          .body(matchesJsonSchemaInClasspath("json_schemas/pantry/product-schema.json"));
  }
}
