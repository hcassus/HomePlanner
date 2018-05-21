package auth.controller;

import hrp.HomePlannerApp;
import hrp.auth.persistence.entities.User;
import hrp.auth.usecase.CreateUserUsecase;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HomePlannerApp.class}, loader = SpringBootContextLoader.class)
@WebAppConfiguration
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    CreateUserUsecase createUserUsecase;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void createUserTest() throws Exception {
        String user = new JSONObject().put("username", "FakeUser").put("password","FakePassword").toString();
        mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON).content(user));
        verify(createUserUsecase, times(1)).execute(any(User.class));
    }

}