package hrp.tasks.persistence;

import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {

  Task findOneByUuidAndCreatedBy(UUID uuid, String createdBy);

  Iterable<Task> findByCreatedBy(String username);

  @Transactional
  void deleteTaskByUuidAndCreatedBy(UUID uuid, String createdBy);

}