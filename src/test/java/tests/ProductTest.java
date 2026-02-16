package tests;

import pages.ProductPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import io.qameta.allure.*;
import utils.ScreenshotUtils;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest extends BaseTest {

    @Epic("Страница товара")
    @Feature("Кнопка 'В корзину'")
    @Story("Состояние нажатой кнопки 'В корзину' сохраняется после обновления страницы")
    @Severity(SeverityLevel.NORMAL)
    @Tag("smoke")
    @Description("""
    Проверяем статус кнопки 'В корзину' после клика на нее и обновления страницы:
    Добавить 'В корзину' -> F5 -> счётчик остаётся
    """)
    @Test
    void checkAddToBasketButtonPersistenceAfterRefresh() {

        ProductPage productPage = new ProductPage(driver);
        productPage.open();

        productPage.clickAddToBasket();

        ScreenshotUtils.attachPageScreenshot("Кнопка после клика должна быть активна");

        assertTrue(productPage.isCounterDisplayedAfterAddingProduct(), "Счетчик должен отображаться вместо " +
                "кнопки В корзину после нажатия на нее, но этого не произошло");

        driver.navigate().refresh();

        assertTrue(productPage.isCounterDisplayedAfterAddingProduct(), "Счетчик должен отображаться вместо " +
                "кнопки В корзину после нажатия на нее, но этого не произошло");
    }

    @Epic("Страница товара")
    @Feature("Кнопка 'В избранное'")
    @Story("Состояние нажатой кнопки 'В избранное' сохраняется после обновления страницы")
    @Severity(SeverityLevel.MINOR)
    @Tag("smoke")
    @Description("""
    Проверяем статус кнопки 'В избранное' после клика на нее и обновления страницы:
    'В избранное' -> F5 -> активность сохраняется
    """)
    @Test
    void checkAddToFavoriteButtonPersistenceAfterRefresh() {

        ProductPage productPage = new ProductPage(driver);
        productPage.open();

        productPage.addProductToFavorites();

        ScreenshotUtils.attachPageScreenshot("Кнопка после клика должна быть активна");

        assertTrue(productPage.isFavoriteButtonActiveAfterClick(), "Кнопка В избранное должна выделяться " +
                "после нажатия на нее, но этого не произошло");

        driver.navigate().refresh();

        assertTrue(productPage.isFavoriteButtonActiveAfterClick(), "Кнопка В избранное должна выделяться " +
                "после нажатия на нее, но этого не произошло");
    }

    @Epic("Страница товара")
    @Feature("Кнопка 'В сравнение'")
    @Story("Состояние нажатой кнопки 'В сравнение' сохраняется после обновления страницы")
    @Severity(SeverityLevel.MINOR)
    @Tag("smoke")
    @Description("""
    Проверяем статус кнопки 'В сравнение' после клика на нее и обновления страницы:
    В сравнение -> F5 -> активность сохраняется
    """)

    @Test
    void checkAddToCompareButtonPersistenceAfterRefresh() {

        ProductPage productPage = new ProductPage(driver);
        productPage.open();

        productPage.addProductToCompare();

        ScreenshotUtils.attachPageScreenshot("Кнопка после клика должна быть активна");

        assertTrue(productPage.isCompareButtonActiveAfterClick(), "Кнопка В сравнение должна выделяться " +
                "после нажатия на нее, но этого не произошло");

        driver.navigate().refresh();

        assertTrue(productPage.isCompareButtonActiveAfterClick(), "Кнопка В сравнение должна выделяться " +
                "после нажатия на нее, но этого не произошло");

    }

}
