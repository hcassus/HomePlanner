package hrp.pantry.controller;

import hrp.HomePlannerApp;
import hrp.pantry.persistence.entities.PantryItem;
import hrp.pantry.usecases.AddPantryItemAndProductDataUsecase;
import hrp.pantry.usecases.DeleteCurrentUserPantryItemsUsecase;
import hrp.pantry.usecases.GetCurrentUserPantryItemsUsecase;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HomePlannerApp.class}, loader = SpringBootContextLoader.class)
@WebAppConfiguration
public class PantryItemControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @MockBean
  AddPantryItemAndProductDataUsecase addPantryItemAndProductUsecase;

  @MockBean
  GetCurrentUserPantryItemsUsecase getCurrentUserPantryItemsUsecase;

  @MockBean
  DeleteCurrentUserPantryItemsUsecase deleteCurrentUserItemByUuid;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void retrievePantryItemsTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/pantry/item"));
    verify(getCurrentUserPantryItemsUsecase, times(1)).execute();
  }

  @Test
  public void createPantryItemsTest() throws Exception {
    String item = new JSONObject().put("eanCode", "123").put("name","item").toString();
    mockMvc.perform(MockMvcRequestBuilders.post("/pantry/item").contentType(MediaType.APPLICATION_JSON).content(item));
    verify(addPantryItemAndProductUsecase, times(1)).execute(any(PantryItem.class));
  }

  @Test
  public void deletePantryItemsTest() throws Exception {
    UUID uuid = UUID.randomUUID();
    mockMvc.perform(MockMvcRequestBuilders.delete("/pantry/item/" + uuid));
    verify(deleteCurrentUserItemByUuid, times(1)).execute(eq(uuid));
  }

}
