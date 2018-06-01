package hrp.auth.usecase;

import hrp.auth.gateways.UserGateway;
import hrp.auth.persistence.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserUsecase {

    private final UserGateway gateway;
    private final PasswordEncoder encoder;

    public User execute(User user){
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        return gateway.createUser(user);
    }
}
