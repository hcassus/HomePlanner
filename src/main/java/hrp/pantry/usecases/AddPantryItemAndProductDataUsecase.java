package hrp.pantry.usecases;

import hrp.pantry.gateways.PantryItemGateway;
import hrp.pantry.persistence.entities.PantryItem;
import hrp.pantry.persistence.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddPantryItemAndProductDataUsecase {

  private final CreateUniqueProductUsecase createUniqueProductUsecase;
  private final PantryItemGateway itemGateway;

  public PantryItem execute(PantryItem item){
    if(item.getEanCode() != null){
      Product product = new Product(item.getEanCode(), item.getName(), item.getUnit());
      createUniqueProductUsecase.execute(product);
    }
    return itemGateway.createOrUpdatePantryItem(item);
  }

}
