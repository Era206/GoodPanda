package goodpanda.propertyEditor;

import goodpanda.domain.Location;
import goodpanda.service.LocationService;

import java.beans.PropertyEditorSupport;
import java.util.Objects;

/**
 * @author sanjidaera
 * @since 15/12/24
 */
public class LocationEditor extends PropertyEditorSupport {

    private final LocationService locationService;

    public LocationEditor(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public void setAsText(String text) {

        if (Objects.isNull(text) || text.isEmpty()) {
            setValue(null);

        } else {
            try {
                int locationId = Integer.parseInt(text);
                Location location = locationService.getLocationById(locationId);

                setValue(location);
            } catch (NumberFormatException e) {
                setValue(null);
            }
        }
    }
}
