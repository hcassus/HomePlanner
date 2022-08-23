package hrp.pantry.usecase;

import hrp.pantry.gateways.ProductGateway;
import hrp.pantry.usecases.RetrieveTopProductByEanUsecase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
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
