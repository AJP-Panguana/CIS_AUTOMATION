package mz.co.maximo.cis.core.session;

import java.util.HashMap;
import java.util.Map;

/**
 * Data handed from one actor to the next in a scenario
 * (client id, process number...). The browser does not need to remember
 * anything: the next actor opens the record by the id stored here.
 */
public class ScenarioContext {

    private final Map<String, Object> values = new HashMap<>();

    public void put(String key, Object value) {
        values.put(key, value);
    }

    public String get(String key) {
        return get(key, String.class);
    }

    public <T> T get(String key, Class<T> type) {
        Object value = values.get(key);
        if (value == null) {
            throw new IllegalStateException("Nothing stored for '" + key + "'. Stored: " + values.keySet());
        }
        return type.cast(value);
    }

    public boolean has(String key) {
        return values.containsKey(key);
    }
}
