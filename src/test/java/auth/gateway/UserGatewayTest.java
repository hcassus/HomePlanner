package auth.gateway;

import commons.testcases.PersistencyTestCase;
import hrp.auth.gateways.UserGateway;
import hrp.auth.persistence.entities.Authority;
import hrp.auth.persistence.entities.User;
import hrp.auth.persistence.repositories.AuthorityRepository;
import hrp.auth.persistence.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UserGatewayTest extends PersistencyTestCase {

    @Autowired
    UserGateway userGateway;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setup(){
        userRepository.deleteAll();
        authorityRepository.deleteAll();
    }

    @Test
    public void testCreateUser() {
        String username = "FakeUsername";
        User user = new User(username, "FakePassword");

        userGateway.createUser(user);
        Authority authority = authorityRepository.findByUsername(username);
        User persisterUser = userRepository.findByUsername(username);

        Assert.assertThat(authority.getUsername(), is(username));
        Assert.assertThat(authority.getAuthority(), is("USER"));
        Assert.assertThat(persisterUser.getUsername(), is(username));
        Assert.assertThat(persisterUser.getPassword(), notNullValue());
    }
}
