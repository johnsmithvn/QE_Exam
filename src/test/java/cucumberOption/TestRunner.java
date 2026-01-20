package cucumberOption;

import io.cucumber.junit.Cucumber;


import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/", glue = "stepDefinitions", monochrome = true, dryRun = false, plugin = {
                "pretty", "html:target/cucumber-html-report",
                "json:target/site/test.json" },
                tags = "@exam")

public class TestRunner {

}
