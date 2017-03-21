package hrp.pantry.gateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import hrp.pantry.persistence.entities.PantryItem;
import hrp.pantry.persistence.repositories.PantryItemRepository;

@Repository
public class PantryItemGateway {

  PantryItemRepository repository;

  @Autowired
  public PantryItemGateway(PantryItemRepository repository){
    this.repository = repository;
  }

  public PantryItem createOrUpdatePantryItem(PantryItem item) {
    return repository.save(item);
  }

  public Iterable<PantryItem> retrieveAllPantryItems() {
    return repository.findAll();
  }

  public void deleteItemByUuid(UUID uuid) {
    repository.deletePantryItemByUuid(uuid);
  }
}
