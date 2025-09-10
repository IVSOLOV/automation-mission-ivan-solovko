package AutomationTest.mission.steps;

import AutomationTest.mission.pages.*;
import AutomationTest.mission.BrowserSetup;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.Map;

public class UISteps {
    private final WebDriver driver = BrowserSetup.getDriver();
    private final LoginPage login = new LoginPage(driver);
    private final InventoryPage inventory = new InventoryPage(driver);
    private final CartPage cart = new CartPage(driver);
    private final CheckoutPage checkout = new CheckoutPage(driver);

    @Given("I open the app")
    public void i_open_the_app() { driver.get(BrowserSetup.prop("BaseUrl", "https://www.saucedemo.com/")); }

    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String u, String p) { login.login(u, p); }

    @Then("I should see the Products page")
    public void i_should_see_the_products_page() { Assert.assertTrue(inventory.isLoaded(), "Inventory not loaded"); }

    @When("I add product {string} to the cart")
    public void i_add_product_to_the_cart(String name) { inventory.addToCart(name); }

    @When("I open the cart")
    public void i_open_the_cart() { inventory.openCart(); Assert.assertTrue(cart.isLoaded(), "Cart not loaded"); }

    @When("I proceed to checkout with:")
    public void i_proceed_to_checkout_with(DataTable table) {
        Map<String,String> row = table.asMaps().get(0);
        cart.checkout();
        checkout.fillInfo(row.get("firstName"), row.get("lastName"), row.get("postalCode"));
        checkout.finish();
    }

    @Then("I should see the checkout complete screen")
    public void i_should_see_the_checkout_complete_screen() { Assert.assertTrue(checkout.isComplete(), "No completion"); }

    @When("I sort products by {string}")
    public void i_sort_products_by(String opt) { inventory.sortBy(opt); }

    @Then("the first product title should be {string}")
    public void the_first_product_title_should_be(String expected) { Assert.assertEquals(inventory.firstItemTitle(), expected); }
}
