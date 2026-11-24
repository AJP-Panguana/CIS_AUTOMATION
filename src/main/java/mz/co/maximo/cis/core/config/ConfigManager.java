package mz.co.maximo.cis.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads configuration with this precedence (highest first):
 * 1. JVM system property      -Dbase.url=...
 * 2. Environment variable     CIS_BASE_URL=...
 * 3. config/environments/{env}.properties   (env = -Denv, default "qa")
 * 4. config/config.properties
 * 5. config/local.properties  (git-ignored, for secrets on your machine)
 */
public final class ConfigManager {

    private static final Properties PROPS = load();

    private ConfigManager() {
    }

    public static String env() {
        return System.getProperty("env", System.getenv().getOrDefault("CIS_ENV", "qa"));
    }

    public static String get(String key) {
        String value = System.getProperty(key);
        if (value == null) {
            value = System.getenv("CIS_" + key.toUpperCase().replace('.', '_'));
        }
        if (value == null) {
            value = PROPS.getProperty(key);
        }
        return value;
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    public static String require(String key) {
        String value = get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing config '" + key + "'. Set -D" + key
                    + ", env var CIS_" + key.toUpperCase().replace('.', '_')
                    + " or add it to config/local.properties.");
        }
        return value;
    }

    private static Properties load() {
        Properties props = new Properties();
        // Loaded lowest precedence first, so later files override earlier ones.
        loadInto(props, "config/local.properties");
        loadInto(props, "config/config.properties");
        loadInto(props, "config/environments/" + env() + ".properties");
        return props;
    }

    private static void loadInto(Properties target, String resource) {
        try (InputStream in = ConfigManager.class.getClassLoader().getResourceAsStream(resource)) {
            if (in != null) {
                target.load(in);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not read " + resource, e);
        }
    }
}
