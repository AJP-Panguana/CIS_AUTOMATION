package mz.co.maximo.cis.core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Creates WebDriver instances. Selenium Manager downloads the matching
 * browser driver automatically, so no chromedriver.exe is needed.
 */
public final class DriverFactory {

    private static final String WINDOW_SIZE = "--window-size=1920,1080";

    private DriverFactory() {
    }

    public static WebDriver create(BrowserType browser, boolean headless) {
        return switch (browser) {
            case CHROME -> {
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless=new", WINDOW_SIZE);
                }
                yield new ChromeDriver(options);
            }
            case EDGE -> {
                EdgeOptions options = new EdgeOptions();
                if (headless) {
                    options.addArguments("--headless=new", WINDOW_SIZE);
                }
                yield new EdgeDriver(options);
            }
            case FIREFOX -> {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("-headless", "--width=1920", "--height=1080");
                }
                yield new FirefoxDriver(options);
            }
        };
    }
}
