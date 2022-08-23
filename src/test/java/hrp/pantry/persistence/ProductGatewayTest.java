package hrp.pantry.persistence;

import hrp.commons.testcases.GatewayTestCase;
import hrp.pantry.enums.PackagingUnit;
import hrp.pantry.gateways.ProductGateway;
import hrp.pantry.persistence.entities.Product;
import hrp.pantry.persistence.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductGatewayTest extends GatewayTestCase {

  @Autowired
  ProductGateway gateway;

  @Autowired
  ProductRepository repository;

  @BeforeEach
  public void setUp() {
    repository.deleteAll();
  }

  @Test
  public void createNewProductTest() {
    Product product = new Product("1234567890123", "Test Product", PackagingUnit.UNIT);
    Product persistedProduct = gateway.createOrUpdateProduct(product);
    assertThat(persistedProduct, samePropertyValuesAs(product));
  }

  @Test
  public void retrieveProductsByEanTest() {
    Product product = new Product("1234567890123", "Test Product", PackagingUnit.UNIT);
    Product product2 = new Product("1234567890123", "Test Product2", PackagingUnit.UNIT);
    Product product3 = new Product("3210987654321", "Test Product3", PackagingUnit.UNIT);
    repository.saveAll(Arrays.asList(product, product2, product3));

    List<Product> retrievedProducts = (List<Product>) gateway
        .retrieveProductsByEan("1234567890123");

    assertThat(retrievedProducts.size(), is(equalTo(2)));
    assertThat(retrievedProducts.get(0), samePropertyValuesAs(product));
    assertThat(retrievedProducts.get(1), samePropertyValuesAs(product2));
  }

  @Test
  public void retrieveHighestCountProduct() {
    Product product = new Product("1234567890123", "Coca", PackagingUnit.PACKAGE);
    Product product2 = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);
    Product product3 = new Product("1234567890123", "Cola 600ml", PackagingUnit.UNIT);
    product.setCount(10L);
    product2.setCount(15L);
    product3.setCount(3L);

    repository.saveAll(Arrays.asList(product, product2, product3));

    Product retrievedProduct = gateway.retrieveHighestCountProductByEanCode("1234567890123");

    assertThat(retrievedProduct, samePropertyValuesAs(product2));
  }

  @Test
  public void updateEntryTest(){
    Product product = new Product("1234567890123", "Test Product", PackagingUnit.UNIT);
    product = repository.save(product);
    product.setCount(2L);

    Product persistedProduct = gateway.createOrUpdateProduct(product);

    assertThat(repository.count(), is(equalTo(1L)));
    assertTrue(persistedProduct.getUpdatedAt().after(
        persistedProduct.getCreatedAt()
    ));
  }

}
