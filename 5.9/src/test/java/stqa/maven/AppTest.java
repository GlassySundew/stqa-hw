package stqa.maven;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.Comparators;

import javax.swing.*;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.security.Guard;
import java.util.*;
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
    public void mainTest() {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        wait.until(ExpectedConditions.titleIs("My Store"));
        {
            driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

            List<WebElement> countries = driver.findElements(By.cssSelector("tr.row"));

            assertTrue(Comparators.isInOrder(countries, (WebElement firstEle, WebElement secondEle) -> {
                return firstEle.findElement(By.cssSelector("a:not([title])")).getText().compareTo(secondEle.findElement(By.cssSelector("a:not([title])")).getText());
            }));

            // Сохраняем ссылки на все страны, где регионов > 0, чтобы потом перейти по ним
            List<String> countryLinks = new ArrayList<>();
            for (WebElement country : countries)
                for (WebElement td : country.findElements(By.cssSelector("td")))
                    if (td.getAttribute("cellIndex").equals("5") && !td.getText().equals("0"))
                        countryLinks.add(country.findElement(By.cssSelector("a")).getAttribute("href"));

            for (String link : countryLinks) {
                driver.get(link);
                // Всё, кроме последнего, потому что последний - форма для добавления новых элементов
                List<WebElement> zones = driver.findElements(By.cssSelector("table.dataTable tr:not(.header):not(:last-child)"));

                assertTrue(Comparators.isInOrder(zones, (WebElement firstEle, WebElement secondEle) -> {
                    String first = firstEle.findElement(By.cssSelector("td input[name*=name]")).getAttribute("value");
                    String second = secondEle.findElement(By.cssSelector("td input[name*=name]")).getAttribute("value");
                    return first.compareTo(second);
                }));
            }
        }
        
        // Вторая часть задания
        {
            driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
            List<WebElement> countries = driver.findElements(By.cssSelector("tr.row a:not([title])"));

            List<String> countryLinks = new ArrayList<>();
            for (WebElement country : countries)
                countryLinks.add(country.getAttribute("href"));

            for (String link : countryLinks) {
                driver.get(link);

                // Всё, кроме последнего, потому что последний - форма для добавления новых элементов
                List<WebElement> zones = driver.findElements(By.cssSelector("table.dataTable tr:not(.header):not(:last-child)"));

                assertTrue(Comparators.isInOrder(zones, (WebElement firstEle, WebElement secondEle) -> {
                    String first = firstEle.findElement(By.cssSelector("select[name$='[zone_code]'] option[selected]")).getText();
                    String second = secondEle.findElement(By.cssSelector("select[name$='[zone_code]'] option[selected]")).getText();
                    return first.compareTo(second);
                }));
                break;
            }
        }
    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
