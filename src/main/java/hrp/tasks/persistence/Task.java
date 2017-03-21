package hrp.tasks.persistence;

import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;

@Entity
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
    this.uuid = UUID.randomUUID();
    this.createdAt = new Timestamp(System.currentTimeMillis());
    this.updatedAt = new Timestamp(System.currentTimeMillis());
  }

  @PreUpdate
  public void adjustUpdateDate(){
    this.updatedAt = new Timestamp(System.currentTimeMillis());
  }

  public String getDescription() {
    return description;
  }

  public int getStatus() {
    return status;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }
}