package goodpanda.service;

import goodpanda.domain.Order;
import goodpanda.domain.OrderStatus;
import goodpanda.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.util.Objects.nonNull;
import static goodpanda.domain.UserRole.USER;

/**
 * @author sanjidaera
 * @since 26/11/24
 */
@Service
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RoleService roleService;

    @Transactional
    public User modifyUser(User user) {

        if (user.isNew()) {
            user.addRole(roleService.getRoleByName(USER));
            entityManager.persist(user);
            entityManager.flush();

            return user;
        }

        try {
            return entityManager.merge(user);
        } catch (OptimisticLockException e) {
            throw e;
        }
    }

    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    public Integer getCurrentSequenceValue(String sequenceName) {

        return ((Number) entityManager
                .createNativeQuery("SELECT " + sequenceName + ".CURRVAL FROM DUAL")
                .getSingleResult()).intValue();
    }

    public List<User> getAllUsers() {
        String jpql = "SELECT u FROM User u";

        return entityManager.createQuery(jpql, User.class)
                .getResultList();
    }

    public List<User> getAllRestaurantAdminCandidates() {
        String jpql = "SELECT u FROM User u WHERE u.restaurant is NULL";

        return entityManager.createQuery(jpql, User.class)
                .getResultList();
    }

    @Transactional
    public void deleteUserById(int id) {
        User user = entityManager.getReference(User.class, id);

        if (nonNull(user)) {
            entityManager.remove(user);
        }
    }

    public User getUserByEmail(String email) {
        String jpql = "SELECT u FROM User u WHERE u.email = :email";

        try {
            return entityManager.createQuery(jpql, User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    public User getUserByNid(String nid) {
        String jpql = "SELECT u FROM User u WHERE u.nid = :nid";

        try {
            return entityManager.createQuery(jpql, User.class)
                    .setParameter("nid", nid)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    @Transactional
    public List<Order> getCompletedOrdersByUser(User user) {
        String jpql = "SELECT o FROM Order o WHERE o.user = :user AND o.status = :status";

        return entityManager.createQuery(jpql, Order.class)
                .setParameter("user", user)
                .setParameter("status", OrderStatus.DELIVERED)
                .getResultList();
    }

    @Transactional
    public List<Order> getRunningOrdersByUser(User user) {
        String jpql = "SELECT o FROM Order o WHERE o.user = :user " +
                "AND o.status <> :status1 AND o.status <> :status2";

        return entityManager.createQuery(jpql, Order.class)
                .setParameter("user", user)
                .setParameter("status1", OrderStatus.DELIVERED)
                .setParameter("status2", OrderStatus.CANCELLED)
                .getResultList();
    }

    public User validateUser(String email, String password) {
        List<User> users = entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();

        if (users.isEmpty()) {
            return null;
        }

        for (User user : users) {
            if (password.equals(user.getPassword())) {
                return user;
            }
        }

        return null;
    }

    public boolean doesUserHaveAdminRole(Integer userId) {
        String jpql = "SELECT COUNT(r) " +
                "FROM User u " +
                "JOIN u.roles r " +
                "WHERE u.id = :userId AND r.id = :roleId";

        Long count = entityManager.createQuery(jpql, Long.class)
                                  .setParameter("userId", userId)
                                  .setParameter("roleId", 1)
                                  .getSingleResult();

        return count > 0;
    }
}