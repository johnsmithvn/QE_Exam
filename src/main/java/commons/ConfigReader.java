package commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * [REFACTOR] MỚI - đọc cấu hình test (URL, tài khoản) từ src/test/resources/config.properties.
 *
 * Tại sao: URL nằm cứng trong GlobalConstants và "admin"/"admin123" nằm cứng trong LoginPageSteps,
 * nên đổi môi trường là phải sửa code. Giờ sửa file config, hoặc ghi đè lúc chạy bằng -Dkey=value
 * (ví dụ: mvn test -Dui.url=https://staging.example.com).
 */
public final class ConfigReader {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPS = load();

    private ConfigReader() {
    }

    private static Properties load() {
        Properties props = new Properties();
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read " + CONFIG_FILE, e);
        }
        return props;
    }

    /** Thứ tự ưu tiên: -Dkey=value, rồi tới config.properties. Thiếu thì báo lỗi rõ ràng. */
    public static String get(String key) {
        String value = get(key, null);
        if (value == null) {
            throw new IllegalStateException(
                    "Missing config '" + key + "': add it to " + CONFIG_FILE + " or pass -D" + key + "=...");
        }
        return value;
    }

    public static String get(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            value = PROPS.getProperty(key);
        }
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return value.trim();
    }
}
