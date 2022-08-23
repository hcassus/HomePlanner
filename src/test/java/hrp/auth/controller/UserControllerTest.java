package hrp.auth.controller;

import hrp.HomePlannerApp;
import hrp.auth.persistence.entities.User;
import hrp.auth.usecase.CreateUserUsecase;
import hrp.auth.usecase.EnableUserUsecase;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HomePlannerApp.class}, loader = SpringBootContextLoader.class)
@WebAppConfiguration
public class UserControllerTest {

  private static final String VALID_USERNAME = System.getenv("VALID_USERNAME");

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @MockBean
  private CreateUserUsecase createUserUsecase;

  @MockBean
  private EnableUserUsecase enableUserUsecase;


  @Captor
  private ArgumentCaptor<User> captor;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void createUserTest() throws Exception {
    String username = "FakeUser";
    String password = "FakePassword";
    String email = "fakemail@gmail.com";

    String user = new JSONObject().put("username", username).put("email", email).put("password",
        password).toString();
    mockMvc.perform(
        MockMvcRequestBuilders.post("/admin/user").contentType(MediaType.APPLICATION_JSON)
            .content(user));
    verify(createUserUsecase, times(1)).execute(captor.capture());

    User deserializedUser = captor.getValue();
    assertThat(deserializedUser.getUsername(), is(username));
    assertThat(deserializedUser.getEmail(), is(email));
    assertThat(deserializedUser.getPassword(), is(password));
  }

  @Test
  public void enableUserTest() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders.post("/admin/user/enable").param("username", VALID_USERNAME).contentType(MediaType.APPLICATION_JSON));
    verify(enableUserUsecase, times(1)).execute(VALID_USERNAME);
    }

}
