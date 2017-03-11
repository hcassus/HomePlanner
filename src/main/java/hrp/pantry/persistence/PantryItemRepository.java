package hrp.pantry.persistence;

import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

public interface PantryItemRepository extends CrudRepository<PantryItem, Long> {

  @Transactional
  void deletePantryItemByUuid(UUID uuid);
}
