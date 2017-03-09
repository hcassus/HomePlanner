package hrp.pantry.persistence;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import hrp.pantry.enums.MeasurementUnits;

@Entity
public class PantryItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @NotNull
  String name;
  Long quantity;

  @Enumerated(EnumType.STRING)
  MeasurementUnits unit;

  UUID uuid;

  public PantryItem(String name, Long quantity, MeasurementUnits unit){
    this.name = name;
    this.quantity = quantity;
    this.unit = unit;
    this.uuid = UUID.randomUUID();
  }

  public PantryItem(){
    this.uuid = UUID.randomUUID();
  }


  public UUID getUuid() {
    return uuid;
  }

  public MeasurementUnits getUnit() {
    return unit;
  }

  public Long getQuantity() {
    return quantity;
  }

  public String getName() {
    return name;
  }
}
