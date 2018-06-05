package hrp.auth.gateways;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class SessionDataGateway {

  public String getAuthenticatedUsername(){
    return ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
  }

}
