package hrp.auth.gateway;

import hrp.auth.gateways.UserGateway;
import hrp.auth.persistence.entities.Authority;
import hrp.auth.persistence.entities.User;
import hrp.auth.persistence.repositories.AuthorityRepository;
import hrp.auth.persistence.repositories.UserRepository;
import hrp.commons.testcases.GatewayTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UserGatewayTest extends GatewayTestCase {

  @Autowired
  UserGateway userGateway;

  @Autowired
  AuthorityRepository authorityRepository;

  @Autowired
  UserRepository userRepository;

  @BeforeEach
  public void setup() {
    userRepository.deleteAll();
    authorityRepository.deleteAll();
  }

  @Test
  public void testCreateUser() {
    String username = "FakeUsername";
    String email = "fakemail@gmail.com";
    User user = new User(username, "FakePassword", email);

    userGateway.createUser(user);
    Authority authority = authorityRepository.findByUsername(username);
    User persisterUser = userRepository.findByUsername(username);

    assertThat(authority.getUsername(), is(username));
    assertThat(authority.getAuthority(), is("USER"));
    assertThat(persisterUser.getUsername(), is(username));
    assertThat(persisterUser.getEmail(), is(email));
    assertThat(persisterUser.getPassword(), notNullValue());
  }

  @Test
  public void testEnableUser() {
    String username = "FakeUsername";
    String email = "fakemail@gmail.com";
    User user = new User(username, "FakePassword", email);
    user.setEnabled(false);
    userGateway.createUser(user);

    userGateway.enableUser(username);

    Authority authority = authorityRepository.findByUsername(username);
    User persisterUser = userRepository.findByUsername(username);

    assertThat(authority.getUsername(), is(username));
    assertThat(authority.getAuthority(), is("USER"));
    assertThat(persisterUser.getUsername(), is(username));
    assertThat(persisterUser.getEmail(), is(email));
    assertThat(persisterUser.getPassword(), notNullValue());
    assertThat(persisterUser.getEnabled(), is(true));
  }

}
