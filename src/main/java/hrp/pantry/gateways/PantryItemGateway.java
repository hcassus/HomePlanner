package hrp.pantry.gateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hrp.pantry.persistence.PantryItem;
import hrp.pantry.persistence.PantryItemRepository;

@Repository
public class PantryItemGateway {

  PantryItemRepository repository;

  @Autowired
  public PantryItemGateway(PantryItemRepository repository){
    this.repository = repository;
  }

  public PantryItem createPantryItem(PantryItem item) {
    return repository.save(item);
  }
}
