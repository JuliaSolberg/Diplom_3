package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.pageObject.MainPage;
import ru.yandex.praktikum.util.DriverFactory;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@Epic("Главная страница")
@Feature("Переключение вкладок конструктора")
@RunWith(Parameterized.class)
public class ConstructorTabsParametrizedTests {

    public static final String PAGE_URL = "https://stellarburgers.nomoreparties.site";

    private WebDriver driver;
    private MainPage mainPage;

    private String tabName;
    private By tabLocator;

    public ConstructorTabsParametrizedTests(String tabName, By tabLocator) {
        this.tabName = tabName;
        this.tabLocator = tabLocator;
    }

    @Parameterized.Parameters(name = "Вкладка: {0}")
    public static Object[][] getTabs() {
        return new Object[][] {
                {"Булки", MainPage.BUNS_TAB},
                {"Соусы", MainPage.SAUCES_TAB},
                {"Начинки", MainPage.FILLINGS_TAB},
        };
    }

    @Before
    @Step("Открытие главной страницы")
    public void setUp() {
        driver = DriverFactory.createDriver();
        driver.get(PAGE_URL);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        mainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("checkConstructorTabIsActive()")
    @Description("Проверка перехода на вкладку конструктора: {tabName}")
    public void checkConstructorTabIsActive() {

        if (tabName.contains("Булки")) {
            mainPage.clickTab(MainPage.SAUCES_TAB);
            mainPage.clickTab(tabLocator);
            new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.attributeContains(tabLocator,"class","current"));

        } else {
            mainPage.clickTab(tabLocator);
        }
        
        assertTrue("Вкладка '" + tabName + "' должна быть активной", mainPage.isTabActive(tabLocator));
    }

    @After
    @Step("Закрытие сессии")
    public void tearDown() {
        driver.quit();
    }
}
