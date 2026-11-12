package qeexam.stepdefinitions;

// [REFACTOR] ĐỔI PACKAGE: stepDefinitions -> qeexam.stepdefinitions (tên package Java viết thường toàn bộ, nằm dưới package gốc qeexam).


import org.openqa.selenium.WebDriver;
import qeexam.pages.pageobjects.LoginPageObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
// [REFACTOR] CŨ:
// import cucumberOption.Hooks;
import qeexam.commons.ConfigReader;
import qeexam.hooks.TestContext;

public class LoginPageSteps {
    WebDriver driver;
    LoginPageObject loginPage;

    // [REFACTOR] CŨ: lấy driver static dùng chung qua lời gọi static.
    // public LoginPageSteps() {
    //     driver = Hooks.getAndCloseBrowser();
    //     this.loginPage = new LoginPageObject(driver);
    //
    // }
    // [REFACTOR] MỚI: picocontainer truyền TestContext của scenario hiện tại vào constructor.
    public LoginPageSteps(TestContext context) {
        driver = context.getDriver();
        this.loginPage = new LoginPageObject(driver);
    }

    @Given("Open website url")
    public void openWebsiteUrl() {
        loginPage.open();
    }

    @When("I login with valid credentials")
    public void iLoginWithValidCredentials() {
        // [REFACTOR] CŨ: tài khoản nằm cứng trong code.
        // loginPage.loginToTheWebsite("admin", "admin123");
        // [REFACTOR] MỚI: đọc từ config.properties (ui.username / ui.password), ghi đè được bằng -D.
        loginPage.login(ConfigReader.get("ui.username"), ConfigReader.get("ui.password"));
  
    }

 

 
}
