package AutomationTest.mission.pages;

import org.openqa.selenium.*;

public class LoginPage {
    private final WebDriver driver;
    private final By user = By.id("user-name");
    private final By pass = By.id("password");
    private final By btn = By.id("login-button");

    public LoginPage(WebDriver driver) { this.driver = driver; }

    public void login(String u, String p) {
        driver.findElement(user).clear();
        driver.findElement(user).sendKeys(u);
        driver.findElement(pass).clear();
        driver.findElement(pass).sendKeys(p);
        driver.findElement(btn).click();
    }
}
