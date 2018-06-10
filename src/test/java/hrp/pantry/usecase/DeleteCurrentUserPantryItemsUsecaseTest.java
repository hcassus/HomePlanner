package hrp.pantry.usecase;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hrp.auth.gateways.SessionDataGateway;
import hrp.pantry.gateways.PantryItemGateway;
import hrp.pantry.usecases.DeleteCurrentUserPantryItemsUsecase;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeleteCurrentUserPantryItemsUsecaseTest {

  @InjectMocks
  private DeleteCurrentUserPantryItemsUsecase deleteCurrentUserPantryItemsUsecase;

  @Mock
  private SessionDataGateway sessionDataGateway;

  @Mock
  private PantryItemGateway pantryItemGateway;

  @Test
  public void testDeleteCurrentUserPantryItem(){
    String loggedUser = "loggedUsername";
    UUID itemUuid = UUID.randomUUID();
    when(sessionDataGateway.getAuthenticatedUsername()).thenReturn(loggedUser);

    deleteCurrentUserPantryItemsUsecase.execute(itemUuid);

    verify(sessionDataGateway, times(1)).getAuthenticatedUsername();
    verify(pantryItemGateway, times(1)).deleteCurrentUserItemByUuid(itemUuid, loggedUser);
  }


}