package hrp.pantry.usecase;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import hrp.pantry.gateways.ProductGateway;
import hrp.pantry.usecases.RetrieveTopProductByEanUsecase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RetrieveTopProductByEanUsecaseTest {

  @InjectMocks
  private RetrieveTopProductByEanUsecase retrieveTopProductByEanUsecase;

  @Mock
  private ProductGateway gatewayMock;

  @Test
  public void returnSingleProductDataTest(){
    String eanCode = "1234567890123";

    retrieveTopProductByEanUsecase.execute(eanCode);

    verify(gatewayMock, times(1)).retrieveHighestCountProductByEanCode(eanCode);
  }

}
