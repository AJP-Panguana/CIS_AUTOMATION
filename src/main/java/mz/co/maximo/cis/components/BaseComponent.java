package mz.co.maximo.cis.components;

import mz.co.maximo.cis.core.config.ConfigManager;
import mz.co.maximo.cis.core.driver.DriverManager;
import mz.co.maximo.cis.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

/**
 * A reusable piece of UI (table, modal, toast...) located by a root element.
 * All lookups are scoped to that root, so two tables on one page don't clash.
 */
public abstract class BaseComponent {

    protected final WebDriver driver;
    protected final WaitHelper wait;
    protected final By root;

    protected BaseComponent(By root) {
        this(DriverManager.getDriver(), root);
    }

    protected BaseComponent(WebDriver driver, By root) {
        this.driver = driver;
        this.root = root;
        this.wait = new WaitHelper(driver, Duration.ofSeconds(ConfigManager.getInt("timeout.seconds", 10)));
    }

    protected WebElement root() {
        return wait.visible(root);
    }

    protected WebElement find(By locator) {
        return root().findElement(locator);
    }

    protected List<WebElement> findAll(By locator) {
        return root().findElements(locator);
    }

    public boolean isVisible() {
        List<WebElement> found = driver.findElements(root);
        return !found.isEmpty() && found.get(0).isDisplayed();
    }

    protected static By buttonWithText(String text) {
        return By.xpath(".//button[normalize-space()=" + xpathLiteral(text) + "]");
    }

    /** Quotes text for XPath, even when it contains an apostrophe. */
    private static String xpathLiteral(String text) {
        if (!text.contains("'")) {
            return "'" + text + "'";
        }
        return "concat('" + text.replace("'", "', \"'\", '") + "')";
    }
}
