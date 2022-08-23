package hrp.auth.controller;

import hrp.HomePlannerApp;
import hrp.auth.dto.SignupUserDTO;
import hrp.auth.usecase.SignupUserUsecase;
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
public class SignupControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @MockBean
  private SignupUserUsecase createUserUsecase;

  @Captor
  private ArgumentCaptor<SignupUserDTO> captor;

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
        password).put("passwordConfirmation",
        password).toString();
    mockMvc.perform(
        MockMvcRequestBuilders.post("/signup").contentType(MediaType.APPLICATION_JSON)
            .content(user));
    verify(createUserUsecase, times(1)).execute(captor.capture());

    SignupUserDTO deserializedUser = captor.getValue();
    assertThat(deserializedUser.getUsername(), is(username));
    assertThat(deserializedUser.getEmail(), is(email));
    assertThat(deserializedUser.getPassword(), is(password));
    assertThat(deserializedUser.getPasswordConfirmation(), is(password));
  }

}
