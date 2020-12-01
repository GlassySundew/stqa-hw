package ru.stqa.cucumber.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends Page {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("http://localhost/litecart/en/");
        PageFactory.initElements(driver, this);
    }

    @FindBy(linkText = "Checkout »")
    public WebElement cartLink;

    @FindBy(css = "li.product")
    public WebElement firstProduct;
}
