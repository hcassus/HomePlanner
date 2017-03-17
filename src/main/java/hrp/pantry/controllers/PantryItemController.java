package hrp.pantry.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import hrp.pantry.gateways.PantryItemGateway;
import hrp.pantry.persistence.entities.PantryItem;
import hrp.pantry.usecases.AddPantryItemAndProductDataUsecase;

@RestController
@RequestMapping(path = "/pantry/item")
public class PantryItemController {

  PantryItemGateway gateway;

  @Autowired
  AddPantryItemAndProductDataUsecase addProductAndItem;

  @Autowired
  public PantryItemController(PantryItemGateway gateway){
    this.gateway = gateway;
  }

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
