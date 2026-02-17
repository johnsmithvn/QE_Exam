package cucumberOption;

import io.cucumber.junit.Cucumber;


import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/", glue = "stepDefinitions", monochrome = true, dryRun = false, plugin = {
                "pretty", "html:target/report", // day la folder
                "json:target/site/sua-ten.json" },
                tags = "@exam_01")

public class TestRunner {

}
