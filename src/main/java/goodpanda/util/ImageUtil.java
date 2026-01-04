package goodpanda.util;

import java.util.Base64;

/**
 * @author sanjidaera
 * @since 29/12/24
 */
public class ImageUtil {

    public static String encodeImage(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
