package hrp.pantry.persistence.entities;

import hrp.pantry.enums.PackagingUnit;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.Data;

@Entity
@Data
public class PantryItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String eanCode;

  @Column(nullable = false)
  private String name;

  private Integer quantity;

  @Enumerated(EnumType.STRING)
  private PackagingUnit unit;

  @Column
  private Timestamp expiresAt;

  @Column(nullable = false)
  private UUID uuid;

  @Column
  private Timestamp createdAt;

  @Column
  private Timestamp updatedAt;

  @PreUpdate
  public void adjustUpdateDate(){
    this.updatedAt = new Timestamp(System.currentTimeMillis());
  }

  public PantryItem(String eanCode, String name, Integer quantity, PackagingUnit unit, Timestamp expiresAt){
    this.eanCode = eanCode;
    this.name = name;
    this.quantity = quantity;
    this.unit = unit;
    this.expiresAt = expiresAt;
    this.uuid = UUID.randomUUID();
    this.createdAt = new Timestamp(System.currentTimeMillis());
    this.updatedAt = new Timestamp(System.currentTimeMillis());
  }

  public PantryItem(){
    this.uuid = UUID.randomUUID();
    this.createdAt = new Timestamp(System.currentTimeMillis());
    this.updatedAt = new Timestamp(System.currentTimeMillis());
  }
}
