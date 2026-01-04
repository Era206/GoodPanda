package goodpanda.propertyEditor;

import goodpanda.domain.Location;
import goodpanda.service.LocationService;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author sanjidaera
 * @since 1/12/24
 */
public class LocationListEditor extends PropertyEditorSupport {

    private final LocationService locationService;

    public LocationListEditor(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public void setAsText(String text) {

        if (isNull(text) || text.isEmpty()) {
            setValue(null);
            return;
        }

        String[] locationIds = text.split(",");
        List<Location> locations = new ArrayList<>();

        for (String locationIdStr : locationIds) {
            try {
                int locationId = Integer.parseInt(locationIdStr);
                Location location = locationService.getLocationById(locationId);

                if (nonNull(location)) {
                    locations.add(location);
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid course ID: " + locationIdStr);
            }
        }

        setValue(locations);
    }
}
