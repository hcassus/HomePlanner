package hrp.auth.persistence.repositories;

import hrp.auth.persistence.entities.User;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    @Transactional
    @Modifying
    @Query("update User set enabled = true where username = :username")
    void enableUser(@Param("username") String username);
}
