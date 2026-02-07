package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductPage extends BasePage {

    private static final String URL = "https://energotechnics.ru/catalog/dizelnie_generators/dizelnyy_generator_ldg6500cl/";

    private static final By addToBasketButton = By.xpath("//span[@data-action='basket']");
    private static final By productH1 = By.tagName("h1");
    private static final By productPrice = By.cssSelector(".price__new span");
    private final By addToFavoritesButton = By.xpath("//a[@data-action='favorite']");
    private final By addToCompareButton = By.xpath("//a[@data-action='compare']");
    private final By addToBasketButtonCounter = By.className("counter__count-wrapper");

    public ProductPage(WebDriver driver) {

        super(driver);
    }

    @Step("Открыть страницу товара")
    @Override
    public ProductPage open() {

        driver.get(URL);
        removeWsChatWidget();
        return this;
    }

    @Step("Нажать 'В корзину'")
    public void clickAddToBasket() {

        driver.findElement(addToBasketButton).click();
        stableSleep(3);
    }

    @Step("Нажать 'В избранное'")
    public void addProductToFavorites() {

        driver.findElement(addToFavoritesButton).click();
        stableSleep(3);
    }

    @Step("Нажать 'В сравнение'")
    public void addProductToCompare() {

        driver.findElement(addToCompareButton).click();
        stableSleep(3);
    }

    @Step("'Получить название товара")
    public String getProductName() {

        return driver.findElement(productH1).getText();
    }

    @Step("'Получить цену товара")
    public int getProductPrice() {

        String productPriceString = driver.findElement(productPrice).getText();
        return parsePrice(productPriceString);
    }

    @Step("'В избранное' активна")
    public boolean isFavoriteButtonActiveAfterClick() {

        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.attributeContains(addToFavoritesButton, "class", "active"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("'В сравнение' активна")
    public boolean isCompareButtonActiveAfterClick() {

        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.attributeContains(addToCompareButton, "class", "active"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("'Счетчик товара' активен")
    public boolean isCounterDisplayedAfterAddingProduct() {

        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(addToBasketButtonCounter));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}

