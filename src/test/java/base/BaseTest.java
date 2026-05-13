package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

protected WebDriver driver;
private static final String BASE_URL =
        "http://192.168.2.58:5174/auth/sign-in";

@BeforeMethod
public void SetupDriver()
{
    driver = new ChromeDriver();

    driver.manage().window().maximize();

    driver.get(BASE_URL);
}

@AfterMethod
public void TearDownDriver()
{
    /*if (driver != null){
        driver.quit();
    }*/

}



}
