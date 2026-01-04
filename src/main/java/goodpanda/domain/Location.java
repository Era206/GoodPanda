package goodpanda.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author sanjidaera
 * @since 25/11/24
 */
@Entity
@Table(name = "location_tag")
public class Location extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationIdSeq")
    @SequenceGenerator(name = "locationIdSeq", sequenceName = "location_id_seq", allocationSize = 1)
    private Integer id;

    @NotNull
    @Length(min = 4, max = 30)
    private String name;

    @ManyToMany(mappedBy = "locations")
    private List<Restaurant> restaurants;

    public Location() {
        restaurants = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Location)) {
            return false;
        }

        Location location = (Location) o;

        return Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
