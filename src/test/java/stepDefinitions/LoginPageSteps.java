package stepDefinitions;


import org.openqa.selenium.WebDriver;
import pages.pageObjects.LoginPageObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
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
