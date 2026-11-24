package mz.co.maximo.cis.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {

    private static final Path DIR = Path.of("target", "screenshots");
    private static final DateTimeFormatter STAMP = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtils() {
    }

    /** Saves a PNG under target/screenshots and returns its path. */
    public static Path save(WebDriver driver, String name) {
        byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        String safeName = name.replaceAll("[^a-zA-Z0-9._-]", "_");
        Path file = DIR.resolve(safeName + "_" + LocalDateTime.now().format(STAMP) + ".png");
        try {
            Files.createDirectories(DIR);
            Files.write(file, png);
        } catch (IOException e) {
            throw new IllegalStateException("Could not save screenshot " + file, e);
        }
        return file;
    }

    /** Base64 PNG, for embedding directly in the HTML report. */
    public static String base64(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }
}
