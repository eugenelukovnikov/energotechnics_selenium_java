package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasketPage extends BasePage {

    private static final String URL = "https://energotechnics.ru/basket/";
    private static final By productNames = By.xpath("//span[@data-entity='basket-item-name']");
    private static final By productPrices = By.className("basket-item-price-current-text");
    private static final By basketSum = By.xpath("//div[@data-entity='basket-total-price']");
    private static final By basketEmptyMsg = By.className("bx-sbb-empty-cart-text");
    private static final By removeProductFromBasketButtons = By.xpath(
            "//span[@data-entity='basket-item-delete']//i");
    private static final By cleanBasket = By.cssSelector(".delete_all");
    private static final By restoreRemovedProductInBasketButtons = By.xpath(
            "//a[@data-entity='basket-item-restore-button']");
    private static final By removedItemContainer = By.cssSelector(
            ".basket-items-list-item-removed-container");

    public BasketPage(WebDriver driver) {

        super(driver);
    }

    @Step("Открыть страницу корзины")
    @Override
    public BasketPage open() {

        driver.get(URL);
        removeWsChatWidget();
        return this;
    }

    @Step("Получить итоговую сумму корзины")
    public int getBasketSum() {

        String basketSumString = driver.findElement(basketSum).getText();
        return parsePrice(basketSumString);
    }

    @Step("Получить цену товара в корзине")
    public int getProductPriceInBasket(int index) {

        String productPriceInBasketString = driver.findElements(productPrices).get(index).getText();
        return parsePrice(productPriceInBasketString);
    }

    @Step("Получить название товара в корзине")
    public String getProductNameInBasket(int index) {

        return driver.findElements(productNames).get(index).getText();
    }

    @Step("Получить сообщение пустой корзины")
    public String getEmptyBasketText() {

        return driver.findElement(basketEmptyMsg).getText();
    }

    @Step("Удаление конкретного товара из корзины")
    public void clickRemoveProductFromBasketButton(int index) {

        driver.findElements(removeProductFromBasketButtons).get(index).click();
        stableSleep(5);
    }

    @Step("Полная очистка корзины кнопкой 'Очистить корзину'")
    public void cleanBasket() {

        driver.findElement(cleanBasket).click();
    }

    @Step("Восстановление конкретного удаленного товара в корзине")
    public void clickRestoreRemovedProductInBasketButton(int index) {

        driver.findElements(restoreRemovedProductInBasketButtons).get(index).click();
    }

    @Step("Проверяем, что отображается сообщение об удалении товара из корзины")
    public boolean hasRemovedItemContainer() {

        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(removedItemContainer));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Ждем, что корзина станет пустой")
    public boolean isBasketShouldBeEmptyNow() {

        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(basketEmptyMsg));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}

