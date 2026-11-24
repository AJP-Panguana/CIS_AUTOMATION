package mz.co.maximo.cis.components;

import org.openqa.selenium.By;

/** Dialog / modal window. Defaults to Bootstrap markup; pass another root if needed. */
public class Modal extends BaseComponent {

    private static final By TITLE = By.cssSelector(".modal-title");
    private static final By CLOSE = By.cssSelector(".btn-close, [data-bs-dismiss='modal']");

    public Modal() {
        super(By.cssSelector(".modal.show"));
    }

    public Modal(By root) {
        super(root);
    }

    public String title() {
        return find(TITLE).getText().trim();
    }

    public void clickButton(String text) {
        find(buttonWithText(text)).click();
    }

    public void close() {
        find(CLOSE).click();
        waitUntilClosed();
    }

    public void waitUntilClosed() {
        wait.invisible(root);
    }
}
