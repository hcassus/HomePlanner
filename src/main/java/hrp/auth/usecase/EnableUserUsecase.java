package hrp.auth.usecase;

import hrp.auth.gateways.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnableUserUsecase {

  private final UserGateway userGateway;

  public void execute(String username){
    userGateway.enableUser(username);
  }
}
