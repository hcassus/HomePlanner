package taskList;

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

    public Task(String content) {
        this.content = content;
        this.status = 0;
    }

    public Task(){};

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}