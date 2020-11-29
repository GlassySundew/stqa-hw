package stqa.maven.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class CartAddPage extends Page {

    public CartAddPage(WebDriver driver) {
        super(driver);
    }

    // Если у товара есть Select с обязательным выбором размера - то надо выбрать
    public CartAddPage selectSizeIfCan() {
        try {
            Select select = new Select(driver.findElement(By.cssSelector("select[name='options[Size]']")));
            select.selectByVisibleText("Small");
        } catch (NoSuchElementException e) {
        }
        return this;
    }

    // Ищем кнопку "Add To Cart" и кликаем
    public CartAddPage addProductToCart() {
        driver.findElement(By.cssSelector("[name='add_cart_product']")).click();
        return this;
    }

    public void waitForTableToUpdate() {
        String currentQuantity = driver.findElement(By.cssSelector("span.quantity")).getText();
        wait.until(not(textToBePresentInElement(driver.findElement(By.cssSelector("span.quantity")), currentQuantity)));
    }
}
