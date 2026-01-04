package goodpanda.service;

import goodpanda.domain.Food;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author sanjidaera
 * @since 26/11/24
 */
@Service
public class FoodService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveOrUpdateFood(Food food) {
        if (isNull(food.getId())) {
            entityManager.persist(food);

            return;
        }

        Food managedFood = entityManager.find(Food.class, food.getId());

        if (isNull(managedFood)) {
            throw new EntityNotFoundException("Food with ID " + food.getId() + " not found.");
        }

        managedFood.setName(food.getName());
        managedFood.setPrice(food.getPrice());
        managedFood.setCategories(food.getCategories());

        entityManager.merge(managedFood);
    }

    public Food getFoodById(int id) {
        return entityManager.find(Food.class, id);
    }

    public List<Food> getAllFoods() {
        String jpql = "SELECT f FROM Food f";

        return entityManager.createQuery(jpql, Food.class).getResultList();
    }

    @Transactional
    public void deleteFoodById(int id) {
        Food food = entityManager.find(Food.class, id);

        if (nonNull(food)) {
            entityManager.remove(food);
        }
    }

    public List<Food> getFoodsByRestaurant(int restaurantId) {
        String jpql = "SELECT f FROM Food f " +
                "JOIN RestaurantFood rf ON f.id = rf.foodId " +
                "WHERE rf.restaurantId = :restaurantId";

        return entityManager.createQuery(jpql, Food.class)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }

    public List<Food> getFoodsByCategoryId(int categoryId) {
        String jpql = "SELECT f FROM Food f WHERE f.category.id = :categoryId";

        return entityManager.createQuery(jpql, Food.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }
}
