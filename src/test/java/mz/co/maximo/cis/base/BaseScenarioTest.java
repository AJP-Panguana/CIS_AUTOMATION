package mz.co.maximo.cis.base;

import mz.co.maximo.cis.core.config.ConfigManager;
import mz.co.maximo.cis.core.session.ScenarioContext;
import mz.co.maximo.cis.core.session.SessionManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Base for multi-user scenarios (create -> approve -> finance -> continue).
 * Open one actor per role with sessions.open(...); every browser stays
 * open until the scenario ends, then all are closed together.
 */
public abstract class BaseScenarioTest {

    protected SessionManager sessions;
    protected ScenarioContext context;

    @BeforeMethod(alwaysRun = true)
    public void startScenario() {
        sessions = SessionManager.start();
        context = new ScenarioContext();
    }

    @AfterMethod(alwaysRun = true)
    public void endScenario() {
        if (ConfigManager.getBoolean("keep.browser.open", false)) {
            sessions.detach();
        } else {
            sessions.closeAll();
        }
    }
}
