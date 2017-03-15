package hrp.pantry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hrp.pantry.gateways.ProductGateway;
import hrp.pantry.persistence.entities.Product;

@Service
public class ProductService {

  ProductGateway gateway;

  @Autowired
  public ProductService(ProductGateway gateway){
    this.gateway = gateway;
  }

  public Product insertUniqueProduct(Product product){
    Iterable<Product> products = gateway.retrieveProductsByEan(product.getEanCode());

    for (Product retrievedProduct: products
         ) {
      if (propertiesMatch(retrievedProduct, product)){
        retrievedProduct.setCount(retrievedProduct.getCount() + 1);
        return gateway.createOrUpdateProduct(retrievedProduct);
      }
    }
    
    return gateway.createOrUpdateProduct(product);
  }

  private boolean propertiesMatch(Product retrievedProduct, Product product){
    boolean nameMatches = getNormalizedName(product).equals(getNormalizedName(retrievedProduct));
    boolean unitMatches = product.getUnit().equals(retrievedProduct.getUnit());
    return nameMatches && unitMatches;
  }

  private String getNormalizedName(Product product){
    return product.getName().replace(" ","").toLowerCase();
  }

  public Product retrieveItemDataByEan(String eanCode) {
    return gateway.retrieveHighestCountProductByEanCode(eanCode);
  }
}
