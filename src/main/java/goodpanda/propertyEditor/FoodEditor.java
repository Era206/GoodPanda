package goodpanda.propertyEditor;

import goodpanda.domain.Food;
import goodpanda.service.FoodService;

import java.beans.PropertyEditorSupport;
import java.util.Objects;

/**
 * @author sanjidaera
 * @since 9/12/24
 */
public class FoodEditor extends PropertyEditorSupport {

    private final FoodService foodService;

    public FoodEditor(FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public void setAsText(String text) {

        if (Objects.isNull(text) || text.isEmpty()) {
            setValue(null);
        } else {
            try {
                int foodId = Integer.parseInt(text);
                Food food = foodService.getFoodById(foodId);

                setValue(food);
            } catch (NumberFormatException e) {
                setValue(null);
            }
        }
    }
}
