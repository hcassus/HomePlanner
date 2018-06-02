package hrp.auth.persistence.entities;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "authorities")
@Data
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String authority;

    public Authority(String username) {
        this.username = username;
        this.authority = "USER";
    }

    public Authority(){}
}
