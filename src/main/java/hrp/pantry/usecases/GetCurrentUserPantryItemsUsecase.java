package hrp.pantry.usecases;

import hrp.auth.gateways.SessionDataGateway;
import hrp.pantry.gateways.PantryItemGateway;
import hrp.pantry.persistence.entities.PantryItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCurrentUserPantryItemsUsecase {

  private final PantryItemGateway pantryItemGateway;
  private final SessionDataGateway sessionDataGateway;

  public Iterable<PantryItem> execute() {
    String currentUser = sessionDataGateway.getAuthenticatedUsername();
    return pantryItemGateway.retrieveAllCurrentUserPantryItems(currentUser);
  }
}
