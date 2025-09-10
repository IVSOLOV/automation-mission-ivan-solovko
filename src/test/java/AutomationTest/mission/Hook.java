package AutomationTest.mission;

import io.cucumber.java.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class Hook {

  /** Shared RequestSpecification for API scenarios */
  public static RequestSpecification apiSpec;

  // ========================== UI HOOKS ==========================
  @Before(value = "@ui", order = 0)
  public void setUpUi() {
    BrowserSetup.getDriver();
  }

  @After(value = "@ui", order = 0)
  public void tearDownUi() {
    BrowserSetup.quitDriver();
  }

  // ========================== API HOOKS ==========================
  @Before(value = "@api", order = 0)
  public void setUpApi() {
    // hard-disable proxies
    System.clearProperty("http.proxyHost");
    System.clearProperty("http.proxyPort");
    System.clearProperty("https.proxyHost");
    System.clearProperty("https.proxyPort");
    System.clearProperty("http.proxyUser");
    System.clearProperty("http.proxyPassword");
    System.setProperty("java.net.useSystemProxies", "false");
    proxy = null;

    // reset RA and enable auto logging on failures
    reset();
    config = config().logConfig(
        LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()
    );

    String baseUri = System.getProperty("BASE_URI", "https://reqres.in");

    RequestSpecBuilder b = new RequestSpecBuilder()
        .setBaseUri(baseUri)
        .setBasePath("/api")
        .addHeader("Content-Type", "application/json")
        .log(LogDetail.ALL);

    // Only add x-api-key if a REAL key is supplied
    String apiKey = System.getenv("REQRES_API_KEY");
    if (apiKey == null || apiKey.isBlank()) {
      apiKey = System.getProperty("reqres.api.key", "");
    }
    if (apiKey != null) {
      String k = apiKey.trim();
      if (!k.isEmpty()
          && !k.equalsIgnoreCase("YOUR_KEY_HERE")
          && !k.equalsIgnoreCase("dummy")
          && !k.equalsIgnoreCase("placeholder")) {
        b.addHeader("x-api-key", k);
      }
    }

    apiSpec = b.build();
  }

  /**
   * If a scenario is tagged to require a key, skip when none is provided.
   * Apply this to any scenario that hits an endpoint which 401/403s without a key.
   */
  @Before(value = "@requiresKey or @requiresWrite", order = 1)
  public void requireApiKey() {
    String apiKey = System.getenv("REQRES_API_KEY");
    if (apiKey == null || apiKey.isBlank()) {
      apiKey = System.getProperty("reqres.api.key", "");
    }
    if (apiKey == null || apiKey.isBlank()
        || apiKey.equalsIgnoreCase("YOUR_KEY_HERE")
        || apiKey.equalsIgnoreCase("dummy")
        || apiKey.equalsIgnoreCase("placeholder")) {
      throw new org.testng.SkipException("REQRES_API_KEY not set; skipping scenario that requires API key.");
    }
  }

  @After(value = "@api", order = 0)
  public void tearDownApi() {
    reset();
  }
}
