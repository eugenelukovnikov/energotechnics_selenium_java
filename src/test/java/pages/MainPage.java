package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage {

    private static final String URL = "https://energotechnics.ru/";

    public MainPage(WebDriver driver) {

        super(driver);
    }

    @Step("Открываем главную страницу")
    @Override
    public MainPage open() {

        driver.get(URL);
        removeWsChatWidget();
        return this;
    }
}

