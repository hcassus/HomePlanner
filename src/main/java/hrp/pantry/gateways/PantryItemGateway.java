package hrp.pantry.gateways;

import hrp.pantry.persistence.PantryItem;
import hrp.pantry.persistence.PantryItemRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

  public Iterable<PantryItem> retrieveAllPantryItems() {
    return repository.findAll();
  }

  public void deleteItemByUuid(UUID uuid) {
    repository.deletePantryItemByUuid(uuid);
  }
}
