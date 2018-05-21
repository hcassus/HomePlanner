package hrp.auth.persistence.entities;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
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

    public String getUsername() {
        return username;
    }

    public String getAuthority() {
        return authority;
    }
}
