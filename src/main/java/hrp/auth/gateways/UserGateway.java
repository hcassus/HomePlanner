package hrp.auth.gateways;

import hrp.auth.persistence.entities.Authority;
import hrp.auth.persistence.entities.User;
import hrp.auth.persistence.repositories.AuthorityRepository;
import hrp.auth.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserGateway {

    AuthorityRepository authorityRepo;
    UserRepository userRepo;

    @Autowired
    public UserGateway(AuthorityRepository authorityRepo, UserRepository userRepo){
        this.authorityRepo = authorityRepo;
        this.userRepo = userRepo;
    }

    public User createUser(User user){
        authorityRepo.save(new Authority(user.getUsername()));
        return userRepo.save(user);
    }

}
