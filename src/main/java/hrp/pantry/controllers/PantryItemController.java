package hrp.pantry.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hrp.pantry.gateways.PantryItemGateway;
import hrp.pantry.persistence.PantryItem;

@RestController
@RequestMapping(path = "/pantry")
public class PantryItemController {

  PantryItemGateway gateway;

  @Autowired
  public PantryItemController(PantryItemGateway gateway){
    this.gateway = gateway;
  }

  @RequestMapping(path = "/item", method = RequestMethod.POST)
  public PantryItem createPantryItem(@RequestBody PantryItem item){
    return gateway.createPantryItem(item);
  }
}
