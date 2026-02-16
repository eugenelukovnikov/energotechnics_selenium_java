package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.BeforeParameterizedClassInvocation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.AutoScreenshotExtension;
import utils.ScreenshotUtils;

import java.util.function.Function;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;

public class BaseTest {

    protected static WebDriver driver;

    static Function<ExtensionContext, WebDriver> driverSupplier =
            context -> ((BaseTest) context.getRequiredTestInstance()).driver;

    @RegisterExtension
    static AutoScreenshotExtension screenshotOnFail = new AutoScreenshotExtension(driverSupplier);

    @BeforeEach
    void createDriver() {

        chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--start-maximized");
       // options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-cache");
        //options.addArguments("--disable-application-cache");
        //options.addArguments("--disk-cache-size=0");
        //options.addArguments("--network-cache-size=0");

        String headless = System.getProperty("headless", "false");
        if ("true".equals(headless)) {
            options.addArguments("--headless=new");
        }

        driver = new ChromeDriver(options);

        ScreenshotUtils.setDriver(driver);

    }

    @AfterEach
    void quitDriver() {

      if (driver != null) {
        driver.quit();
        driver = null;
      }
    }
}
