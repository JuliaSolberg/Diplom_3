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
import ru.yandex.praktikum.pageObject.RegisterPage;
import ru.yandex.praktikum.util.DriverFactory;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@Epic("Регистрация нового пользователя")
@Feature("Успешная регистрация и регистрация с коротким паролем")
public class RegisterNewUserTest {

    public static final String PAGE_URL = "https://stellarburgers.nomoreparties.site/register";
    public static final String LOGIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/login";
    public static final String USER_NAME = "User";
    public static final String USER_EMAIL = "gullrotkake@gmail.com";
    public static final String USER_PASSWORD = "shadylady";
    public static final String SHORT_PASSWORD = "12345";

    private WebDriver driver;
    private String token;

    @Before
    @Step("Подготовка: открытие браузера и переход на форму регистрации")
    public void setUp() {
        driver = DriverFactory.createDriver();
        driver.get(PAGE_URL);
    }

    @Test
    @DisplayName("registerNewUserValidData()")
    @Description("Проверка успешной регистрации пользователя")
    public void registerNewUserValidData() {

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterName(USER_NAME);
        registerPage.enterEmail(USER_EMAIL);
        registerPage.enterPassword(USER_PASSWORD);
        registerPage.clickRegister();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("login"));

        assertTrue(driver.getCurrentUrl().contentEquals(LOGIN_PAGE_URL));

        token = UserClient.getAccessToken(USER_EMAIL,USER_PASSWORD);
    }

    @Test
    @DisplayName("registerNewUserErrorShortPassword()")
    @Description("Проверка регистрации пользователя с коротким паролем. Ошибка для некорректного пароля")
    public void registerNewUserErrorShortPassword() {

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterName(USER_NAME);
        registerPage.enterEmail(USER_EMAIL);
        registerPage.enterPassword(SHORT_PASSWORD);
        registerPage.clickRegister();

        new WebDriverWait(driver, Duration.ofSeconds(5));

        assertTrue(registerPage.getErrorText().contains("Некорректный пароль"));
        assertTrue(registerPage.getPasswordFieldContainerClass().contains("input_status_error"));
    }

    @After
    @Step("Закрытие сессии и удаление пользователя, если он был создан")
    public void tearDown() {
        if (token != null) {
            UserClient.deleteUser(token);
        }
        driver.quit();
    }
}
