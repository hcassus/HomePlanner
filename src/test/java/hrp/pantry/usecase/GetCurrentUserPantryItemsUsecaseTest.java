package hrp.pantry.usecase;

import hrp.auth.gateways.SessionDataGateway;
import hrp.pantry.gateways.PantryItemGateway;
import hrp.pantry.usecases.GetCurrentUserPantryItemsUsecase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCurrentUserPantryItemsUsecaseTest {

  @InjectMocks
  private GetCurrentUserPantryItemsUsecase getCurrentUserPantryItemsUsecase;

  @Mock
  private SessionDataGateway sessionDataGateway;

  @Mock
  private PantryItemGateway pantryItemGateway;

  @Test
  public void testGetCurrentUser(){
    String loggedUser = "loggedUsername";
    when(sessionDataGateway.getAuthenticatedUsername()).thenReturn(loggedUser);

    getCurrentUserPantryItemsUsecase.execute();

    verify(sessionDataGateway, times(1)).getAuthenticatedUsername();
    verify(pantryItemGateway, times(1)).retrieveAllCurrentUserPantryItems(loggedUser);
  }


}
