package AutomationTest.mission.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class InventoryPage {
    private final WebDriver driver;
    private final By title = By.cssSelector(".title");
    private final By cart = By.cssSelector(".shopping_cart_link");
    private final By sorter = By.cssSelector("select.product_sort_container");

    public InventoryPage(WebDriver driver) { this.driver = driver; }

    public boolean isLoaded() {
        return driver.findElements(title).stream().anyMatch(e -> e.getText().contains("Products"));
    }

    public void addToCart(String productName) {
        String slug = productName.toLowerCase().replace(" ", "-").replace("(", "").replace(")", "").replace(".", "");
        By addBtn = By.id("add-to-cart-" + slug);
        driver.findElement(addBtn).click();
    }

    public void openCart() { driver.findElement(cart).click(); }

    public void sortBy(String optionVisibleText) { new Select(driver.findElement(sorter)).selectByVisibleText(optionVisibleText); }

    public String firstItemTitle() { return driver.findElement(By.cssSelector(".inventory_item:first-of-type .inventory_item_name")).getText(); }
}
