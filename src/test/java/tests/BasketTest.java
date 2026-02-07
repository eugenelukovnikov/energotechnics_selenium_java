package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.Tag;
import pages.BasketPage;
import pages.CatalogPage;
import pages.MainPage;
import pages.ProductPage;
import pages.components.Header;
import org.junit.jupiter.api.Test;
import utils.ScreenshotUtils;

import static org.junit.jupiter.api.Assertions.*;

public class BasketTest extends BaseTest {

    @Epic("Корзина")
    @Feature("Добавление товара в корзину")
    @Story("Товар корректно добавляется в корзину с каталога")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    @Description("""
    Проверяем что товар корректно добавился в корзину:
    1. Открываем каталог
    2. Добавляем товар
    3. Получаем название и цену товара
    4. Переходим в корзину
    5. Сравниваем полученные в каталоге значения со значениями в корзине
    
    Должны совпадать.
    """)
    @Test
    void productShouldBeAddedToBasketCorrectlyFromCatalog() {

        CatalogPage catalogPage = new CatalogPage(driver);
        catalogPage.open();
        catalogPage.addProductToBasket(0);
        String productNameFromCatalog = catalogPage.getProductName(0);
        int productPriceFromCatalog = catalogPage.getProductPriceAsInt(0);

        BasketPage basketPage = new BasketPage(driver);
        basketPage.open();

        int productPriceInBasket = basketPage.getProductPriceInBasket(0);
        int basketSum = basketPage.getBasketSum();
        String productNameInBasket = basketPage.getProductNameInBasket(0);

        assertEquals(productNameFromCatalog, productNameInBasket);
        assertEquals(productPriceFromCatalog, productPriceInBasket);
        assertEquals(productPriceInBasket, basketSum);

    }

    @Epic("Корзина")
    @Feature("Итоговая сумма в корзине")
    @Story("Сумма цен товаров должна совпадать с итоговой ценой корзины")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    @Description("""
    Проверяем, что сумма цен добавленных в корзину товаров совпадает с итоговой:
    1. Открываем каталог
    2. Добавляем товары
    3. Переходим в корзину
    4. Суммируем цены добавленных товаров
    5. Сравниваем с суммой корзины
    
    Должны совпадать.
    """)
    @Test
    void basketTotalShouldEqualProductsSum() {

        CatalogPage catalogPage = new CatalogPage(driver);
        catalogPage.open();
        catalogPage.addProductToBasket(0);
        catalogPage.addProductToBasket(1);

        BasketPage basketPage = new BasketPage(driver);
        basketPage.open();

        int firstProductPrice = basketPage.getProductPriceInBasket(0);
        int secondProductPrice = basketPage.getProductPriceInBasket(1);
        int basketSum = basketPage.getBasketSum();

        assertEquals(firstProductPrice + secondProductPrice, basketSum, "Сумма цен товаров: " + firstProductPrice + " и " + secondProductPrice + " и итоговая сумма корзины: " + basketSum + " должны совпадать, но не совпали.");

    }

    @Epic("Корзина")
    @Feature("Сообщение в пустой корзине")
    @Story("В пустой корзине должно быть сообщение о том, что она пустая")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    @Description("""
    Проверяем, что в корзине имеется уведомление о ее пустоте:
    1. Открываем корзину
    2. Получаем сообщение
    3. Сравниваем с ожидаемым
    
    Должны совпадать.
    """)
    @Test
    void emptyBasketShouldHaveMessage() {

        BasketPage basketPage = new BasketPage(driver);
        basketPage.open();

        String emptyBasketMessage = basketPage.getEmptyBasketText();

        assertEquals("Ваша корзина пуста", emptyBasketMessage, "Ожидаемое сообщение не совпало с полученным: " + emptyBasketMessage);

    }

    @Epic("Корзина")
    @Feature("Добавление товара в корзину")
    @Story("Товар корректно добавляется в корзину с карточки товара")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    @Description("""
    Проверяем, что товар корректно добавляется с карточки товара:
    1. Открываем карточку товара
    2. Добавляем в корзину
    3. Получаем название товара
    4. Получаем цену товара
    5. Переходим в корзину
    6. Получаем цену товара в корзине
    7. Получаем название товара в корзине
    8. Сравниваем ожидаемое и актуальное
    
    Должны совпадать.
    """)
    @Test
    void productShouldBeAddedToBasketCorrectlyFromProductCart() {

        ProductPage productPage = new ProductPage(driver);
        productPage.open();
        productPage.clickAddToBasket();

        String productNameInCart = productPage.getProductName();
        int productPriceInCart = productPage.getProductPrice();

        BasketPage basketPage = new BasketPage(driver);
        basketPage.open();

        int productPriceInBasket = basketPage.getProductPriceInBasket(0);
        String productNameInBasket = basketPage.getProductNameInBasket(0);

        assertEquals(productNameInCart, productNameInBasket, "Название в карточке:\n" + productNameInCart + "\nне совпало с названием в корзине:\n" + productNameInBasket);
        assertEquals(productPriceInCart, productPriceInBasket, "Цена в карточке:\n" + productPriceInCart + "\nне совпало с названием в корзине:\n" + productPriceInBasket);

    }

    @Epic("Корзина")
    @Feature("Практически полный E2E")
    @Story("Главная -> Каталог -> Товар -> Добавить в корзину -> Корзина")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    @Description("""
    Проверяем, что пользователь сможет добавить товар в корзину, если начнет с главной:
    1. Открываем главную страницу
    2. Переходим через Мега-меню в каталог
    3. Из каталога переходим в карточку товара
    4. Добавляем товар в корзину
    5. Получаем название и цену товара
    6. Переходим в корзину через верхнее меню
    7. Получаем цену товара в корзине
    8. Получаем название товара в корзине
    9. Сравниваем ожидаемое и актуальное
    
    Должны совпадать.
    """)
    @Test
    void fullE2EUserFlow() {

        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        Header header = new Header(driver);
        CatalogPage catalogPage = header.goToCatalog(0);

        ProductPage productPage = catalogPage.goToProductCart(0);
        productPage.clickAddToBasket();

        String productNameInCart = productPage.getProductName();
        int productPriceInCart = productPage.getProductPrice();

        BasketPage basketPage = header.goToBasket();

        int productPriceInBasket = basketPage.getProductPriceInBasket(0);
        String productNameInBasket = basketPage.getProductNameInBasket(0);

        assertEquals(productNameInCart, productNameInBasket,"Название в карточке:\n" + productNameInCart + "\nне совпало с названием в корзине:\n" + productNameInBasket);
        assertEquals(productPriceInCart, productPriceInBasket, "Цена в карточке:\n" + productPriceInCart + "\nне совпало с названием в корзине:\n" + productPriceInBasket);

    }

    @Epic("Корзина")
    @Feature("Удаление товара из корзины")
    @Story("Товар корректно удаляется из корзины")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    @Description("""
    Проверяем, что товар корректно удаляется из корзины:
    1. Открываем каталог
    2. Добавляем товар в корзину
    3. Переходим в корзину
    4. Удаляем товар
    5. Проверяем, что появилось сообщение об удалении товара
    6. Обновляем страницу
    7. Должно отображаться сообщение, что корзина пустая
    
    """)
    @Test
    void removeProductFromBasketShouldBeCorrect() {

        CatalogPage catalogPage = new CatalogPage(driver);
        catalogPage.open();
        catalogPage.addProductToBasket(0);

        BasketPage basketPage = new BasketPage(driver);
        basketPage.open();
        basketPage.clickRemoveProductFromBasketButton(0);

        ScreenshotUtils.attachPageScreenshot("Товар удален, должно быть сообщение об этом");

        assertTrue(basketPage.hasRemovedItemContainer(), "Сообщение об удалении товара не появилось");

        driver.navigate().refresh();

        basketPage.isBasketShouldBeEmptyNow();

        String emptyBasketMessage = basketPage.getEmptyBasketText();

        assertEquals("Ваша корзина пуста", emptyBasketMessage, "Ожидаемое сообщение не совпало с полученным: " + emptyBasketMessage);

    }

    @Epic("Корзина")
    @Feature("Полная очистка корзины")
    @Story("Корзина должна корректно очищаться при нажатии на Очистить корзину")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    @Description("""
    Проверяем, что корзина очищается корректно:
    1. Открываем каталог
    2. Добавляем товар в корзину
    3. Переходим в корзину
    4. Нажимаем кнопку Очистить корзину
    5. Проверяем, что появилось сообщение об удалении товара
    6. Обновляем страницу
    7. Должно отображаться сообщение, что корзина пустая
    
    """)
    @Test
    void cleanBasketShouldBeCorrect() {

        CatalogPage catalogPage = new CatalogPage(driver);
        catalogPage.open();
        catalogPage.addProductToBasket(0);

        BasketPage basketPage = new BasketPage(driver);
        basketPage.open();

        basketPage.cleanBasket();

        ScreenshotUtils.attachPageScreenshot("Корзина должна стать пустой");

        driver.navigate().refresh();

        assertTrue(basketPage.isBasketShouldBeEmptyNow(), "Сообщение о пустой корзине не отображается");

        String emptyBasketMessage = basketPage.getEmptyBasketText();

        assertEquals("Ваша корзина пуста", emptyBasketMessage, "Ожидаемое сообщение не совпало " +
                "с полученным: " + emptyBasketMessage);
    }

    @Epic("Корзина")
    @Feature("Восстановление товара после удаления из корзины")
    @Story("Товар должен корректно восстанавливаться после удаления из корзины, если страница не была обновлена")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    @Description("""
    Проверяем, что товар восстанавливается корректно:
    1. Открываем каталог
    2. Добавляем товар в корзину
    3. Переходим в корзину
    4. Нажимаем кнопку Удалить товар
    5. Проверяем, что появилось сообщение об удалении товара
    6. Нажимаем кнопку Восстановить товар
    7. Сообщение об удалении должно пропасть
    
    """)
    @Test
    void restoreProductAfterRemovingShouldBeCorrect() {

        CatalogPage catalogPage = new CatalogPage(driver);
        catalogPage.open();
        catalogPage.addProductToBasket(0);

        BasketPage basketPage = new BasketPage(driver);
        basketPage.open();

        basketPage.clickRemoveProductFromBasketButton(0);
        assertTrue(basketPage.hasRemovedItemContainer(), "Сообщение об удалении товара не появилось.");
        basketPage.clickRestoreRemovedProductInBasketButton(0);
        assertFalse(basketPage.hasRemovedItemContainer(), "Сообщение об удалении товара пропало.");
    }
}
