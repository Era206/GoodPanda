package goodpanda.service;

import goodpanda.domain.Role;
import goodpanda.domain.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author sanjidaera
 * @since 26/11/24
 */
@Service
public class RoleService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveOrUpdateRole(Role role) {
        if (isNull(role.getId())) {
            entityManager.persist(role);

            return;
        }

        entityManager.merge(role);
    }

    public Role getRoleById(int id) {
        return entityManager.find(Role.class, id);
    }

    public List<Role> getAllRoles() {
        String jpql = "SELECT r FROM Role r";

        return entityManager.createQuery(jpql, Role.class)
                .getResultList();
    }

    public Role getRoleByName(UserRole name) {
        String jpql = "SELECT r FROM Role r WHERE r.name = :name";

        try {
            return entityManager.createQuery(jpql, Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void deleteRoleById(int id) {
        Role role = entityManager.find(Role.class, id);

        if (nonNull(role)) {
            entityManager.remove(role);
        }
    }
}
