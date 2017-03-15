package pantry.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import hrp.HomePlannerApp;
import hrp.pantry.enums.PackagingUnit;
import hrp.pantry.persistence.entities.Product;
import hrp.pantry.persistence.repositories.ProductRepository;
import hrp.pantry.services.ProductService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = HomePlannerApp.class, loader = SpringBootContextLoader.class)
public class ProductServiceTest {

  @Autowired
  ProductService service;

  @Autowired
  ProductRepository repository;

  @Before
  public void setUp(){
    repository.deleteAll();
  }

  @Test
  public void createNonExistingItemTest(){
    Product product = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);

    service.insertUniqueProduct(product);

    List<Product> persistedItems = (List<Product>) repository.findAll();

    assertThat(persistedItems.size(), is(equalTo(1)));
    assertThat(persistedItems.get(0), samePropertyValuesAs(product));
  }

  @Test
  public void createDistinctEanCodesItemTest(){
    Product product = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);
    Product product2 = new Product("3210987654321", "Coca Cola 2L", PackagingUnit.BOTTLE);

    service.insertUniqueProduct(product);
    service.insertUniqueProduct(product2);

    List<Product> persistedItems = (List<Product>) repository.findAll();

    assertThat(persistedItems.size(), is(equalTo(2)));
    assertThat(persistedItems.get(0), samePropertyValuesAs(product));
    assertThat(persistedItems.get(0).getCount(), is(equalTo(1L)));
    assertThat(persistedItems.get(1), samePropertyValuesAs(product2));
    assertThat(persistedItems.get(1).getCount(), is(equalTo(1L)));
  }

  @Test
  public void incrementIdenticalItemCountTest(){
    Product product = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);

    service.insertUniqueProduct(product);
    service.insertUniqueProduct(product);

    List<Product> persistedItems = (List<Product>) repository.findAll();

    assertThat(persistedItems.size(), is(equalTo(1)));
    assertThat(persistedItems.get(0).getName(), is(equalTo(product.getName())));
    assertThat(persistedItems.get(0).getEanCode(), is(equalTo(product.getEanCode())));
    assertThat(persistedItems.get(0).getUnit(), is(equalTo(product.getUnit())));
    assertThat(persistedItems.get(0).getCount(), is(equalTo(2L)));
  }

  @Test
  public void createMultipleNonIdenticalItemCountTest(){
    Product product = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);
    Product product2 = new Product("1234567890123", "Fanta 2L", PackagingUnit.BOTTLE);

    service.insertUniqueProduct(product);
    service.insertUniqueProduct(product2);

    List<Product> persistedItems = (List<Product>) repository.findAll();

    assertThat(persistedItems.size(), is(equalTo(2)));
    assertThat(persistedItems.get(0), samePropertyValuesAs(product));
    assertThat(persistedItems.get(0).getCount(), is(equalTo(1L)));
    assertThat(persistedItems.get(1), samePropertyValuesAs(product2));
    assertThat(persistedItems.get(1).getCount(), is(equalTo(1L)));
  }

  @Test
  public void incrementNonMatchingCaseItemCountTest(){
    Product product = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);
    Product product2 = new Product("1234567890123", "coca cola 2l", PackagingUnit.BOTTLE);
    Product product3 = new Product("1234567890123", "Coca cola 2L", PackagingUnit.BOTTLE);

    service.insertUniqueProduct(product);
    service.insertUniqueProduct(product2);
    service.insertUniqueProduct(product3);

    List<Product> persistedItems = (List<Product>) repository.findAll();

    assertThat(persistedItems.size(), is(equalTo(1)));
    assertThat(persistedItems.get(0).getName(), is(equalTo(product.getName())));
    assertThat(persistedItems.get(0).getEanCode(), is(equalTo(product.getEanCode())));
    assertThat(persistedItems.get(0).getUnit(), is(equalTo(product.getUnit())));
    assertThat(persistedItems.get(0).getCount(), is(equalTo(3L)));
  }

  @Test
  public void incrementNonMatchingSpaceItemCountTest(){
    Product product = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);
    Product product2 = new Product("1234567890123", "cocacola2l", PackagingUnit.BOTTLE);
    Product product3 = new Product("1234567890123", "Co ca cola2L", PackagingUnit.BOTTLE);

    service.insertUniqueProduct(product);
    service.insertUniqueProduct(product2);
    service.insertUniqueProduct(product3);

    List<Product> persistedItems = (List<Product>) repository.findAll();

    assertThat(persistedItems.size(), is(equalTo(1)));
    assertThat(persistedItems.get(0).getName(), is(equalTo(product.getName())));
    assertThat(persistedItems.get(0).getEanCode(), is(equalTo(product.getEanCode())));
    assertThat(persistedItems.get(0).getUnit(), is(equalTo(product.getUnit())));
    assertThat(persistedItems.get(0).getCount(), is(equalTo(3L)));
  }

  @Test
  public void incrementNonMatchingUnitItemCountTest(){
    Product product = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);
    Product product2 = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.UNIT);

    service.insertUniqueProduct(product);
    service.insertUniqueProduct(product2);

    List<Product> persistedItems = (List<Product>) repository.findAll();

    assertThat(persistedItems.size(), is(equalTo(2)));
    assertThat(persistedItems.get(0).getName(), is(equalTo(product.getName())));
    assertThat(persistedItems.get(0).getEanCode(), is(equalTo(product.getEanCode())));
    assertThat(persistedItems.get(0).getUnit(), is(equalTo(product.getUnit())));
    assertThat(persistedItems.get(0).getCount(), is(equalTo(1L)));
    assertThat(persistedItems.get(1).getName(), is(equalTo(product2.getName())));
    assertThat(persistedItems.get(1).getEanCode(), is(equalTo(product2.getEanCode())));
    assertThat(persistedItems.get(1).getUnit(), is(equalTo(product2.getUnit())));
    assertThat(persistedItems.get(1).getCount(), is(equalTo(1L)));
  }

  @Test
  public void returnSingleProductDataTest(){
    Product product = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);
    repository.save(product);

    Product retrievedProduct = service.retrieveItemDataByEan("1234567890123");

    assertThat(retrievedProduct, samePropertyValuesAs(product));
  }

  @Test
  public void returnOnlyProductWithHigestCountDataTest(){
    Product product = new Product("1234567890123", "Coca", PackagingUnit.PACKAGE);
    Product product2 = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);
    Product product3 = new Product("1234567890123", "Cola 600ml", PackagingUnit.UNIT);
    product.setCount(10L);
    product2.setCount(15L);
    product3.setCount(3L);

    repository.save(Arrays.asList(product,product2,product3));

    Product retrievedProduct = service.retrieveItemDataByEan("1234567890123");

    assertThat(retrievedProduct, samePropertyValuesAs(product2));
  }

  @Test
  public void returnNoItemIfCodeDoesNotExistTest(){
    Product retrievedProduct = service.retrieveItemDataByEan("1234567890123");

    assertThat(retrievedProduct, nullValue(Product.class));
  }



}
