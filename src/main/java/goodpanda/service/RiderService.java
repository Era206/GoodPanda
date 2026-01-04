package goodpanda.service;

import goodpanda.controller.UserController;
import goodpanda.domain.*;
import goodpanda.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.util.Objects.isNull;
import static goodpanda.domain.UserRole.RIDER;

/**
 * @author sanjidaera
 * @since 14/12/24
 */
@Service
public class RiderService {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RoleService roleService;

    @Transactional(readOnly = true)
    public List<Rider> getFreeRidersByLocation(int locationId) {
        String jpql = "SELECT r FROM Rider r WHERE r.status = :status AND r.location.id = :locationId";

        return entityManager.createQuery(jpql, Rider.class)
                .setParameter("status", RiderStatus.FREE)
                .setParameter("locationId", locationId)
                .getResultList();
    }

    @Transactional
    public void assignRidersToOrders() {
        List<Order> pendingOrders = orderService.getPendingOrders();

        for (Order order : pendingOrders) {
            int restaurantLocationId = order.getLocation().getId();
            List<Rider> freeRiders = getFreeRidersByLocation(restaurantLocationId);

            if (!freeRiders.isEmpty()) {
                Rider assignedRider = freeRiders.get(0);
                order.setRider(assignedRider.getUser());
                order.setStatus(OrderStatus.IN_PROGRESS);
                orderService.saveOrUpdateOrder(order);

                System.out.println("order: " + order.getId() + " is assigned to " + assignedRider.getId());
                assignedRider.setStatus(RiderStatus.ENGAGED);
                entityManager.merge(assignedRider);
                logger.info("rider assigned to order" + order.getId());
            }
        }
    }

    public Rider getRiderById(int id) {
        return entityManager.find(Rider.class, id);
    }

    public List<Rider> getFreeRiders() {
        String jpql = "SELECT r FROM Rider r WHERE r.riderStatus = :status";

        return entityManager.createQuery(jpql, Rider.class)
                .setParameter("status", RiderStatus.FREE)
                .getResultList();
    }

    public List<Rider> getAllRiders() {
        String jpql = "SELECT r FROM Rider r";

        return entityManager.createQuery(jpql, Rider.class).getResultList();
    }

    @Transactional
    public void saveOrUpdateRider(Rider rider) {
        if (isNull(rider.getId())) {
            User user = rider.getUser();
            user.addRole(roleService.getRoleByName(RIDER));
            entityManager.merge(user);
            entityManager.persist(rider);
            return;
        }

        entityManager.merge(rider);
    }

    @Transactional(readOnly = true)
    public List<Order> getAssignedOrdersForRider(Integer riderId) {
        System.out.println("riderId");
        Integer userId = getRiderById(riderId).getUser().getId();
        String jpql = "SELECT o FROM Order o WHERE o.rider.id = :riderId AND o.status = :status";

        return entityManager.createQuery(jpql, Order.class)
                .setParameter("riderId", userId)
                .setParameter("status", OrderStatus.IN_PROGRESS)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public List<Order> getCompletedOrdersForRider(Integer riderId) {
        Integer userId = getRiderById(riderId).getUser().getId();
        String jpql = "SELECT o FROM Order o WHERE o.rider.id = :riderId AND o.status = :status";

        return entityManager.createQuery(jpql, Order.class)
                .setParameter("riderId", userId)
                .setParameter("status", OrderStatus.DELIVERED)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public List<User> getRiderCandidates() {
        String jpql = "SELECT u FROM User u WHERE u.id NOT IN (SELECT r.user.id FROM Rider r)";

        return entityManager.createQuery(jpql, User.class)
                .getResultList();
    }

    public Rider getRiderByUserId(Integer userId) {
        try {
            return entityManager.createQuery(
                            "SELECT r FROM Rider r WHERE r.user.id = :userId", Rider.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }
}
