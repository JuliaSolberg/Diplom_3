package ru.yandex.praktikum.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class LoginPage {

    // локатор для поля ввода email
    public static final By EMAIL_INPUT = By.xpath(".//div[label[text()='Email']]/input");
    // локатор для поля ввода пароля
    public static final By PASSWORD_INPUT = By.xpath(".//div[label[text()='Пароль']]/input");
    // локатор для кнопки "Войти"
    public static final By LOGIN_BUTTON = By.xpath(".//button[text()='Войти']");

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Авторизация существующего пользователя с email: {email}")
    public void login(String email, String password) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.findElement(EMAIL_INPUT).sendKeys(email);
        driver.findElement(PASSWORD_INPUT).sendKeys(password);
        driver.findElement(LOGIN_BUTTON).click();
    }
}
