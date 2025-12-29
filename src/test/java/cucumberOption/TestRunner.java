package cucumberOption;

import cucumber.api.junit.Cucumber;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/", glue = "stepDefinitions", monochrome = true, dryRun = false, plugin = {
                "pretty", "html:target/cucumber-html-report",
                "json:target/site/test.json" }, snippets = SnippetType.CAMELCASE, tags = "@LoginSetup")

public class TestRunner {

}
