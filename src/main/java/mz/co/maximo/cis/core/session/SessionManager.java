package mz.co.maximo.cis.core.session;

import mz.co.maximo.cis.core.driver.BrowserType;
import mz.co.maximo.cis.core.driver.DriverFactory;
import mz.co.maximo.cis.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Browsers of one multi-user scenario. Every actor keeps their browser
 * (and login) open from the first step to the last; closeAll() runs once
 * at the end of the scenario, even when it fails.
 */
public class SessionManager {

    private static final Logger LOG = LogManager.getLogger(SessionManager.class);
    private static final ThreadLocal<SessionManager> CURRENT = new ThreadLocal<>();

    private final Map<String, Actor> actors = new LinkedHashMap<>();

    /** Session manager of the scenario running on this thread, or null. */
    public static SessionManager current() {
        return CURRENT.get();
    }

    public static SessionManager start() {
        SessionManager sessions = new SessionManager();
        CURRENT.set(sessions);
        return sessions;
    }

    /** Opens a browser for the actor and logs in as the user of that role. */
    public Actor open(String name, BrowserType browser, String role) {
        return open(name, browser, User.forRole(role));
    }

    public Actor open(String name, BrowserType browser, User user) {
        if (actors.containsKey(name)) {
            throw new IllegalStateException("Actor '" + name + "' is already open");
        }
        WebDriver driver = DriverFactory.create(browser);
        Actor actor = new Actor(name, browser, user, driver);
        actors.put(name, actor); // registered before login, so it is closed even if login fails
        return actor.login();
    }

    public Actor get(String name) {
        Actor actor = actors.get(name);
        if (actor == null) {
            throw new IllegalArgumentException("No actor '" + name + "'. Open: " + actors.keySet());
        }
        return actor;
    }

    public Collection<Actor> all() {
        return Collections.unmodifiableCollection(actors.values());
    }

    /** Quits every browser; one failing quit does not stop the others. */
    public void closeAll() {
        List<RuntimeException> errors = new ArrayList<>();
        for (Actor actor : actors.values()) {
            try {
                actor.driver().quit();
            } catch (RuntimeException e) {
                LOG.warn("Could not close browser of '{}': {}", actor.name(), e.getMessage());
                errors.add(e);
            }
        }
        actors.clear();
        CURRENT.remove();
        if (!errors.isEmpty()) {
            LOG.warn("{} browser(s) did not close cleanly", errors.size());
        }
    }

    /** Forgets the actors without closing the browsers (keep.browser.open=true). */
    public void detach() {
        actors.clear();
        CURRENT.remove();
    }
}
