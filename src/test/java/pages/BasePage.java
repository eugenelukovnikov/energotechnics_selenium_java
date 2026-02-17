package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    private final By h1 = By.id("pagetitle");

    public BasePage(WebDriver driver) {

        this.driver = driver;
    }

    public BasePage open() {

        return this;
    }

    @Step("Фиксируем H1")
    public String getH1Text() {

        return driver.findElement(h1).getText().trim();
    }

    @Step("Фиксируем Title")
    public String getPageTitle() {

        return driver.getTitle();
    }

    @Step("Получаем цену как число")
    public int parsePrice(String priceString) {
        // допустим "66 900 ₽" мы приводим к 66900 заменяя все символы кроме цифр на ""
        return Integer.parseInt(priceString.replaceAll("\\D", ""));
    }

    protected void stableSleep(int seconds) {

        try {
            Thread.sleep(Duration.ofSeconds(seconds));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected boolean isWsChatWidgetVisible() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ws-chat")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Удаляем виджет WS-Chat")
    protected void removeWsChatWidget() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        if(isWsChatWidgetVisible()) {
            js.executeScript("document.querySelector('div.ws-chat').remove()");
        }
    }

    @Step("Обновляем страницу")
    public void refreshPage() {
        driver.navigate().refresh();
    }
}