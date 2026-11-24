package mz.co.maximo.cis.components;

import org.openqa.selenium.By;

/** Success / error notifications that appear and disappear by themselves. */
public class Toast extends BaseComponent {

    public Toast() {
        super(By.cssSelector(".toast, [role='alert'], [role='status']"));
    }

    public Toast(By root) {
        super(root);
    }

    /** Waits for the toast to appear and returns its text. */
    public String message() {
        return root().getText().trim();
    }

    public void waitUntilGone() {
        wait.invisible(root);
    }
}
