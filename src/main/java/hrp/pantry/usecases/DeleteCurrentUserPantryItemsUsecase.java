package hrp.pantry.usecases;

import hrp.auth.gateways.SessionDataGateway;
import hrp.pantry.gateways.PantryItemGateway;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCurrentUserPantryItemsUsecase {

  private final PantryItemGateway pantryItemGateway;
  private final SessionDataGateway sessionDataGateway;

  public void execute(UUID uuid){
    String currentUser = sessionDataGateway.getAuthenticatedUsername();
    pantryItemGateway.deleteCurrentUserItemByUuid(uuid, currentUser);
  }

}
