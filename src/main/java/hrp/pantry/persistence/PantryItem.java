package hrp.pantry.persistence;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import hrp.pantry.enums.MeasurementUnits;

@Entity
public class PantryItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String name;
  private Long quantity;

  @Enumerated(EnumType.STRING)
  private MeasurementUnits unit;

  @Column(nullable = false)
  private UUID uuid;

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
