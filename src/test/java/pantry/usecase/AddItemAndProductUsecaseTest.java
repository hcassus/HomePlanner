package pantry.usecase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import hrp.pantry.enums.PackagingUnit;
import hrp.pantry.gateways.PantryItemGateway;
import hrp.pantry.persistence.entities.PantryItem;
import hrp.pantry.persistence.entities.Product;
import hrp.pantry.services.CreateUniqueProductUsecase;
import hrp.pantry.usecases.AddPantryItemAndProductDataUsecase;
import java.sql.Timestamp;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class AddItemAndProductUsecaseTest {

  @InjectMocks
  private AddPantryItemAndProductDataUsecase usecase;

  @Mock
  private CreateUniqueProductUsecase service;

  @Mock
  private PantryItemGateway gateway;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Test
  public void createItemAndProductTest(){
    PantryItem item = new PantryItem("1234567890123", "Test Item", 10, PackagingUnit.BOTTLE, new Timestamp(System.currentTimeMillis()));
    ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

    usecase.execute(item);

    verify(gateway, times(1)).createOrUpdatePantryItem(item);
    verify(service, times(1)).execute(captor.capture());

    Product persistedProduct = captor.getValue();
    assertThat(persistedProduct.getEanCode(), is(equalTo(item.getEanCode())));
    assertThat(persistedProduct.getName(), is(equalTo(item.getName())));
    assertThat(persistedProduct.getUnit(), is(equalTo(item.getUnit())));
  }

  @Test
  public void noBarCodeCreatesNoProductTest(){
    PantryItem item = new PantryItem(null, "Test Item", 10, PackagingUnit.BOTTLE, new Timestamp(System.currentTimeMillis()));

    usecase.execute(item);

    verify(gateway, times(1)).createOrUpdatePantryItem(item);
    verify(service, times(0)).execute(any(Product.class));
  }
}
