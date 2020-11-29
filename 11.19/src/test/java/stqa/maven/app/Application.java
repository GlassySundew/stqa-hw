package stqa.maven.app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.Comparators;
import stqa.maven.pages.CartAddPage;
import stqa.maven.pages.CartRemovePage;
import stqa.maven.pages.MainPage;

import javax.swing.*;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.security.Guard;
import java.util.*;
import java.util.NoSuchElementException;
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
        TimeUnit.MILLISECONDS.sleep(500);
    }

    // Удаляем первый товар из корзины
    public void removeProductFromCart() {
        cartRemovePage.removeProduct().waitForItemsToUpdate();
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