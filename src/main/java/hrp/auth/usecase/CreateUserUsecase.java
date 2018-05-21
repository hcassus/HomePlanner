package hrp.auth.usecase;

import hrp.auth.persistence.entities.User;
import hrp.auth.gateways.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateUserUsecase {

    private UserGateway gateway;
    private PasswordEncoder encoder;

    @Autowired
    public CreateUserUsecase(UserGateway gateway){
        this.gateway = gateway;
        this.encoder = new BCryptPasswordEncoder();
    }

    public User execute(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEnabled(true);
        return gateway.createUser(user);
    }
}
