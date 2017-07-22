package hrp.pantry.persistence.entities;

import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import hrp.pantry.enums.PackagingUnit;

@Entity
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


  public UUID getUuid() {
    return uuid;
  }

  public PackagingUnit getUnit() {
    return unit;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public String getName() {
    return name;
  }

  public Timestamp getCreatedAt(){
    return createdAt;
  }

  public Timestamp getUpdatedAt(){
    return updatedAt;
  }

  public String getEanCode() {
    return eanCode;
  }

  public Timestamp getExpiresAt(){
    return expiresAt;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
