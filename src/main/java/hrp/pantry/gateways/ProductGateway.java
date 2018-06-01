package hrp.pantry.gateways;

import hrp.pantry.persistence.entities.Product;
import hrp.pantry.persistence.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductGateway {

  private final ProductRepository repository;

  public Product createOrUpdateProduct(Product product) {
    return repository.save(product);
  }

  public Iterable<Product> retrieveProductsByEan(String eanCode) {
    return repository.findProductsByEanCode(eanCode);
  }

  public Product retrieveHighestCountProductByEanCode(String eanCode) {
    return repository.findTop1ProductsByEanCodeOrderByCountDesc(eanCode);
  }
}
