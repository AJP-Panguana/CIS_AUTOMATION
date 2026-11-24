package mz.co.maximo.cis.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import mz.co.maximo.cis.core.driver.DriverManager;
import mz.co.maximo.cis.core.report.ExtentManager;
import mz.co.maximo.cis.core.session.Actor;
import mz.co.maximo.cis.core.session.SessionManager;
import mz.co.maximo.cis.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Feeds the HTML report and takes a screenshot when a test fails.
 * Registered in every suite XML.
 */
public class TestListener implements ITestListener {

    private static final Logger LOG = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        String name = result.getTestClass().getRealClass().getSimpleName() + " > " + result.getMethod().getMethodName();
        LOG.info("START  {}", name);
        ExtentTest test = ExtentManager.start(name, result.getMethod().getDescription());
        test.assignCategory(result.getMethod().getGroups());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOG.info("PASS   {}", result.getMethod().getMethodName());
        ExtentManager.current().pass("Passed");
        ExtentManager.end();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOG.error("FAIL   {}", result.getMethod().getMethodName(), result.getThrowable());
        ExtentTest test = ExtentManager.current();
        test.fail(result.getThrowable());

        String method = result.getMethod().getMethodName();
        SessionManager sessions = SessionManager.current();
        if (sessions != null && !sessions.all().isEmpty()) {
            // Multi-user scenario: one screenshot per actor, so you see every screen at the moment of failure.
            for (Actor actor : sessions.all()) {
                attachScreenshot(test, actor.driver(), method + "_" + actor.name(),
                        "Screenshot - " + actor.name() + " (" + actor.browser() + ")");
            }
        } else if (DriverManager.hasDriver()) {
            attachScreenshot(test, DriverManager.getDriver(), method, "Screenshot at failure");
        }
        ExtentManager.end();
    }

    private static void attachScreenshot(ExtentTest test, WebDriver driver, String fileName, String title) {
        try {
            ScreenshotUtils.save(driver, fileName);
            test.fail(title, MediaEntityBuilder.createScreenCaptureFromBase64String(
                    ScreenshotUtils.base64(driver)).build());
        } catch (RuntimeException e) {
            LOG.warn("Could not capture screenshot '{}': {}", fileName, e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOG.warn("SKIP   {}", result.getMethod().getMethodName());
        ExtentTest test = ExtentManager.current();
        if (test == null) {
            test = ExtentManager.start(result.getMethod().getMethodName(), null);
        }
        test.skip(result.getThrowable() != null ? result.getThrowable().getMessage() : "Skipped");
        ExtentManager.end();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flush();
        LOG.info("Report: {}", ExtentManager.REPORT_PATH);
    }
}
