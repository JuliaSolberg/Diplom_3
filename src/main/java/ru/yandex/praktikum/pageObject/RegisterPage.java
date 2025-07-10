
package ru.yandex.praktikum.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {

    // локатор для поля ввода имени
    public static final By NAME_INPUT = By.xpath(".//div[label[text()='Имя']]/input");
    // локатор для поля ввода email
    public static final By EMAIL_INPUT = By.xpath(".//div[label[text()='Email']]/input");
    // локатор для поля ввода пароля
    public static final By PASSWORD_INPUT = By.xpath(".//div[label[text()='Пароль']]/input");
    // локатор для кнопки "Зарегистрироваться"
    public static final By REGISTER_BUTTON = By.xpath(".//button[text()='Зарегистрироваться']");
    // локатор для надписи "Некорректный пароль"
    public static final By WRONG_PASSWORD_NOTIFICATION = By.xpath(".//*[contains(@class,'input__error')]");
    // локатор для элемента div, в котором произошла ошибка - введен слищком короткий пароль
    public static final By PASSWORD_FIELD_CONTAINER = By.xpath(".//label[text()='Пароль']/parent::div");
    // локатор для кнопки "Войти"
    public static final By LOGIN_BUTTON = By.xpath(".//a[contains(@href,'login')]");
    // при вводе некорректного пароля должна появляться красная обводка у поля
    public static final String RED_BORDER_AROUND_ERROR = "input_status_error";

    private WebDriver driver;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ввод имени: {name}")
    public void enterName(String name) {
        driver.findElement(NAME_INPUT).sendKeys(name);
    }

    @Step("Ввод email: {email}")
    public void enterEmail(String email) {
        driver.findElement(EMAIL_INPUT).sendKeys(email);
    }

    @Step("Ввод пароля: {password}")
    public void enterPassword(String password) {
        driver.findElement(PASSWORD_INPUT).sendKeys(password);
    }

    @Step("Нажатие кнопки Зарегистрироваться")
    public void clickRegister() {
        driver.findElement(REGISTER_BUTTON).click();
    }

    @Step("Получение текста ошибки под полем пароля")
    public String getErrorText() {
        return driver.findElement(WRONG_PASSWORD_NOTIFICATION).getText();
    }

    @Step("Получение класса контейнера поля пароля")
    public String getPasswordFieldContainerClass() {
        return driver.findElement(PASSWORD_FIELD_CONTAINER).getAttribute("class");
    }

    @Step("Нажатие кнопки Войти")
    public void clickLoginButton() {
        driver.findElement(LOGIN_BUTTON).click();
    }
}
