package hrp.tasks.persistence;

import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {

  @Transactional
  void deleteTaskByUuid(UUID uuid);

  Task findOneByUuid(UUID uuid);
}