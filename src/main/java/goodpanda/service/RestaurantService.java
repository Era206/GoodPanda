package goodpanda.service;

import goodpanda.domain.*;
import goodpanda.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static goodpanda.domain.UserRole.RESTAURANT_ADMIN;

/**
 * @author sanjidaera
 * @since 26/11/24
 */
@Service
public class RestaurantService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private RoleService roleService;

    @Transactional
    public void saveOrUpdateRestaurant(Restaurant restaurant) {
        if (isNull(restaurant.getId())) {
            for (Food food : restaurant.getFoods()) {
                for (Category category : food.getCategories()) {
                    restaurant.addCategory(category);
                }
            }

            entityManager.persist(restaurant);

            for (User user : restaurant.getRestaurantAdmins()) {
                user.setRestaurant(restaurant);
                user.addRole(roleService.getRoleByName(RESTAURANT_ADMIN));
                entityManager.merge(user);
            }

            return;
        }

        try {
            for (Food food : restaurant.getFoods()) {
                for (Category category : food.getCategories()) {
                    restaurant.addCategory(category);
                }
            }
            entityManager.merge(restaurant);

            for (User user : restaurant.getRestaurantAdmins()) {
                user.setRestaurant(restaurant);
                user.addRole(roleService.getRoleByName(RESTAURANT_ADMIN));
                entityManager.merge(user);
            }
        } catch (OptimisticLockException e) {
            throw e;
        }

    }

    public Restaurant getRestaurantById(int id) {
        return entityManager.find(Restaurant.class, id);
    }

    public List<Restaurant> getAllRestaurants() {
        String jpql = "SELECT r FROM Restaurant r";

        return entityManager.createQuery(jpql, Restaurant.class).getResultList();
    }

    @Transactional
    public void deleteRestaurantById(int id) {
        Restaurant restaurant = entityManager.getReference(Restaurant.class, id);

        if (nonNull(restaurant)) {
            for (User admin : restaurant.getRestaurantAdmins()) {
                User user = entityManager.find(User.class, admin.getId());
                user.setRestaurant(null);
            }
            entityManager.remove(restaurant);
        }
    }

    public List<Restaurant> getRestaurantsByFood(int foodId) {
        String jpql = "SELECT r FROM Restaurant r " +
                "JOIN RestaurantFood rf ON r.id = rf.restaurantId " +
                "WHERE rf.foodId = :foodId";

        return entityManager.createQuery(jpql, Restaurant.class)
                .setParameter("foodId", foodId)
                .getResultList();
    }

    public List<Restaurant> getRestaurantsByLocation(int locationId) {
        String jpql = "SELECT r FROM Restaurant r " +
                "JOIN RestaurantLocation rl ON r.id = rl.restaurantId " +
                "WHERE rl.locationId = :locationId";

        return entityManager.createQuery(jpql, Restaurant.class)
                .setParameter("locationId", locationId)
                .getResultList();
    }

    public List<Restaurant> getRestaurantsByCategory(int categoryId) {
        String jpql = "SELECT r FROM Restaurant r " +
                "JOIN RestaurantCategory rc ON r.id = rc.restaurantId " +
                "WHERE rc.categoryId = :categoryId";

        return entityManager.createQuery(jpql, Restaurant.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Transactional
    public List<Order> getCompletedOrdersByRestaurant(Restaurant restaurant) {
        String jpql = "SELECT o FROM Order o WHERE o.restaurant = :restaurant AND o.status = :status";

        return entityManager.createQuery(jpql, Order.class)
                .setParameter("restaurant", restaurant)
                .setParameter("status", OrderStatus.DELIVERED)
                .getResultList();
    }

    @Transactional
    public List<Order> getRunningOrdersByRestaurant(Restaurant restaurant) {
        String jpql = "SELECT o FROM Order o WHERE o.restaurant = :restaurant " +
                "AND o.status <> :status1 AND o.status <> :status2";
        return entityManager.createQuery(jpql, Order.class)
                .setParameter("restaurant", restaurant)
                .setParameter("status1", OrderStatus.DELIVERED)
                .setParameter("status2", OrderStatus.CANCELLED)
                .getResultList();
    }

    @Transactional
    public List<Order> getPendingOrdersByRestaurant(Restaurant restaurant) {
        String jpql = "SELECT o FROM Order o WHERE o.restaurant = :restaurant AND o.status = :status";

        return entityManager.createQuery(jpql, Order.class)
                .setParameter("restaurant", restaurant)
                .setParameter("status", OrderStatus.PLACED)
                .getResultList();
    }

    @Transactional
    public List<Restaurant> searchRestaurants(Integer categoryId, Integer locationId, String restaurantName) {
        StringBuilder query = new StringBuilder("SELECT r FROM Restaurant r WHERE 1=1");

        if (nonNull(categoryId)) {
            query.append(" AND :categoryId MEMBER OF r.categories");
        }

        if (nonNull(locationId)) {
            query.append(" AND :locationId MEMBER OF r.locations");
        }

        if (nonNull(restaurantName) && !restaurantName.isEmpty()) {
            query.append(" AND LOWER(r.name) LIKE LOWER(:restaurantName)");
        }

        TypedQuery<Restaurant> typedQuery = entityManager.createQuery(query.toString(), Restaurant.class);

        if (nonNull(categoryId)) {
            typedQuery.setParameter("categoryId", categoryService.getCategoryById(categoryId));
        }

        if (nonNull(locationId)) {
            typedQuery.setParameter("locationId", locationService.getLocationById(locationId));
        }

        if (nonNull(restaurantName) && !restaurantName.isEmpty()) {
            typedQuery.setParameter("restaurantName", "%" + restaurantName + "%");
        }

        return typedQuery.getResultList();
    }
}