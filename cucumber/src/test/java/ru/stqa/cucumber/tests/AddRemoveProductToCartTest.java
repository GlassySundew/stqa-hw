package ru.stqa.cucumber.tests;

import org.junit.Test;

public class AddRemoveProductToCartTest extends TestBase {

    @Test
    public void addAndRemoveThreeItemsToFromCart() throws InterruptedException {
        app.addProductToCart();
        app.addProductToCart();
        app.addProductToCart();

        app.locateToCart();

        app.removeAllProductsFromCart();

    }
}
