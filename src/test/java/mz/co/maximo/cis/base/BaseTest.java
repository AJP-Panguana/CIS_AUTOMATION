package mz.co.maximo.cis.base;

import mz.co.maximo.cis.core.config.ConfigManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUpDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(ConfigManager.require("base.url"));
    }

    @AfterMethod
    public void tearDownDriver() {
        /*if (driver != null){
            driver.quit();
        }*/
    }
}
