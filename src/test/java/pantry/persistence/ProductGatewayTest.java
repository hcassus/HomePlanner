package pantry.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import hrp.HomePlannerApp;
import hrp.pantry.enums.PackagingUnit;
import hrp.pantry.gateways.ProductGateway;
import hrp.pantry.persistence.entities.Product;
import hrp.pantry.persistence.repositories.ProductRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = HomePlannerApp.class, loader = SpringBootContextLoader.class)
public class ProductGatewayTest {

  @Autowired
  ProductGateway gateway;

  @Autowired
  ProductRepository repository;

  @Test
  public void createNewProductTest(){
    Product product = new Product("1234567890123", "Test Product", PackagingUnit.UNIT);
    Product persistedProduct = gateway.createOrUpdateProduct(product);
    assertThat(persistedProduct, samePropertyValuesAs(product));
  }

//  @Test
//  public void createOrIncrementProductTest(){
//    Product product = new Product("1234567890123", "Test Product", PackagingUnit.UNIT);
//    Product product2 = new Product("1234567890123", "Test Product", PackagingUnit.UNIT);
//    gateway.createOrUpdateProduct(product);
//    gateway.createOrUpdateProduct(product2);
//
//    List<Product> products = (List<Product>) gateway.retrieveProductsByEan("1234567890123");
//
//
//    assertThat(products.size(), is(equalTo(1)));
//    assertThat(products.get(0), samePropertyValuesAs(product));
//  }

  @Test
  public void retrieveProductsByEanTest(){
    Product product = new Product("1234567890123", "Test Product", PackagingUnit.UNIT);
    Product product2 = new Product("1234567890123", "Test Product2", PackagingUnit.UNIT);
    Product product3 = new Product("3210987654321", "Test Product3", PackagingUnit.UNIT);
    repository.save(Arrays.asList(product, product2));

    List<Product> retrievedProducts = (List<Product>) gateway.retrieveProductsByEan("1234567890123");

    assertThat(retrievedProducts.size(), is(equalTo(2)));
    assertThat(retrievedProducts.get(0), samePropertyValuesAs(product));
    assertThat(retrievedProducts.get(1), samePropertyValuesAs(product2));
  }

}