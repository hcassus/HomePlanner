package pantry.controller;

import hrp.HomePlannerApp;
import hrp.pantry.gateways.PantryItemGateway;
import hrp.pantry.persistence.entities.PantryItem;
import hrp.pantry.usecases.AddPantryItemAndProductDataUsecase;
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

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HomePlannerApp.class}, loader = SpringBootContextLoader.class)
@WebAppConfiguration
public class PantryItemControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @MockBean
  PantryItemGateway gateway;

  @MockBean
  AddPantryItemAndProductDataUsecase usecase;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void retrievePantryItemsTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/pantry/item"));
    verify(gateway, times(1)).retrieveAllPantryItems();
  }

  @Test
  public void createPantryItemsTest() throws Exception {
    String item = new JSONObject().put("eanCode", "123").put("name","item").toString();
    mockMvc.perform(MockMvcRequestBuilders.post("/pantry/item").contentType(MediaType.APPLICATION_JSON).content(item));
    verify(usecase, times(1)).execute(any(PantryItem.class));
  }

  @Test
  public void deletePantryItemsTest() throws Exception {
    UUID uuid = UUID.randomUUID();
    mockMvc.perform(MockMvcRequestBuilders.delete("/pantry/item/" + uuid));
    verify(gateway, times(1)).deleteItemByUuid(uuid);
  }

}