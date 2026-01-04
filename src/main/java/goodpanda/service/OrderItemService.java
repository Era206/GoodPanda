package goodpanda.service;

import goodpanda.domain.OrderItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author sanjidaera
 * @since 26/11/24
 */
@Service
public class OrderItemService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveOrUpdateOrderItem(OrderItem orderItem) {
        if (isNull(orderItem.getId())) {
            entityManager.merge(orderItem);

            return;
        }

        try {
            entityManager.merge(orderItem);
        } catch (OptimisticLockException e) {
            throw e;
        }

    }

    public OrderItem getOrderItemById(int id) {
        return entityManager.find(OrderItem.class, id);
    }

    public List<OrderItem> getAllOrderItems() {
        String jpql = "SELECT o FROM OrderItem o";

        return entityManager.createQuery(jpql, OrderItem.class).getResultList();
    }

    @Transactional
    public void deleteOrderItemById(int id) {
        OrderItem orderItem = entityManager.find(OrderItem.class, id);

        if (nonNull(orderItem)) {
            entityManager.remove(orderItem);
        }
    }

    @Transactional
    public void deleteAllOrderItemsForFood(int foodId) {
        String jpql = "DELETE FROM OrderItem o WHERE o.food.id = :foodId";
        entityManager.createQuery(jpql).setParameter("foodId", foodId).executeUpdate();
    }

    public List<OrderItem> getOrderItemsByFoodId(int foodId) {
        String jpql = "SELECT o FROM OrderItem o WHERE o.food.id = :foodId";

        return entityManager.createQuery(jpql, OrderItem.class)
                .setParameter("foodId", foodId)
                .getResultList();
    }
}
