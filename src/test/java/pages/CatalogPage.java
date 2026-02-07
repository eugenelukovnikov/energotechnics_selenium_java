package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CatalogPage extends BasePage {

    private static final String URL = "https://energotechnics.ru/catalog/dizelnie_generators/";
    private final By addToBasketButtons = By.xpath("//span[@title='В корзину']");
    private final By productNames = By.cssSelector(".js-popup-title span");
    private final By productPrices = By.cssSelector(".price__new-val");
    private final By filterPhaseCountProperty = By.xpath(
            "//div[@data-prop_code='kolichestvo_faz_']//div[@class='bx_filter_parameter_label']");
    private final By filterPhaseCountItems = By.xpath(
            "//div[@data-prop_code='kolichestvo_faz_']//span[@class='bx_filter_param_text']");
    private final By addToFavoritesButtons = By.xpath("//a[@data-action='favorite']");
    private final By addToCompareButtons = By.xpath("//a[@data-action='compare']");
    private final By addToBasketButtonCounter = By.className("counter__count-wrapper");
    private final By catalogBlock = By.className("catalog-block");

    public CatalogPage(WebDriver driver) {

        super(driver);
    }

    @Step("Открыть страницу каталога")
    @Override
    public CatalogPage open() {

        driver.get(URL);
        removeWsChatWidget();
        return this;
    }

    @Step("Нажать на кнопку 'В корзину'")
    public void addProductToBasket(int index) {

        driver.findElements(addToBasketButtons).get(index).click();
        stableSleep(3);
    }

    @Step("Нажать на кнопку 'Добавить в избранное'")
    public void addProductToFavorites(int index) {

        driver.findElements(addToFavoritesButtons).get(index).click();
        stableSleep(3);
    }

    @Step("Нажать на кнопку 'Добавить в сравнение'")
    public void addProductToCompareList(int index) {

        driver.findElements(addToCompareButtons).get(index).click();
        stableSleep(3);
    }

    @Step("Получить название товара")
    public String getProductName(int index) {
        return driver.findElements(productNames).get(index).getText();
    }

    @Step("Получить цену товара")
    public int getProductPriceAsInt(int index) {

        String priceString = driver.findElements(productPrices).get(index).getText();
        return parsePrice(priceString);
    }

    @Step("Выбрать требуемое значение фильтра")
    public void selectFilterValue(int index) {

        driver.findElement(filterPhaseCountProperty).click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(filterPhaseCountItems));

        driver.findElements(filterPhaseCountItems).get(index).click();
    }

    @Step("Убедиться, что url изменился на ожидаемый после выбора значения фильтра")
    public void isUrlChangedAfterSelectFilterValue(String expectedUrlPart) {

        new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(driver -> driver.getCurrentUrl().contains(expectedUrlPart));
    }

    @Step("Перейти в карточку товара")
    public ProductPage goToProductCart(int index) {

        driver.findElements(productNames).get(index).click();
        return new ProductPage(driver);

    }

    @Step("После нажатия на кнопку 'В корзину' должен отобразиться счетчик товара в корзине")
    public boolean isCounterDisplayedAfterAddingProduct() {

        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(addToBasketButtonCounter));
            return true;
        } catch (TimeoutException e) {
            return false;
        }

    }

    @Step("После нажатия на кнопку 'Добавить в избранное' кнопка должна выделяться")
    public boolean isFavoriteButtonActiveAfterClick(int index) {

        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.attributeContains(
                            driver.findElements(addToFavoritesButtons).get(index), "class", "active"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("После нажатия на кнопку 'Добавить в сравнение' кнопка должна выделяться")
    public boolean isCompareButtonActiveAfterClick(int index) {

        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.attributeContains(
                            driver.findElements(addToCompareButtons).get(index), "class", "active"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Проверка, что каталог с товарами загружается")
    public void isCatalogBlockShouldLoad() {

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(catalogBlock));
    }


}

