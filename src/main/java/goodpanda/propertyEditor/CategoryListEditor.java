package goodpanda.propertyEditor;

import goodpanda.domain.Category;
import goodpanda.service.CategoryService;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author sanjidaera
 * @since 2/12/24
 */
public class CategoryListEditor extends PropertyEditorSupport {

    private final CategoryService categoryService;

    public CategoryListEditor(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void setAsText(String text) {

        if (isNull(text) || text.isEmpty()) {
            setValue(null);
            return;
        }

        String[] categoryIds = text.split(",");
        List<Category> categories = new ArrayList<>();

        for (String categoryIdStr : categoryIds) {
            try {
                int categoryId = Integer.parseInt(categoryIdStr);
                Category category = categoryService.getCategoryById(categoryId);

                if (nonNull(category)) {
                    categories.add(category);
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid course ID: " + categoryIdStr);
            }
        }

        setValue(categories);
    }
}
