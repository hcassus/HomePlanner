package hrp.auth.persistence.repositories;

import hrp.auth.persistence.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
