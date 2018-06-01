package hrp.pantry.controllers;

import hrp.pantry.gateways.PantryItemGateway;
import hrp.pantry.persistence.entities.PantryItem;
import hrp.pantry.usecases.AddPantryItemAndProductDataUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/pantry/item")
@RequiredArgsConstructor
public class PantryItemController {

  private final PantryItemGateway gateway;
  private final AddPantryItemAndProductDataUsecase addProductAndItem;

  @RequestMapping(method = RequestMethod.POST)
  public PantryItem createPantryItem(@RequestBody PantryItem item){
    return addProductAndItem.execute(item);
  }

  @RequestMapping(method = RequestMethod.GET)
  public Iterable<PantryItem> retrievePantryItems(){
    return gateway.retrieveAllPantryItems();
  }

  @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
  public void deletePantryItemByUuid(@PathVariable UUID uuid){
    gateway.deleteItemByUuid(uuid);
  }
}
