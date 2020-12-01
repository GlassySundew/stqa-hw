package ru.stqa.cucumber.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeLessThan;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class CartRemovePage extends Page {

    public CartRemovePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Ищем кнопку "Add To Cart" и кликаем
    public CartRemovePage removeProduct() {
        wait.until(visibilityOfElementLocated(By.cssSelector("button[name='remove_cart_item']"))).click();
        return this;
    }

    public void waitForItemsToUpdate() {
        int entriesAmount = driver.findElements(By.cssSelector("table.dataTable.rounded-corners tr")).size();
        wait.until(numberOfElementsToBeLessThan(By.cssSelector("table.dataTable.rounded-corners tr"), entriesAmount));
    }

    @FindBy(css = "button[name=remove_cart_item]")
    public List<WebElement> removeButtons;
}
