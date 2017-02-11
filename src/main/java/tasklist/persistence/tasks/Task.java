package tasklist.persistence.tasks;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String content;
    private int status;
    private UUID uuid;

    public Task(String content) {
        this.content = content;
        this.status = 0;
        this.uuid = UUID.randomUUID();
    }

    public Task(){}

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getStatus(){
        return status;
    }

    public UUID getUuid(){
        return uuid;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}