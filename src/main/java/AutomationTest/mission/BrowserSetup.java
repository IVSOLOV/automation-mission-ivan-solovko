package AutomationTest.mission;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class BrowserSetup {
    private static final ThreadLocal<WebDriver> TL = new ThreadLocal<>();
    private static final Properties P = new Properties();

    static {
        try (InputStream in = BrowserSetup.class.getClassLoader().getResourceAsStream("TestData.properties")) {
            if (in != null) P.load(in);
        } catch (Exception ignored) {}
    }

    public static String prop(String key, String def) {
        return System.getProperty(key, P.getProperty(key, def));
    }

    public static WebDriver getDriver() {
        if (TL.get() == null) {
            String browser = prop("Browser", "chrome").toLowerCase();
            boolean headless = Boolean.parseBoolean(prop("Headless", "false"));
            int iw = Integer.parseInt(prop("ImplicitWait", "5"));
            WebDriver d;
            switch (browser) {
                case "firefox":
                    FirefoxOptions ff = new FirefoxOptions();
                    if (headless) ff.addArguments("-headless");
                    d = new FirefoxDriver(ff); break;
                case "edge":
                    EdgeOptions eo = new EdgeOptions();
                    d = new EdgeDriver(eo); break;
                default:
                    ChromeOptions co = new ChromeOptions();
                    if (headless) co.addArguments("--headless=new");
                    d = new ChromeDriver(co); break;
            }
            d.manage().timeouts().implicitlyWait(Duration.ofSeconds(iw));
            d.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            d.manage().window().maximize();
            TL.set(d);
        }
        return TL.get();
    }

    public static void quitDriver() {
        WebDriver d = TL.get();
        if (d != null) { d.quit(); TL.remove(); }
    }
}
