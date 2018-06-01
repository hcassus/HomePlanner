package hrp.pantry.usecases;

import hrp.pantry.gateways.ProductGateway;
import hrp.pantry.persistence.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RetrieveTopProductByEanUsecase {

  private final ProductGateway productGateway;

  public Product execute(String eanCode) {
    return productGateway.retrieveHighestCountProductByEanCode(eanCode);
  }
}
