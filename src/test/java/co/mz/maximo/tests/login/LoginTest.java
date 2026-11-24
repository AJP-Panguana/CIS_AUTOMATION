package co.mz.maximo.tests.login;

import base.BaseTest;
import com.maximo.automation.pages.common.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test
    public void LoginTest(){
        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.login("afonso.panguana@maximo.co.mz",
                "Ap123456");

        String titulo;
        titulo = driver.getTitle();


        Assert.assertEquals(titulo,  " Max Sure");

    }
}
