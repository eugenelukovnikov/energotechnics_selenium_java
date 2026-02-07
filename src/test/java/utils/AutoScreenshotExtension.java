package utils;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import java.util.function.Function;
import org.openqa.selenium.WebDriver;

public class AutoScreenshotExtension implements TestExecutionExceptionHandler {

    private final Function<ExtensionContext, WebDriver> driverSupplier;

    public AutoScreenshotExtension(Function<ExtensionContext, WebDriver> driverSupplier) {
        this.driverSupplier = driverSupplier;
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        WebDriver driver = driverSupplier.apply(context);
        if (driver != null) {
            String name = "Упал тест: " + context.getDisplayName() + " (" + throwable.getClass().getSimpleName() + ")";
            ScreenshotUtils.attachPageScreenshot(name);
        }
        throw throwable;
    }
}
