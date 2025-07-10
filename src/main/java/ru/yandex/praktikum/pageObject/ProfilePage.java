package ru.yandex.praktikum.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage {

    // локатор для логотипа Stellar Burgers
    public static final By LOGO = By.xpath(".//div[contains(@class,'logo')]/a");
    // локатор для ссылки "Конструктор"
    public static final By BUILDER = By.xpath(".//p[text()='Конструктор']/parent::a");
    // локатор для кнопки "Выход"
    public static final By LOGOUT_BUTTON = By.xpath(".//button[text()='Выход']");

    private WebDriver driver;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажатие ссылки 'Конструктор'")
    public void clickBuilder() {
        driver.findElement(BUILDER).click();
    }

    @Step("Нажатие на логотип Stellar Burger")
    public void clickLogo() {
        driver.findElement(LOGO).click();
    }

    @Step("Нажатие кнопки 'Выход'")
    public void clickLogout() {
        driver.findElement(LOGOUT_BUTTON).click();
    }
}
