package hrp.auth.services;

import hrp.auth.persistence.entities.User;
import hrp.auth.gateways.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    UserGateway gateway;
    PasswordEncoder encoder;

    @Autowired
    public UserService(UserGateway gateway){
    this.gateway = gateway;
    this.encoder = new BCryptPasswordEncoder();
    }

    public User createUser(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return gateway.createUser(user);
    }
}
