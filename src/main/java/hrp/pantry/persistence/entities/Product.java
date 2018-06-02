package hrp.pantry.persistence.entities;

import hrp.pantry.enums.PackagingUnit;

import javax.persistence.*;
import java.sql.Timestamp;
import lombok.Data;

@Entity
@Data
public class Product {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String eanCode;

  private String name;

  private PackagingUnit unit;

  private Long count;

  private Timestamp createdAt;

  private Timestamp updatedAt;

  @PreUpdate
  public void adjustUpdateTime(){
    this.updatedAt = new Timestamp(System.currentTimeMillis());
  }

  public Product(String eanCode, String name, PackagingUnit unit){
    this.eanCode = eanCode;
    this.name = name;
    this.unit = unit;
    this.createdAt = new Timestamp(System.currentTimeMillis());
    this.updatedAt = new Timestamp(System.currentTimeMillis());
    this.count = 1L;
  }

  public Product(){
    this.count = 1L;
    this.createdAt = new Timestamp(System.currentTimeMillis());
    this.updatedAt = new Timestamp(System.currentTimeMillis());
  }
}
