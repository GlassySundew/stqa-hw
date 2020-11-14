package stqa.maven;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
//        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
    }

    @Test
    public void mainTest() throws InterruptedException {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        wait.until(ExpectedConditions.titleIs("My Store"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ul[id=box-apps-menu]:first-child")));

        // Костыль чтобы сбросить анимацию загрузки
        driver.get("http://localhost/litecart/admin");

        // Поиск и кликание по пунктам  меню
        for (int i = 0; i < driver.findElements(By.cssSelector("li[id=app-]")).size(); i++) {
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[id=app-]:nth-of-type(" + (i + 1) + ")"))).click();;
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
            // Поиск и кликание по вложенным пунктам
            for (int j = 0; j < driver.findElements(By.xpath("//li[starts-with(@id,'doc')]")).size(); j++) {
                driver.findElement(By.xpath("//li[starts-with(@id,'doc')][" + (j + 1) + "]")).click();
                driver.findElement(By.xpath("//h1"));
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
