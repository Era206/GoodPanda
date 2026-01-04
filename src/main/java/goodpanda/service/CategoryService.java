package goodpanda.service;

import goodpanda.domain.Category;
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
public class CategoryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveOrUpdateCategory(Category category) {

        if (isNull(category.getId())) {
            entityManager.persist(category);

            return;
        }

        entityManager.merge(category);
    }

    public Category getCategoryById(int id) {
        return entityManager.find(Category.class, id);
    }

    public List<Category> getAllCategories() {
        String jpql = "SELECT c FROM Category c";

        return entityManager.createQuery(jpql, Category.class).getResultList();
    }

    @Transactional
    public void deleteCategoryById(int id) {
        Category category = entityManager.find(Category.class, id);

        if (nonNull(category)) {
            entityManager.remove(category);
        }
    }
}
