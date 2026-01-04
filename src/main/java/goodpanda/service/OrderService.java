package goodpanda.service;

import goodpanda.domain.Order;
import goodpanda.domain.OrderItem;
import goodpanda.domain.OrderStatus;
import goodpanda.util.ImageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LogManager.getLogger(OrderService.class);

    @Transactional
    public void saveOrUpdateOrder(Order order) {
        if (isNull(order.getUser()) || isNull(order.getRestaurant())) {
            throw new IllegalArgumentException("User and Restaurant must not be null.");
        }

        if (isNull(order.getId())) {
            List<OrderItem> orderItems = order.getOrderItems();

            if (orderItems.isEmpty()) {
                throw new IllegalArgumentException("Order List must not be null.");
            }

            entityManager.persist(order);

            for (OrderItem orderItem : orderItems) {
                orderItem.setOrder(order);
                entityManager.merge(orderItem);
            }

            return;
        }

        try {
            entityManager.merge(order);
            List<OrderItem> orderItems = order.getOrderItems();

            for (OrderItem orderItem : orderItems) {
                orderItem.setOrder(order);
                entityManager.merge(orderItem);
            }
        } catch (OptimisticLockException e) {
            throw e;
        }

    }

    @Transactional
    public Order getOrderById(Integer id) {
        String jpql = "SELECT o FROM Order o WHERE o.id = :id";

        try {
            return entityManager.createQuery(jpql, Order.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    public List<Order> getAllOrders() {
        String jpql = "SELECT o FROM Order o";

        return entityManager.createQuery(jpql, Order.class).getResultList();
    }

    public String getBase64ImageForOrder(Integer orderId) {
        Order order = getOrderById(orderId);

        if (order != null && order.getDeliveryProof() != null) {
            return ImageUtil.encodeImage(order.getDeliveryProof());
        }

        return null;
    }

    @Transactional
    public void deleteOrderById(int id) {
        Order order = entityManager.find(Order.class, id);

        if (nonNull(order)) {
            entityManager.remove(order);
        }
    }

    @Transactional
    public void deleteAllOrdersForFood(int foodId) {
        String jpql = "DELETE FROM Order o " +
                "WHERE o.food.id = :foodId";
        entityManager.createQuery(jpql).setParameter("foodId", foodId).executeUpdate();
    }

    public List<Order> getOrdersByFoodId(int foodId) {
        String jpql = "SELECT o FROM Order o " +
                "WHERE o.food.id = :foodId";

        return entityManager.createQuery(jpql, Order.class)
                .setParameter("foodId", foodId)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public List<Order> getPendingOrders() {
        String jpql = "SELECT o FROM Order o WHERE o.status = :status AND o.rider IS NULL";

        return entityManager.createQuery(jpql, Order.class)
                .setParameter("status", OrderStatus.ACCEPTED)
                .getResultList();
    }
}
