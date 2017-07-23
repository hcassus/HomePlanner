package hrp.auth.persistence.repositories;

import hrp.auth.persistence.entities.Authority;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
}
