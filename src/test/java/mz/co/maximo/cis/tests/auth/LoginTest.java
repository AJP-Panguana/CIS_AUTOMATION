package mz.co.maximo.cis.tests.auth;

import mz.co.maximo.cis.base.BaseTest;
import mz.co.maximo.cis.core.config.ConfigManager;
import mz.co.maximo.cis.pages.auth.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(groups = {"smoke", "regression"})
    public void loginWithValidCredentials() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(ConfigManager.require("user.email"),
                ConfigManager.require("user.password"));

        Assert.assertEquals(driver.getTitle(), " Max Sure");
    }
}
