package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.Tag;
import pages.CatalogPage;
import org.junit.jupiter.api.Test;
import utils.ScreenshotUtils;

import static org.junit.jupiter.api.Assertions.*;

public class CatalogTest extends BaseTest {

    @Epic("Каталог")
    @Feature("Фильтр")
    @Story("Фильтр должен корректно подменять мета-теги и урл, если условие под свойство создано")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    @Description("""
    Проверяем, что подмена мета-тегов и урла работает корректно:
    1. Открываем каталог
    2. Получаем текущий (старый) урл.
    3. Выбираем нужное значение фильтра и сравниваем с полученным урлом
    4. Получаем Title, H1, URL после изменения
    5. Проверяем, что URL до != URL после
    6. Проверяем соответствие полученных мета-тегов ожидаемым, должны совпадать

    """)
    @Test
    void filterShouldCorrectlyReplaceMetaTags() {

        CatalogPage catalogPage = new CatalogPage(driver);
        catalogPage.open();
        String urlBefore = driver.getCurrentUrl();

        catalogPage.selectFilterValue(0);
        catalogPage.isUrlChangedAfterSelectFilterValue("odnofaznye-220-V");

        String title = catalogPage.getPageTitle();
        String h1 = catalogPage.getH1Text();
        String urlAfter = driver.getCurrentUrl();

        assertNotEquals(urlBefore, urlAfter, "Урлы не совпали.");
        assertEquals("Дизельные генераторы c количеством фаз 1 - купить в Москве", title, "Ожидаемый Title не совпал с полученным.");
        assertEquals("Дизельные однофазные генераторы 220 В в Москве", h1, "Ожидаемый H1 не совпал с полученным.");

    }

    @Epic("Каталог")
    @Feature("Кнопка 'В корзину'")
    @Story("Состояние кнопки 'В корзину' сохраняется после обновления страницы")
    @Severity(SeverityLevel.NORMAL)
    @Tag("smoke")
    @Description("""
    Проверяем статус кнопки 'В корзину' в каталоге после клика на нее и обновления страницы:
    Клик 'В корзину' -> F5 -> счётчик остаётся
    """)
    @Test
    void checkAddToBasketButtonPersistenceAfterClick() {

        CatalogPage catalogPage = new CatalogPage(driver);
        catalogPage.open();

        catalogPage.addProductToBasket(0);

        ScreenshotUtils.attachPageScreenshot("Кнопка после клика должна быть активна");

        assertTrue(catalogPage.isCounterDisplayedAfterAddingProduct(), "Счетчик не отобразился");

        driver.navigate().refresh();

        catalogPage.isCatalogBlockShouldLoad();

        assertTrue(catalogPage.isCounterDisplayedAfterAddingProduct(), "Счетчик не отобразился");
    }

    @Epic("Каталог")
    @Feature("Кнопка 'В избранное'")
    @Story("Состояние кнопки 'В избранное' сохраняется после обновления страницы")
    @Severity(SeverityLevel.MINOR)
    @Tag("smoke")
    @Description("""
    Проверяем статус кнопки 'В избранное' в каталоге после клика на нее и обновления страницы:
    Клик 'В корзину' -> F5 -> активность сохраняется
    """)
    @Test
    void checkAddToFavoriteButtonPersistenceAfterClick() {

        CatalogPage catalogPage = new CatalogPage(driver);
        catalogPage.open();

        catalogPage.addProductToFavorites(0);

        ScreenshotUtils.attachPageScreenshot("Кнопка после клика должна быть активна");

        assertTrue(catalogPage.isFavoriteButtonActiveAfterClick(0), "Кнопка 'В избранное' не выделяется");

        driver.navigate().refresh();

        catalogPage.isCatalogBlockShouldLoad();

        assertTrue(catalogPage.isFavoriteButtonActiveAfterClick(0), "Кнопка 'В избранное' не выделяется");
    }

    @Epic("Каталог")
    @Feature("Кнопка 'В сравнение'")
    @Story("Состояние кнопки 'В сравнение' сохраняется после обновления страницы")
    @Severity(SeverityLevel.MINOR)
    @Tag("smoke")
    @Description("""
    Проверяем статус кнопки 'В сравнение' в каталоге после клика на нее и обновления страницы:
    Клик 'В сравнение' -> F5 -> активность сохраняется
    """)
    @Test
    void checkAddToCompareButtonPersistenceAfterClick() {

        CatalogPage catalogPage = new CatalogPage(driver);
        catalogPage.open();

        catalogPage.addProductToCompareList(0);

        ScreenshotUtils.attachPageScreenshot("Кнопка после клика должна быть активна");

        assertTrue(catalogPage.isCompareButtonActiveAfterClick(0), "Кнопка 'В сравнение' не выделяется");

        driver.navigate().refresh();

        catalogPage.isCatalogBlockShouldLoad();

        assertTrue(catalogPage.isCompareButtonActiveAfterClick(0), "Кнопка 'В сравнение' не выделяется");
    }
}

