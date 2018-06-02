package hrp.tasks.persistence;

import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import lombok.Data;

@Entity
@Data
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String description;
  private int status;
  private UUID uuid;
  private Timestamp updatedAt;
  private Timestamp createdAt;

  public Task() {
    this.status = 0;
    this.uuid = UUID.randomUUID();
    this.createdAt = new Timestamp(System.currentTimeMillis());
    this.updatedAt = new Timestamp(System.currentTimeMillis());
  }

  public Task(String description){
    this.status = 0;
    this.description = description;
    this.uuid = UUID.randomUUID();
    this.createdAt = new Timestamp(System.currentTimeMillis());
    this.updatedAt = new Timestamp(System.currentTimeMillis());
  }

  @PreUpdate
  public void adjustUpdateDate(){
    this.updatedAt = new Timestamp(System.currentTimeMillis());
  }
}