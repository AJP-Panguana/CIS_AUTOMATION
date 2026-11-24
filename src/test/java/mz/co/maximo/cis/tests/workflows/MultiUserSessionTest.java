package mz.co.maximo.cis.tests.workflows;

import mz.co.maximo.cis.base.BaseScenarioTest;
import mz.co.maximo.cis.core.driver.BrowserType;
import mz.co.maximo.cis.core.session.Actor;
import mz.co.maximo.cis.pages.auth.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Proves the multi-user setup: two users, two different browsers,
 * both logged in at the same time and both still usable at the end.
 * (sessions.open already fails the test if a login does not succeed.)
 *
 * Template for real workflows, e.g. client approval:
 *
 *   Actor comercial  = sessions.open("comercial",  BrowserType.CHROME,  "comercial");
 *   Actor supervisor = sessions.open("supervisor", BrowserType.EDGE,    "supervisor");
 *   Actor financeiro = sessions.open("financeiro", BrowserType.FIREFOX, "financeiro");
 *
 *   context.put("clienteId", comercial.step("Cria cliente").on(ClientesPage.class).criar(dados));
 *   supervisor.step("Aprova cliente").on(AprovacoesPage.class).aprovar(context.get("clienteId"));
 *   financeiro.step("Analisa e aprova").on(FinanceiroPage.class).aprovar(context.get("clienteId"));
 *   comercial.step("Continua o processo").on(ClientesPage.class).abrir(context.get("clienteId"));
 */
public class MultiUserSessionTest extends BaseScenarioTest {

    @Test(groups = "e2e", description = "Two users stay logged in at the same time in different browsers")
    public void twoUsersInDifferentBrowsers() {
        Actor first = sessions.open("utilizador-1", BrowserType.CHROME, "comercial");
        Actor second = sessions.open("utilizador-2", BrowserType.EDGE, "supervisor");

        // The first browser is still open and logged in after the second user acted.
        first.step("Continua onde parou");
        first.driver().navigate().refresh();
        Assert.assertTrue(first.on(LoginPage.class).isLoggedIn(), "utilizador-1 lost the session");
        Assert.assertTrue(second.on(LoginPage.class).isLoggedIn(), "utilizador-2 lost the session");
    }
}
