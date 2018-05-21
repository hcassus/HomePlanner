package hrp.pantry.usecases;

import hrp.pantry.gateways.PantryItemGateway;
import hrp.pantry.persistence.entities.PantryItem;
import hrp.pantry.persistence.entities.Product;
import hrp.pantry.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddPantryItemAndProductDataUsecase {

  ProductService productService;
  PantryItemGateway itemGateway;

  @Autowired
  public AddPantryItemAndProductDataUsecase(ProductService productService, PantryItemGateway itemGateway){
    this.productService = productService;
    this.itemGateway = itemGateway;
  }

  public PantryItem execute(PantryItem item){
    if(item.getEanCode() != null){
      Product product = new Product(item.getEanCode(), item.getName(), item.getUnit());
      productService.insertUniqueProduct(product);
    }
    return itemGateway.createOrUpdatePantryItem(item);
  }

}
