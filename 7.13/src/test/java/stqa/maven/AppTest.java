package stqa.maven;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

import java.security.Key;
import java.sql.Time;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class AppTest {
    public static WebDriver driver;
    public static WebDriverWait wait;

    @Before
    public void start() {

        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 4);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void mainTest() throws InterruptedException {
        driver.get("http://localhost/litecart/en/");

        // Добавляем первые 3 товара
        for (int i = 1; i < 4; i++) {
            driver.findElement(By.cssSelector("li.product:nth-child(" + i + ")")).click();
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("[name='add_cart_product']"), "Add To Cart"));

            // Если утка жёлтая или какая ещё, у которой есть обязательный select - выбираем
            try {
                Select select = new Select(driver.findElement(By.cssSelector("select[name='options[Size]']")));
                select.selectByVisibleText("Small");
            } catch (NoSuchElementException e) {
            }

            driver.findElement(By.cssSelector("[name='add_cart_product']")).click();
            String currentQuantity = driver.findElement(By.cssSelector("span.quantity")).getText();
            wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(driver.findElement(By.cssSelector("span.quantity")), currentQuantity)));

            driver.get("http://localhost/litecart/en/");
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Checkout »"))).click();

        // Удаляем из корзины
        for (WebElement button : wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("button[name=remove_cart_item]")))) {
            int entriesAmount = driver.findElements(By.cssSelector("table.dataTable.rounded-corners tr")).size();
            WebElement removeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[name='remove_cart_item']")));
            removeButton.click();
            wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector("table.dataTable.rounded-corners tr"), entriesAmount));
            wait.until(ExpectedConditions.invisibilityOf(removeButton));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}