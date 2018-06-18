package hrp.auth.gateways;

import hrp.auth.persistence.entities.Authority;
import hrp.auth.persistence.entities.User;
import hrp.auth.persistence.repositories.AuthorityRepository;
import hrp.auth.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserGateway {

    private final AuthorityRepository authorityRepo;
    private final UserRepository userRepo;

    public User createUser(User user){
        authorityRepo.save(new Authority(user.getUsername()));
        return userRepo.save(user);
    }

  public void enableUser(String username) {
      userRepo.enableUser(username);
  }
}
