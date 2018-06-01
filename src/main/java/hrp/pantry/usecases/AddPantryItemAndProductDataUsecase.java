package hrp.pantry.usecases;

import hrp.pantry.gateways.PantryItemGateway;
import hrp.pantry.persistence.entities.PantryItem;
import hrp.pantry.persistence.entities.Product;
import hrp.pantry.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddPantryItemAndProductDataUsecase {

  private final ProductService productService;
  private final PantryItemGateway itemGateway;

  public PantryItem execute(PantryItem item){
    if(item.getEanCode() != null){
      Product product = new Product(item.getEanCode(), item.getName(), item.getUnit());
      productService.insertUniqueProduct(product);
    }
    return itemGateway.createOrUpdatePantryItem(item);
  }

}
