package qeexam.stepdefinitions;

// [REFACTOR] ĐỔI PACKAGE: stepDefinitions -> qeexam.stepdefinitions (tên package Java viết thường toàn bộ, nằm dưới package gốc qeexam).

import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
// [REFACTOR] CŨ:
// import cucumberOption.Hooks;
import qeexam.hooks.TestContext;
import qeexam.pages.pageobjects.CommonPageObject;

public class CommonPageSteps {
    WebDriver driver;
    CommonPageObject commonPage;
    
    // [REFACTOR] CŨ: lấy driver static dùng chung qua lời gọi static.
    // public CommonPageSteps() {
    //     driver = Hooks.getAndCloseBrowser();   
    //     commonPage = new CommonPage(driver);
    // }
    // [REFACTOR] MỚI: picocontainer truyền TestContext của scenario hiện tại vào constructor.
    public CommonPageSteps(TestContext context) {
        driver = context.getDriver();
        commonPage = new CommonPageObject(driver);
    }

    @And("^I click on the \"([^\"]*)\" item on sidebar menu$")
    public void clickSidebarMenuItem(String menuName) {
        commonPage.clickSidebarMenuItem(menuName);
        
    }

    @And("^I click on the \"([^\"]*)\" button$")
    public void iClickOnButton(String buttonName) {
        commonPage.clickButton(buttonName);
    }

}
