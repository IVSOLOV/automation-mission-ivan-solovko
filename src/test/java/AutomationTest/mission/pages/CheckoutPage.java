package AutomationTest.mission.pages;

import org.openqa.selenium.*;

public class CheckoutPage {
    private final WebDriver driver;

    public CheckoutPage(WebDriver driver) { this.driver = driver; }

    public void fillInfo(String fn, String ln, String zip) {
        driver.findElement(By.id("first-name")).sendKeys(fn);
        driver.findElement(By.id("last-name")).sendKeys(ln);
        driver.findElement(By.id("postal-code")).sendKeys(zip);
        driver.findElement(By.id("continue")).click();
    }

    public void finish() { driver.findElement(By.id("finish")).click(); }

    public boolean isComplete() { return driver.findElement(By.cssSelector(".complete-header")).getText().contains("Thank you"); }
}
