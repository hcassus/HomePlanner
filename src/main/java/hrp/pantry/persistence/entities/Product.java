package hrp.pantry.persistence.entities;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import hrp.pantry.enums.PackagingUnit;

@Entity
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
  }


  public String getEanCode() {
    return eanCode;
  }

  public String getName() {
    return name;
  }

  public PackagingUnit getUnit() {
    return unit;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }
}
