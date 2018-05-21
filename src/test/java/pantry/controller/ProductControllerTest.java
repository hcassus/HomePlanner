package pantry.controller;

import hrp.HomePlannerApp;
import hrp.pantry.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HomePlannerApp.class }, loader = SpringBootContextLoader.class)
@WebAppConfiguration
public class ProductControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @MockBean
  ProductService service;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void productDataRequestTest() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/product/123"));
    verify(service, times(1)).retrieveItemDataByEan("123");
  }

}
