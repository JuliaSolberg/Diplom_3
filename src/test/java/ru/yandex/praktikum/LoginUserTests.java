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
import ru.yandex.praktikum.pageObject.ForgotPasswordPage;
import ru.yandex.praktikum.pageObject.LoginPage;
import ru.yandex.praktikum.pageObject.MainPage;
import ru.yandex.praktikum.pageObject.RegisterPage;
import ru.yandex.praktikum.util.DriverFactory;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@Epic("Авторизация пользователя")
@Feature("Вход в систему из разных точек входа")
public class LoginUserTests {

    public static final String PAGE_URL = "https://stellarburgers.nomoreparties.site";
    public static final String USER_EMAIL = "sitronkake@gmail.com";
    public static final String USER_PASSWORD = "User123";
    public static final String USER_NAME = "User_name";

    private WebDriver driver;
    private String token;
    private MainPage mainPage;

    @Before
    @Step("Открытие браузера, переход на главную страницу + создание нового пользователя")
    public void setUp() {
        driver = DriverFactory.createDriver();
        driver.get(PAGE_URL);
        UserClient.createUser(USER_NAME,USER_EMAIL,USER_PASSWORD);
        token = UserClient.getAccessToken(USER_EMAIL,USER_PASSWORD);
        mainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("loginFromLoginButtonMainPage()")
    @Description("Проверяет вход по кнопке «Войти в аккаунт» на главной")
    public void loginFromLoginButtonMainPage() {
        mainPage.clickLoginButton();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("login"));
        new LoginPage(driver).login(USER_EMAIL,USER_PASSWORD);

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(PAGE_URL + "/"));
        assertTrue(driver.getCurrentUrl().contains(PAGE_URL));
        assertTrue(mainPage.getOrderButtonText().contains("Оформить заказ"));
    }

    @Test
    @DisplayName("loginFromPersonalAccountButtonMainPage()")
    @Description("Проверяет вход через кнопку «Личный кабинет» на главной")
    public void loginFromPersonalAccountButtonMainPage() {
        mainPage.clickAccountButton();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("login"));
        new LoginPage(driver).login(USER_EMAIL,USER_PASSWORD);

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(PAGE_URL + "/"));
        assertTrue(driver.getCurrentUrl().contains(PAGE_URL));
        assertTrue(mainPage.getOrderButtonText().contains("Оформить заказ"));
    }

    @Test
    @DisplayName("loginFromLoginButtonRegisterPage()")
    @Description("Проверяет вход через кнопку в форме регистрации")
    public void loginFromLoginButtonRegisterPage() {
        driver.get(PAGE_URL + "/register");
        new RegisterPage(driver).clickLoginButton();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("login"));
        new LoginPage(driver).login(USER_EMAIL,USER_PASSWORD);

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(PAGE_URL + "/"));
        assertTrue(driver.getCurrentUrl().contains(PAGE_URL));
        assertTrue(mainPage.getOrderButtonText().contains("Оформить заказ"));
    }

    @Test
    @DisplayName("loginFromLoginButtonForgotPasswordPage()")
    @Description("Проверяет вход через кнопку в форме восстановления пароля")
    public void loginFromLoginButtonForgotPasswordPage() {
        driver.get(PAGE_URL + "/forgot-password");
        new ForgotPasswordPage(driver).clickLoginButton();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("login"));
        new LoginPage(driver).login(USER_EMAIL,USER_PASSWORD);

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(PAGE_URL + "/"));
        assertTrue(driver.getCurrentUrl().contains(PAGE_URL));
        assertTrue(mainPage.getOrderButtonText().contains("Оформить заказ"));
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
