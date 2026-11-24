package mz.co.maximo.cis.pages.auth;

import mz.co.maximo.cis.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private By emailField =
            By.id("email-id");

    private By passwordField =
            By.id("password-id");

    private By loginButton =
            By.cssSelector(".btn.btn-primary");

    public void login(String email, String password) {

        type(emailField, email);
        type(passwordField, password);
        click(loginButton);
    }
}
