package pages.components;

import io.qameta.allure.Step;
import pages.BasketPage;
import pages.CatalogPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Header extends pages.BasePage {

    private final By basketButton = By.xpath("//a[@href='/basket/']");
    private final By megaMenuButton = By.xpath("//nav[@class='mega-menu']//a[@href='/catalog/']");
    private final By megaMenuCatalogLinks = By.className("header-menu__wide-child-link");
    private final By addingNotice = By.cssSelector("div.notice__detail");

    public Header(WebDriver driver) {

        super(driver);
    }

    @Step("Клик на кнопку корзины в шапке")
    public BasketPage goToBasket() {

        driver.findElement(basketButton).click();
        return new BasketPage(driver);
    }

    @Step("Ожидание пока уведомление в шапке не пропадет")
    public void isAddingNoticeDisappeared() {

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        addingNotice));
    }

    @Step("Переход в каталог через Мега-меню")
    public CatalogPage goToCatalog(int index) {

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(megaMenuButton)).click();

        driver.findElement(megaMenuButton).click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(megaMenuCatalogLinks));

        driver.findElements(megaMenuCatalogLinks).get(index).click();
        return new CatalogPage(driver);

    }
}

