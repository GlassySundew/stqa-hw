package stqa.maven;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.management.timer.Timer;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
    public void mainTest() throws InterruptedException {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");

        for (int i = 0; i < wait.until(presenceOfAllElementsLocatedBy(By.cssSelector("td a[href*='category_id=1&product_id=']"))).size() / 2; i++) {
            wait.until(presenceOfAllElementsLocatedBy(By.cssSelector("td a[href*='category_id=1&product_id=']"))).get(i * 2).click();
            Assert.assertEquals(driver.manage().logs().get("browser").getAll(), new ArrayList<LogEntry>());
            driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}