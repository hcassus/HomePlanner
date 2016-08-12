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

    public Task(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Task(){};

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}