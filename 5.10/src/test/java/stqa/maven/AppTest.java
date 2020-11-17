package stqa.maven;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.Color;

import static org.junit.Assert.*;

import javax.management.timer.Timer;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class AppTest {
    public static WebDriver driver;
    public static WebDriverWait wait;

    @Test
    public void mainTest() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 4);
        testPerDriver();
        stop();

        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 4);
        testPerDriver();
        stop();

        switch (System.getProperty("os.name")) {
            case "Linux":
                System.setProperty("webdriver.edge.driver", "/usr/bin/msedgedriver");
                break;
            default:
                System.setProperty("webdriver.edge.driver", "C://Tools/EdgeDriver.exe");
                break;
        }
        driver = new EdgeDriver();
        wait = new WebDriverWait(driver, 4);
        testPerDriver();
        stop();
    }

    void testPerDriver() {
        driver.get("http://localhost/litecart/");

        WebElement productCard = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[id=box-campaigns] li.product")));
        String mainName = productCard.findElement(By.cssSelector("div.name")).getText();
        String regularPrice = productCard.findElement(By.cssSelector("s.regular-price")).getText();
        String auctionPrice = productCard.findElement(By.cssSelector("strong.campaign-price")).getText();

        // в)
        {
            assertEquals("line-through", productCard.findElement(By.cssSelector("s.regular-price")).getCssValue("text-decoration-line"));
            Color regularPrColor = Color.fromString(productCard.findElement(By.cssSelector("s.regular-price")).getCssValue("color"));
            assertEquals(regularPrColor.getColor().getRed(), regularPrColor.getColor().getBlue());
            assertEquals(regularPrColor.getColor().getRed(), regularPrColor.getColor().getGreen());
        }

        // г)
        {
            assertTrue(Integer.parseInt(productCard.findElement(By.cssSelector("strong.campaign-price")).getCssValue("font-weight")) >= 700);
            Color auctionPrColor = Color.fromString(productCard.findElement(By.cssSelector("strong.campaign-price")).getCssValue("color"));
            assertEquals(0, auctionPrColor.getColor().getBlue());
            assertEquals(0, auctionPrColor.getColor().getGreen());
        }

        // д)
        {
            String campaignSize = productCard.findElement(By.cssSelector("strong.campaign-price")).getCssValue("font-size");
            String regularSize = productCard.findElement(By.cssSelector("s.regular-price")).getCssValue("font-size");

            assertTrue(Float.parseFloat(campaignSize.substring(0, campaignSize.length() - 2)) >
                    Float.parseFloat(regularSize.substring(0, regularSize.length() - 2)));
        }

        productCard.click();

        // a)
        assertEquals(mainName, wait.until(presenceOfElementLocated(By.cssSelector("h1.title"))).getText());

        // б)
        assertEquals(regularPrice, wait.until(presenceOfElementLocated(By.cssSelector("s.regular-price"))).getText());
        assertEquals(auctionPrice, wait.until(presenceOfElementLocated(By.cssSelector("strong.campaign-price"))).getText());

        // в)
        {
            Color regularPrColor = Color.fromString(driver.findElement(By.cssSelector("s.regular-price")).getCssValue("color"));
            assertEquals(regularPrColor.getColor().getRed(), regularPrColor.getColor().getBlue());
            assertEquals(regularPrColor.getColor().getRed(), regularPrColor.getColor().getGreen());
        }

        // г)
        {
            Color auctionPrColor = Color.fromString(driver.findElement(By.cssSelector("strong.campaign-price")).getCssValue("color"));
            assertEquals(0, auctionPrColor.getColor().getBlue());
            assertEquals(0, auctionPrColor.getColor().getGreen());
        }

        // д)
        {
            String campaignSize = driver.findElement(By.cssSelector("strong.campaign-price")).getCssValue("font-size");
            String regularSize = driver.findElement(By.cssSelector("s.regular-price")).getCssValue("font-size");

            assertTrue(Float.parseFloat(campaignSize.substring(0, campaignSize.length() - 2)) >
                    Float.parseFloat(regularSize.substring(0, regularSize.length() - 2)));
        }
    }


    public void stop() {
        driver.quit();
        driver = null;
    }
}
