package ru.yandex.praktikum.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage {

    // локатор для кнопки "Войти"
    public static final By LOGIN_BUTTON = By.xpath(".//a[contains(@href,'login')]");

    private WebDriver driver;

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажатие кнопки 'Войти' на странице восстановления пароля")
    public void clickLoginButton() {
        driver.findElement(LOGIN_BUTTON).click();
    }
}
