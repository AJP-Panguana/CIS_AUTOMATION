package mz.co.maximo.cis.pages.auth;

import mz.co.maximo.cis.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    /** Path of the sign-in page; after a successful login the URL leaves it. */
    private static final String SIGN_IN_PATH = "/auth/sign-in";

    private final By emailField = By.id("email-id");
    private final By passwordField = By.id("password-id");
    private final By loginButton = By.cssSelector(".btn.btn-primary");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /** Fills the form and submits. Does not check the result (use for negative tests). */
    public void login(String email, String password) {
        type(emailField, email);
        type(passwordField, password);
        click(loginButton);
    }

    /** Logs in and fails clearly if the app stays on the sign-in page. */
    public void loginAndWait(String email, String password) {
        login(email, password);
        if (!isLoggedIn()) {
            throw new IllegalStateException("Login failed for " + email
                    + ": still on the sign-in page (" + getCurrentUrl() + ")");
        }
    }

    /**
     * True once the URL leaves the sign-in page. The page title is the same
     * before and after login, so it cannot be used to check this.
     */
    public boolean isLoggedIn() {
        try {
            wait.urlNotContains(SIGN_IN_PATH);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
