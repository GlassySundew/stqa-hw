package stqa.maven;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

import javax.management.timer.Timer;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class AppTest {
    public static WebDriver driver;
    public static WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 4);
    }

    @Test
    public void mainTest() {
        driver.get("http://localhost/litecart");
        int productsAmount = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("li[class^=product]"))).size();
        int stickersAmount = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("li[class^=product] div.sticker"))).size();

        assertEquals(productsAmount, stickersAmount);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
