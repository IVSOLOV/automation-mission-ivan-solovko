@ui @all
Feature: SauceDemo UI
  Background:
    Given I open the app

  Scenario: Standard user can login and checkout one item
    When I login with username "standard_user" and password "secret_sauce"
    Then I should see the Products page
    When I add product "Sauce Labs Backpack" to the cart
    And I open the cart
    And I proceed to checkout with:
      | firstName | lastName | postalCode |
      | Ivan      | Solovko  | 19134      |
    Then I should see the checkout complete screen

  Scenario: Sort Z to A shows reverse order
    When I login with username "standard_user" and password "secret_sauce"
    And I sort products by "Name (Z to A)"
    Then the first product title should be "Test.allTheThings() T-Shirt (Red)"
