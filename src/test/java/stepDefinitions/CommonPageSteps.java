package stepDefinitions;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
// [REFACTOR] CŨ:
// import cucumberOption.Hooks;
import cucumberOption.TestContext;
import pages.pageObjects.CommonPage;

public class CommonPageSteps {
    WebDriver driver;
    CommonPage commonPage;
    
    // [REFACTOR] CŨ: lấy driver static dùng chung qua lời gọi static.
    // public CommonPageSteps() {
    //     driver = Hooks.getAndCloseBrowser();   
    //     commonPage = new CommonPage(driver);
    // }
    // [REFACTOR] MỚI: picocontainer truyền TestContext của scenario hiện tại vào constructor.
    public CommonPageSteps(TestContext context) {
        driver = context.getDriver();
        commonPage = new CommonPage(driver);
    }

    @And("^I click on the \"([^\"]*)\" item on sidebar menu$")
    public void clickOntheMenuItem(String menuName) {
        commonPage.clickOntheMenuItem(menuName);
        
    }

    @And("^I click on the \"([^\"]*)\" button$")
    public void iClickOnButton(String buttonName) {
        commonPage.clickOnButton(buttonName);
    }

}
