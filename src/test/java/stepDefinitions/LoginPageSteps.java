package stepDefinitions;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import pages.pageObjects.LoginPageObject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumberOption.Hooks;

public class LoginPageSteps {
    WebDriver driver;
    LoginPageObject loginPage;

    public LoginPageSteps() {
        driver = Hooks.getAndCloseBrowser();
        this.loginPage = new LoginPageObject(driver);

    }

    @Given("Open website url")
    public void openWebsiteUrl() {
        loginPage.goToPage();
    }

    @When("I login with valid credentials")
    public void iLoginWithValidCredentials() {
        loginPage.loginToTheWebsite("admin", "admin123");
  
    }

 

 
}
