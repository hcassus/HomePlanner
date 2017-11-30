package pantry.service;

import hrp.pantry.enums.PackagingUnit;
import hrp.pantry.gateways.ProductGateway;
import hrp.pantry.persistence.entities.Product;
import hrp.pantry.services.ProductService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

  @InjectMocks
  ProductService service;

  @Mock
  ProductGateway gatewayMock;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Test
  public void createNonExistingItemTest(){
    Product product = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);
    ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
    when(gatewayMock.retrieveProductsByEan(product.getEanCode())).thenReturn(new ArrayList<Product>());

    service.insertUniqueProduct(product);

    verify(gatewayMock, times(1)).createOrUpdateProduct(captor.capture());
    assertThat(captor.getValue().getCount(), is(equalTo(1L)));
  }

  @Test
  public void incrementIdenticalItemCountTest(){
    Product product = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);
    when(gatewayMock.retrieveProductsByEan(product.getEanCode())).thenReturn(Arrays.asList(product));
    ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

    service.insertUniqueProduct(product);

    verify(gatewayMock, times(1)).createOrUpdateProduct(captor.capture());
    assertThat(captor.getValue().getCount(), is(equalTo(2L)));
  }

  @Test
  public void createMultipleNonIdenticalNamesItemCountTest(){
    Product product = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);
    when(gatewayMock.retrieveProductsByEan(product.getEanCode())).thenReturn(Arrays.asList(product));
    Product product2 = new Product("1234567890123", "Fanta 2L", PackagingUnit.BOTTLE);
    ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

    service.insertUniqueProduct(product2);

    verify(gatewayMock, times(1)).createOrUpdateProduct(captor.capture());
    assertThat(captor.getValue().getName(), is(equalTo(product2.getName())));
    assertThat(captor.getValue().getCount(), is(equalTo(1L)));
  }

  @Test
  public void createMultipleNonIdenticalUnitsItemCountTest(){
    Product product = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);
    when(gatewayMock.retrieveProductsByEan(product.getEanCode())).thenReturn(Arrays.asList(product));
    Product product2 = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.UNIT);
    ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

    service.insertUniqueProduct(product2);

    verify(gatewayMock, times(1)).createOrUpdateProduct(captor.capture());
    assertThat(captor.getValue().getUnit(), is(equalTo(product2.getUnit())));
    assertThat(captor.getValue().getCount(), is(equalTo(1L)));
  }

  @Test
  public void incrementNonMatchingCaseItemCountTest(){
    Product product = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);
    when(gatewayMock.retrieveProductsByEan(product.getEanCode())).thenReturn(Arrays.asList(product));
    Product product2 = new Product("1234567890123", "coca cola 2l", PackagingUnit.BOTTLE);
    ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

    service.insertUniqueProduct(product2);

    verify(gatewayMock, times(1)).createOrUpdateProduct(captor.capture());
    assertThat(captor.getValue().getName(), is(equalTo(product.getName())));
    assertThat(captor.getValue().getCount(), is(equalTo(2L)));
  }

  @Test
  public void incrementNonMatchingSpaceItemCountTest(){
    Product product = new Product("1234567890123", "Coca Cola 2L", PackagingUnit.BOTTLE);
    when(gatewayMock.retrieveProductsByEan(product.getEanCode())).thenReturn(Arrays.asList(product));
    Product product2 = new Product("1234567890123", "C o c aC o l a2 L", PackagingUnit.BOTTLE);
    ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

    service.insertUniqueProduct(product2);

    verify(gatewayMock, times(1)).createOrUpdateProduct(captor.capture());
    assertThat(captor.getValue().getName(), is(equalTo(product.getName())));
    assertThat(captor.getValue().getCount(), is(equalTo(2L)));
  }

  @Test
  public void returnSingleProductDataTest(){
    String eanCode = "1234567890123";

    service.retrieveItemDataByEan(eanCode);

    verify(gatewayMock, times(1)).retrieveHighestCountProductByEanCode(eanCode);
  }
}
