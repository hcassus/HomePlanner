package hrp.pantry.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;
import javax.transaction.Transactional;
import hrp.pantry.persistence.entities.PantryItem;

public interface PantryItemRepository extends CrudRepository<PantryItem, Long> {

  @Transactional
  void deletePantryItemByUuid(UUID uuid);
}
