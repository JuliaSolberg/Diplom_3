package ru.yandex.praktikum.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class MainPage {

    // локатор для кнопки "Войти в аккаунт"
    public static final By LOGIN_BUTTON = By.xpath(".//button[text()='Войти в аккаунт']");
    // локатор для элемента "Личный Кабинет"
    public static final By ACCOUNT_BUTTON = By.xpath(".//a[contains(@href,'account')]");
    // локатор кнопки "Оформить заказ"
    public static final By MAKE_ORDER_BUTTON = By.xpath(".//div[contains(@class,'BurgerConstructor_basket__container')]/button");
    // локатор для вкладки "Булки"
    public static final By BUNS_TAB = By.xpath(".//span[text()='Булки']/parent::div");
    // локатор для вкладки "Соусы"
    public static final By SAUCES_TAB = By.xpath(".//span[text()='Соусы']/parent::div");
    // локатор для вкладки "Начинки"
    public static final By FILLINGS_TAB = By.xpath(".//span[text()='Начинки']/parent::div");

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажатие кнопки 'Войти в аккаунт'")
    public void clickLoginButton() {
        driver.findElement(LOGIN_BUTTON).click();
    }

    @Step("Нажатие кнопки 'Оформить заказ'")
    public String getOrderButtonText() {
        return driver.findElement(MAKE_ORDER_BUTTON).getText();
    }

    @Step("Нажатие кнопки 'Личный Кабинет'")
    public void clickAccountButton() {
        driver.findElement(ACCOUNT_BUTTON).click();
    }

    @Step("Нажатие вкладки конструктора")
    public void clickTab(By locator) {
        driver.findElement(locator).click();
    }

    @Step("Проверка, что данная вкладка является активной")
    public boolean isTabActive(By locator) {
        return driver.findElement(locator).getAttribute("class").contains("tab_type_current");
    }
}
