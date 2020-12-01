package ru.stqa.cucumber.tests;

import io.cucumber.java8.En;

public class AddRemoveCartTest extends TestBase implements En {
    public AddRemoveCartTest() {
        When("^we add (\\d+) products to the cart$", (Integer arg0) -> {
            if(app == null) start();

            app.addProductToCart();
            app.addProductToCart();
            app.addProductToCart();
        });
        Then("^we successfully remove all items from the cart$", () -> {
            app.locateToCart();

            app.removeAllProductsFromCart();
        });
    }
}
