package AutomationTest.mission.pages;

import org.openqa.selenium.*;

public class CartPage {
    private final WebDriver driver;
    private final By title = By.cssSelector(".title");

    public CartPage(WebDriver driver) { this.driver = driver; }

    public boolean isLoaded() { return driver.findElement(title).getText().contains("Your Cart"); }

    public void checkout() { driver.findElement(By.id("checkout")).click(); }
}
