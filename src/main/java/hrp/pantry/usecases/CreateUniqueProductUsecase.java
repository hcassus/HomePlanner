package hrp.pantry.usecases;

import hrp.pantry.gateways.ProductGateway;
import hrp.pantry.persistence.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUniqueProductUsecase {

  private final ProductGateway gateway;

  public Product execute(Product product) {
    Iterable<Product> products = gateway.retrieveProductsByEan(product.getEanCode());

    for (Product retrievedProduct : products
        ) {
      if (propertiesMatch(retrievedProduct, product)) {
        retrievedProduct.setCount(retrievedProduct.getCount() + 1);
        return gateway.createOrUpdateProduct(retrievedProduct);
      }
    }

    return gateway.createOrUpdateProduct(product);
  }

  private boolean propertiesMatch(Product retrievedProduct, Product product) {
    boolean nameMatches = getNormalizedName(product).equals(getNormalizedName(retrievedProduct));
    boolean unitMatches = product.getUnit().equals(retrievedProduct.getUnit());
    return nameMatches && unitMatches;
  }

  private String getNormalizedName(Product product) {
    return product.getName().replace(" ", "").toLowerCase();
  }

}
