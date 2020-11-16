package stqa.maven;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.management.timer.Timer;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

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

        wait.until(ExpectedConditions.titleIs("My Store"));
//        wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id=loader]"))));
//        try {
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[id=loader]")));
//        } catch (NoSuchElementException e) {
//        } catch (TimeoutException e) {
//        }

        // Костыль чтобы сбросить анимацию загрузки
        driver.get("http://localhost/litecart/admin");
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li[id=app-]")));
        // Поиск и кликание по пунктам  меню
        for (int i = 0; i < driver.findElements(By.cssSelector("li[id=app-]")).size(); i++) {
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[id=app-]:nth-of-type(" + (i + 1) + ")"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
            // Поиск и кликание по вложенным пунктам
            for (int j = 0; j < driver.findElements(By.xpath("//li[starts-with(@id,'doc')]")).size(); j++) {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[starts-with(@id,'doc')][" + (j + 1) + "]"))).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
                TimeUnit.MILLISECONDS.sleep(500);
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
