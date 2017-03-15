package hrp.pantry.gateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hrp.pantry.persistence.entities.Product;
import hrp.pantry.persistence.repositories.ProductRepository;

@Repository
public class ProductGateway {

  @Autowired
  ProductRepository repository;

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
