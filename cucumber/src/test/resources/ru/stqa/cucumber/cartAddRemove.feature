
  Feature: Cart testing

    Scenario: Add and remove 3 products from and to cart
      When we add 3 products to the cart
      Then we successfully remove all items from the cart
