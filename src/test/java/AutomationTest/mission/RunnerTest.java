package AutomationTest.mission;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
  features = "src/test/resources/features",
  glue = {"AutomationTest.mission", "AutomationTest.mission.steps"},
  plugin = {"pretty", "html:target/cucumber.html", "json:target/cucumber.json"},
  tags = "@all"
)
public class RunnerTest extends AbstractTestNGCucumberTests { }
