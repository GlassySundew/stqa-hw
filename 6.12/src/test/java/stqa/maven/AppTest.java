package stqa.maven;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

import java.security.Key;
import java.sql.Time;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;


public class AppTest {
    public static WebDriver driver;
    public static WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 4);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
    }

    @Test
    public void mainTest() throws InterruptedException {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog");

        driver.findElement(By.cssSelector("a.button[href='http://localhost/litecart/admin/?category_id=0&app=catalog&doc=edit_product']")).click();

        TimeUnit.SECONDS.sleep(1);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='status'][value='1']"))).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='name[en]']"))).click();
        new Actions(driver).sendKeys(generateAlphabeticString(6)).sendKeys(Keys.TAB).perform();
        new Actions(driver).sendKeys(generateNumericString(6)).sendKeys(Keys.TAB).perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='date_valid_from']"))).click();
        new Actions(driver)
                .sendKeys(Keys.LEFT)
                .sendKeys(Keys.LEFT)
                .sendKeys("12312000")
                .sendKeys(Keys.TAB)
                .sendKeys("12312000").perform();

        WebElement imgUpload = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='new_images[]'")));
        imgUpload.sendKeys(System.getProperty("user.dir") + "/card.jpg");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='product_groups[]'][value='1-3']"))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='quantity'][value='0.00']"))).click();
        new Actions(driver).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE)
                .sendKeys(generateNumericString(5)).perform();


        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href='#tab-information']"))).click();

        TimeUnit.SECONDS.sleep(1);

        Select selectManufacturer = new Select(driver.findElement(By.cssSelector("select[name='manufacturer_id']")));
        selectManufacturer.selectByVisibleText("ACME Corp.");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='keywords']"))).sendKeys(generateAlphabeticString(8));

        new Actions(driver)
                .sendKeys(Keys.TAB)
                .sendKeys(generateAlphabeticString(8))
                .sendKeys(Keys.TAB)
                .sendKeys(generateAlphabeticString(8))
                .sendKeys(Keys.TAB)
                .sendKeys(generateAlphabeticString(8))
                .sendKeys(Keys.TAB)
                .sendKeys(generateAlphabeticString(8))
                .perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href='#tab-prices']"))).click();

        TimeUnit.SECONDS.sleep(1);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='purchase_price']"))).click();
        new Actions(driver)
                .sendKeys(Keys.DELETE)
                .sendKeys(Keys.DELETE)
                .sendKeys(Keys.DELETE)
                .sendKeys(Keys.DELETE)
                .sendKeys(generateNumericString(4))
                .perform();

        Select selectCurrency = new Select(driver.findElement(By.cssSelector("select[name='purchase_price_currency_code']")));
        selectCurrency.selectByVisibleText("US Dollars");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='prices[USD]']"))).sendKeys(generateNumericString(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='gross_prices[USD]']"))).sendKeys(generateNumericString(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='prices[EUR]']"))).sendKeys(generateNumericString(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='gross_prices[EUR]']"))).sendKeys(generateNumericString(5));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[name='save']"))).click();
    }

    public String generateNumericString(int length) {
        return generateString(48, 57, length);
    }

    public String generateAlphabeticString(int length) {
        return generateString(97, 122, length);

    }

    public String generateString(int leftLimit, int rightLimit, int targetStringLength) {
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}