package hrp.pantry.gateways;

import hrp.pantry.persistence.entities.PantryItem;
import hrp.pantry.persistence.repositories.PantryItemRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PantryItemGateway {

  private final PantryItemRepository repository;

  public PantryItem createOrUpdatePantryItem(PantryItem item) {
    return repository.save(item);
  }

  public Iterable<PantryItem> retrieveAllCurrentUserPantryItems(String currentUser) {
    return repository.findAllByCreatedBy(currentUser);
  }

  public void deleteCurrentUserItemByUuid(UUID uuid, String currentUser) {
    repository.deletePantryItemByUuidAndCreatedBy(uuid, currentUser);
  }
}
