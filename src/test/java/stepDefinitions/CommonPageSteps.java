package stepDefinitions;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import cucumberOption.Hooks;
import pages.pageObjects.CommonPage;

public class CommonPageSteps {
    WebDriver driver;
    CommonPage commonPage;
    
    public CommonPageSteps() {
        driver = Hooks.getAndCloseBrowser();   
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
