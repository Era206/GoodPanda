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
@Table(name = "restaurant")
public class Restaurant extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurantIdSeq")
    @SequenceGenerator(name = "restaurantIdSeq", sequenceName = "restaurant_id_seq", allocationSize = 1)
    private Integer id;

    @NotNull
    @Length(max = 30, min = 3)
    private String name;

    @Length(max = 3000)
    private String description;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.MERGE)
    private List<User> restaurantAdmins;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "restaurant_location",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private List<Location> locations;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Food> foods;


    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Order> orders;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "restaurant_category",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    public Restaurant() {
        locations = new ArrayList<>();
        foods = new ArrayList<>();
        categories = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public List<Location> getLocations() {
        return locations;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<User> getRestaurantAdmins() {
        return restaurantAdmins;
    }

    public void setRestaurantAdmins(List<User> restaurantAdmins) {
        this.restaurantAdmins = restaurantAdmins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Restaurant)) {
            return false;
        }

        Restaurant restaurant = (Restaurant) o;

        return Objects.equals(id, restaurant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}