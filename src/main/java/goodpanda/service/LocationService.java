package goodpanda.service;

import goodpanda.domain.Location;
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
public class LocationService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void modifyLocation(Location location) {
        if (isNull(location.getId())) {
            entityManager.persist(location);

            return;
        }

        try {
            entityManager.merge(location);
        } catch (OptimisticLockException e) {
            throw e;
        }
    }

    public Location getLocationById(int id) {
        return entityManager.find(Location.class, id);
    }

    public List<Location> getAllLocations() {
        String jpql = "SELECT l FROM Location l";

        return entityManager.createQuery(jpql, Location.class).getResultList();
    }

    @Transactional
    public void deleteLocationById(int id) {
        Location location = entityManager.getReference(Location.class, id);

        if (nonNull(location)) {
            entityManager.remove(location);
        }
    }

    public List<Location> findLocationsByPostalCode(String postalCode) {
        String jpql = "SELECT l FROM Location l WHERE l.postalCode = :postalCode";

        return entityManager.createQuery(jpql, Location.class)
                .setParameter("postalCode", postalCode)
                .getResultList();
    }
}
