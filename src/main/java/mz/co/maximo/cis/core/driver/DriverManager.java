package mz.co.maximo.cis.core.driver;

import mz.co.maximo.cis.core.config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

/**
 * Holds one WebDriver per thread, so tests can run in parallel safely.
 */
public final class DriverManager {

    private static final Logger LOG = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void initDriver() {
        BrowserType browser = BrowserType.from(ConfigManager.get("browser", "chrome"));
        boolean headless = ConfigManager.getBoolean("headless", false);
        LOG.info("Starting {} (headless={})", browser, headless);

        WebDriver driver = DriverFactory.create(browser, headless);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigManager.getInt("page.load.timeout.seconds", 60)));
        if (!headless) {
            driver.manage().window().maximize();
        }
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("No WebDriver for this thread. Call DriverManager.initDriver() first.");
        }
        return driver;
    }

    public static boolean hasDriver() {
        return DRIVER.get() != null;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            return;
        }
        try {
            driver.quit();
        } finally {
            DRIVER.remove();
        }
    }
}
