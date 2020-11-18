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
import java.util.concurrent.TimeUnit;


public class AppTest {
    public static WebDriver driver;
    public static WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 4);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void mainTest() throws InterruptedException {
        driver.get("http://localhost/litecart/en/create_account");

        // Tax id
        WebElement initialField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[name=tax_id]")));
        initialField.click();
        new Actions(driver).sendKeys(generateNumericString(10)).perform();

        // Company
        new Actions(driver).sendKeys(Keys.TAB).sendKeys(generateAlphabeticString(10)).perform();

        // First name
        new Actions(driver).sendKeys(Keys.TAB).sendKeys(generateAlphabeticString(10)).perform();

        // Last name
        new Actions(driver).sendKeys(Keys.TAB).sendKeys(generateAlphabeticString(10)).perform();

        // Address 1
        new Actions(driver).sendKeys(Keys.TAB).sendKeys(generateAlphabeticString(10)).perform();

        // Address 2
        new Actions(driver).sendKeys(Keys.TAB).sendKeys(generateAlphabeticString(10)).perform();

        // Postcode
        new Actions(driver).sendKeys(Keys.TAB).sendKeys(generateNumericString(5)).perform();

        // City
        new Actions(driver).sendKeys(Keys.TAB).sendKeys(generateAlphabeticString(10)).perform();

        // Country
        new Actions(driver).sendKeys(Keys.TAB).perform();
        Select selectCountry = new Select(driver.findElement(By.cssSelector("select.select2-hidden-accessible")));
        selectCountry.selectByVisibleText("United States");

        // State
        new Actions(driver).sendKeys(Keys.TAB).perform();
        Select selectState = new Select(driver.findElement(By.cssSelector("select[name=zone_code]")));
        selectState.selectByVisibleText("Alabama");

        // Email
        String email = generateAlphabeticString(10) + "@" + generateAlphabeticString(5) + "." + generateAlphabeticString(5);
        new Actions(driver)
                .sendKeys(Keys.TAB)
                .sendKeys(Keys.TAB)
                .sendKeys(email)
                .perform();

        // Phone
        new Actions(driver).sendKeys(Keys.TAB).sendKeys(generateNumericString(10)).perform();

        new Actions(driver).sendKeys(Keys.TAB).sendKeys(Keys.SPACE).sendKeys(Keys.TAB).perform();

        // Passwords
        String passwd = generateAlphabeticString(12);
        new Actions(driver).sendKeys(passwd).sendKeys(Keys.TAB).sendKeys(passwd).sendKeys(Keys.TAB).perform();

        // Confirming
        new Actions(driver).sendKeys("\n").perform();

        // Log out
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Logout"))).click();

        // Log in
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name=email]"))).click();
        new Actions(driver).sendKeys(email).sendKeys(Keys.TAB).sendKeys(passwd).sendKeys(Keys.TAB).sendKeys(Keys.TAB).sendKeys("\n").perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Logout"))).click();
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