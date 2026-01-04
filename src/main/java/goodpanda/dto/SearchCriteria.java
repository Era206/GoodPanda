package goodpanda.dto;

/**
 * @author sanjidaera
 * @since 19/12/24
 */
public class SearchCriteria {

    private Integer categoryId;
    private Integer locationId;
    private String restaurantName;

    public SearchCriteria(Integer categoryId, Integer locationId, String restaurantName) {
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.restaurantName = restaurantName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}

