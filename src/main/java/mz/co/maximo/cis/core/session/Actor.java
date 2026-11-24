package mz.co.maximo.cis.core.session;

import com.aventstack.extentreports.ExtentTest;
import mz.co.maximo.cis.core.config.ConfigManager;
import mz.co.maximo.cis.core.driver.BrowserType;
import mz.co.maximo.cis.core.report.ExtentManager;
import mz.co.maximo.cis.models.User;
import mz.co.maximo.cis.pages.auth.LoginPage;
import mz.co.maximo.cis.pages.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;

/**
 * One person in a scenario (e.g. "comercial", "financeiro"),
 * with their own browser that stays open for the whole scenario.
 */
public class Actor {

    private static final Logger LOG = LogManager.getLogger(Actor.class);

    private final String name;
    private final BrowserType browser;
    private final User user;
    private final WebDriver driver;

    Actor(String name, BrowserType browser, User user, WebDriver driver) {
        this.name = name;
        this.browser = browser;
        this.user = user;
        this.driver = driver;
    }

    /** Returns a Page Object bound to this actor's browser. */
    public <T extends BasePage> T on(Class<T> pageClass) {
        try {
            return pageClass.getDeclaredConstructor(WebDriver.class).newInstance(driver);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(pageClass.getSimpleName()
                    + " needs a public constructor (WebDriver driver)", e);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Could not create " + pageClass.getSimpleName(), e);
        }
    }

    /** Writes a named step to the log and the HTML report: "[financeiro] Aprova cliente". */
    public Actor step(String description) {
        String line = "[" + name + "] " + description;
        LOG.info(line);
        ExtentTest test = ExtentManager.current();
        if (test != null) {
            test.info(line);
        }
        return this;
    }

    /** Opens a path of the CIS app, e.g. "/clientes/123". */
    public Actor open(String path) {
        String base = ConfigManager.require("app.url");
        driver.get(base.replaceAll("/+$", "") + "/" + path.replaceAll("^/+", ""));
        return this;
    }

    Actor login() {
        step("Login as " + user);
        driver.get(ConfigManager.require("base.url"));
        on(LoginPage.class).loginAndWait(user.email(), user.password());
        return this;
    }

    public String name() {
        return name;
    }

    public BrowserType browser() {
        return browser;
    }

    public User user() {
        return user;
    }

    public WebDriver driver() {
        return driver;
    }
}
