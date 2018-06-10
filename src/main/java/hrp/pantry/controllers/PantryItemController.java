package hrp.pantry.controllers;

import hrp.pantry.persistence.entities.PantryItem;
import hrp.pantry.usecases.AddPantryItemAndProductDataUsecase;
import hrp.pantry.usecases.DeleteCurrentUserPantryItemsUsecase;
import hrp.pantry.usecases.GetCurrentUserPantryItemsUsecase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/pantry/item")
@RequiredArgsConstructor
public class PantryItemController {

  private final AddPantryItemAndProductDataUsecase addProductAndItem;
  private final GetCurrentUserPantryItemsUsecase getCurrentUserPantryItemsUsecase;
  private final DeleteCurrentUserPantryItemsUsecase deleteCurrentUserPantryItemsUsecase;

  @RequestMapping(method = RequestMethod.POST)
  public PantryItem createPantryItem(@RequestBody PantryItem item){
    return addProductAndItem.execute(item);
  }

  @RequestMapping(method = RequestMethod.GET)
  public Iterable<PantryItem> retrievePantryItems(){
    return getCurrentUserPantryItemsUsecase.execute();
  }

  @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
  public void deletePantryItemByUuid(@PathVariable UUID uuid){
    deleteCurrentUserPantryItemsUsecase.execute(uuid);
  }
}
