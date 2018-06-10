package hrp.auth.gateway;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import hrp.auth.gateways.UserGateway;
import hrp.auth.persistence.entities.Authority;
import hrp.auth.persistence.entities.User;
import hrp.auth.persistence.repositories.AuthorityRepository;
import hrp.auth.persistence.repositories.UserRepository;
import hrp.commons.testcases.GatewayTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserGatewayTest extends GatewayTestCase {

  @Autowired
  UserGateway userGateway;

  @Autowired
  AuthorityRepository authorityRepository;

  @Autowired
  UserRepository userRepository;

  @Before
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

    Assert.assertThat(authority.getUsername(), is(username));
    Assert.assertThat(authority.getAuthority(), is("USER"));
    Assert.assertThat(persisterUser.getUsername(), is(username));
    Assert.assertThat(persisterUser.getEmail(), is(email));
    Assert.assertThat(persisterUser.getPassword(), notNullValue());
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

    Assert.assertThat(authority.getUsername(), is(username));
    Assert.assertThat(authority.getAuthority(), is("USER"));
    Assert.assertThat(persisterUser.getUsername(), is(username));
    Assert.assertThat(persisterUser.getEmail(), is(email));
    Assert.assertThat(persisterUser.getPassword(), notNullValue());
    Assert.assertThat(persisterUser.getEnabled(), is(true));
  }

}
