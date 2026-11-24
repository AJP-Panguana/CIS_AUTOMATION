package mz.co.maximo.cis.base;

import mz.co.maximo.cis.core.config.ConfigManager;
import mz.co.maximo.cis.core.driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Opens a fresh browser before each test and closes it after.
 * Each thread has its own driver, so tests can run in parallel.
 */
public abstract class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUpDriver() {
        DriverManager.initDriver();
        driver().get(ConfigManager.require("base.url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownDriver() {
        // keep.browser.open=true leaves the browser open for debugging.
        if (!ConfigManager.getBoolean("keep.browser.open", false)) {
            DriverManager.quitDriver();
        }
    }

    protected WebDriver driver() {
        return DriverManager.getDriver();
    }
}
