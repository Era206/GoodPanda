package goodpanda.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author sanjidaera
 * @since 26/11/24
 */
@Entity
@Table(name = "role")
public class Role extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleIdSeq")
    @SequenceGenerator(name = "roleIdSeq", sequenceName = "role_id_seq", allocationSize = 1)
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserRole getName() {
        return name;
    }

    public void setName(String name) {
        this.name = UserRole.valueOf(name);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Role)) {
            return false;
        }

        Role role = (Role) o;

        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
