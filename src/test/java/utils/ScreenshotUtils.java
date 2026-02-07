package utils;

import java.io.ByteArrayInputStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Allure;

public class ScreenshotUtils {
    private static WebDriver driver;

    public static void setDriver(WebDriver driverInstance) {
        driver = driverInstance;
    }

    public static void attachPageScreenshot(String name) {
        if (driver == null) return;

        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
            System.out.println("✅ Скриншот '" + name + "' добавлен");
        } catch (Exception e) {
            System.out.println("⚠️ Скриншот '" + name + "' не создан: " + e.getClass().getSimpleName());
        }
    }
}

