package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.pageObject.LoginPage;
import ru.yandex.praktikum.pageObject.MainPage;
import ru.yandex.praktikum.pageObject.ProfilePage;
import ru.yandex.praktikum.util.DriverFactory;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@Epic("Переход из личного кабинета в конструктор")
@Feature("Переход по клику на «Конструктор» и на логотип Stellar Burgers")
public class SwitchFromProfileTests {

    public static final String PAGE_URL = "https://stellarburgers.nomoreparties.site";
    public static final String USER_EMAIL = "pavlovakake@gmail.com";
    public static final String USER_PASSWORD = "User131";
    public static final String USER_NAME = "User_name";

    private WebDriver driver;
    private String token;
    private MainPage mainPage;
    private ProfilePage profilePage;


    @Before
    @Step("Подготовка: создание пользователя, вход и переход в личный кабинет")
    public void setUp() {
        driver = DriverFactory.createDriver();
        driver.get(PAGE_URL);
        UserClient.createUser(USER_NAME, USER_EMAIL, USER_PASSWORD);
        token = UserClient.getAccessToken(USER_EMAIL, USER_PASSWORD);
        mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USER_EMAIL, USER_PASSWORD);
        mainPage.clickAccountButton();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("profile"));
        profilePage = new ProfilePage(driver);
    }

    @Test
    @DisplayName("goToMainPageFromBuilderOnProfile()")
    @Description("Проверка перехода по клику на «Конструктор»")
    public void goToMainPageFromBuilderOnProfile() {
        profilePage.clickBuilder();

        assertTrue(driver.getCurrentUrl().contentEquals(PAGE_URL + "/"));
    }

    @Test
    @DisplayName("goToMainPageFromLogoOnProfile()")
    @Description("Проверка перехода по клику на логотип Stellar Burgers")
    public void goToMainPageFromLogoOnProfile() {
        profilePage.clickLogo();

        assertTrue(driver.getCurrentUrl().contentEquals(PAGE_URL + "/"));
    }

    @After
    @Step("Удаление пользователя и закрытие сессии")
    public void tearDown() {
        if (token != null) {
            UserClient.deleteUser(token);
            System.out.println("Удален");
        }
        driver.quit();
    }
}
