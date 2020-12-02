package ru.stqa.cucumber.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stqa.cucumber.pages.CartAddPage;
import ru.stqa.cucumber.pages.CartRemovePage;
import ru.stqa.cucumber.pages.MainPage;

import java.util.concurrent.TimeUnit;

public class Application {

    private static WebDriver driver;

    private MainPage mainPage;
    private CartAddPage cartAddPage;
    private CartRemovePage cartRemovePage;


    public Application() {
        driver = new ChromeDriver();
        this.mainPage = new MainPage(driver);
        this.cartAddPage = new CartAddPage(driver);
        this.cartRemovePage = new CartRemovePage(driver);
    }

    // Добавляем первый товар с главный страницы в корзину
    public void addProductToCart() {
        mainPage.open();
        mainPage.firstProduct.click();

        cartAddPage.selectSizeIfCan().addProductToCart().waitForTableToUpdate();
    }

    public void locateToCart() throws InterruptedException {
        mainPage.cartLink.click();
        // Chrome bug crutchy fix
        TimeUnit.MILLISECONDS.sleep(500);
    }

    // Удаляем первый товар из корзины
    public void removeProductFromCart() {
        cartRemovePage.removeProductAndWaitForItemsTableToUpdate();
    }

    // Удаляем все товары из корзины
    public void removeAllProductsFromCart() {
        for (WebElement button : cartRemovePage.removeButtons) {
            removeProductFromCart();
        }
    }

    public void quit() {
        driver.quit();
    }
}