package hrp.pantry.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import hrp.pantry.persistence.entities.Product;

public interface ProductRepository extends CrudRepository<Product,Long>{
  Iterable<Product> findProductsByEanCode(String eanCode);
}
