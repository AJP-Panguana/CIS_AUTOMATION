package mz.co.maximo.cis.core.driver;

import mz.co.maximo.cis.core.config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

/**
 * Creates WebDriver instances, locally or on a Selenium Grid.
 * Set grid.url (e.g. http://localhost:4444) to run on the Grid;
 * leave it empty to run on this machine. Selenium Manager downloads
 * local browser drivers automatically.
 */
public final class DriverFactory {

    private static final Logger LOG = LogManager.getLogger(DriverFactory.class);
    private static final String WINDOW_SIZE = "--window-size=1920,1080";

    private DriverFactory() {
    }

    /** Uses the browser and headless settings from config. */
    public static WebDriver create() {
        return create(BrowserType.from(ConfigManager.get("browser", "chrome")));
    }

    /** Uses the headless setting from config. */
    public static WebDriver create(BrowserType browser) {
        return create(browser, ConfigManager.getBoolean("headless", false));
    }

    public static WebDriver create(BrowserType browser, boolean headless) {
        Capabilities options = options(browser, headless);
        String gridUrl = ConfigManager.get("grid.url", "");

        WebDriver driver;
        if (gridUrl.isBlank()) {
            LOG.info("Starting local {} (headless={})", browser, headless);
            driver = switch (browser) {
                case CHROME -> new ChromeDriver((ChromeOptions) options);
                case EDGE -> new EdgeDriver((EdgeOptions) options);
                case FIREFOX -> new FirefoxDriver((FirefoxOptions) options);
            };
        } else {
            LOG.info("Starting {} on Grid {} (headless={})", browser, gridUrl, headless);
            try {
                driver = new RemoteWebDriver(URI.create(gridUrl).toURL(), options);
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Invalid grid.url: " + gridUrl, e);
            }
        }

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(ConfigManager.getInt("page.load.timeout.seconds", 60)));
        if (!headless) {
            driver.manage().window().maximize();
        }
        return driver;
    }

    private static Capabilities options(BrowserType browser, boolean headless) {
        return switch (browser) {
            case CHROME -> {
                ChromeOptions o = new ChromeOptions();
                if (headless) {
                    o.addArguments("--headless=new", WINDOW_SIZE);
                }
                yield o;
            }
            case EDGE -> {
                EdgeOptions o = new EdgeOptions();
                if (headless) {
                    o.addArguments("--headless=new", WINDOW_SIZE);
                }
                yield o;
            }
            case FIREFOX -> {
                FirefoxOptions o = new FirefoxOptions();
                if (headless) {
                    o.addArguments("-headless", "--width=1920", "--height=1080");
                }
                yield o;
            }
        };
    }
}
