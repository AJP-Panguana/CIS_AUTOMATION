package mz.co.maximo.cis.pages.base;

import mz.co.maximo.cis.core.config.ConfigManager;
import mz.co.maximo.cis.core.driver.DriverManager;
import mz.co.maximo.cis.utils.WaitHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

/**
 * Parent of every Page Object. Pages describe WHAT the user does;
 * this class handles HOW (waits, logging, JS fallbacks).
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WaitHelper wait;
    protected final Logger log = LogManager.getLogger(getClass());

    protected BasePage() {
        this(DriverManager.getDriver());
    }

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver, Duration.ofSeconds(ConfigManager.getInt("timeout.seconds", 10)));
    }

    // ---------- actions ----------

    protected void click(By locator) {
        log.debug("Click {}", locator);
        wait.clickable(locator).click();
    }

    protected void jsClick(By locator) {
        log.debug("JS click {}", locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", wait.present(locator));
    }

    protected void type(By locator, String text) {
        log.debug("Type into {}", locator);
        WebElement element = wait.visible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void selectByText(By locator, String visibleText) {
        log.debug("Select '{}' in {}", visibleText, locator);
        new Select(wait.visible(locator)).selectByVisibleText(visibleText);
    }

    protected void hover(By locator) {
        new Actions(driver).moveToElement(wait.visible(locator)).perform();
    }

    protected void scrollTo(By locator) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", wait.present(locator));
    }

    // ---------- reads ----------

    protected String getText(By locator) {
        return wait.visible(locator).getText().trim();
    }

    protected String getAttribute(By locator, String attribute) {
        return wait.present(locator).getDomAttribute(attribute);
    }

    /** Checks immediately, without waiting. */
    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    // ---------- page state ----------

    public String getTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected void waitForUrlContains(String fragment) {
        wait.urlContains(fragment);
    }
}
