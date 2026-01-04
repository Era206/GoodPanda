package goodpanda.domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author sanjidaera
 * @since 25/11/24
 */
@Entity
@Table(name = "rider")
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "riderIdSeq")
    @SequenceGenerator(name = "riderIdSeq", sequenceName = "rider_id_seq", allocationSize = 1)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @Enumerated(EnumType.STRING)
    @Column(name = "rider_status", nullable = false)
    private RiderStatus status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public RiderStatus getStatus() {
        return status;
    }

    public void setStatus(RiderStatus riderStatus) {
        this.status = riderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Rider)) {
            return false;
        }

        Rider rider = (Rider) o;

        return Objects.equals(id, rider.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
