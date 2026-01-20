package stepDefinitions;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import cucumberOption.Hooks;
import pages.pageObjects.AdminPageObject;

public class AdminPageSteps {
    WebDriver driver;
    AdminPageObject adminPage;

    public AdminPageSteps() {
        driver = Hooks.getAndCloseBrowser();   
        adminPage = new AdminPageObject(driver);

    }   

    @And("I click on the Save button")
    public void clickOnSaveButton() {
        adminPage.clickOnSaveButton();
    }

    @And("I fill in the add user form with valid data")
    public void fillInTheAddUserFormWithValidData() {
        adminPage.enteringValidInformation();

    }
    @Then("Verify new user is created successfully")
    public void verifyNewUserIsCreatedSuccessfully() {
        Assert.assertTrue(adminPage.isSuccessMessageDisplayed());
    }
    @And("I search for the new user by username")
    public void iSearchForTheNewUserByUsername() {
        adminPage.searchUserByUsername();
    }
    @Then("Verify new user is displayed in search results")
    public void verifyNewUserIsDisplayedInSearchResults() {
        Assert.assertTrue(adminPage.verifyNewUserIsDisplayedInSearchResults());
        Assert.assertEquals(adminPage.getValueOnTableCell(1, 2), adminPage.newUser.getUserName());
        Assert.assertEquals(adminPage.getValueOnTableCell(1, 3), adminPage.newUser.getUserRole());
        Assert.assertEquals(adminPage.getValueOnTableCell(1, 4), adminPage.newUser.getEmployeeName());
        Assert.assertEquals(adminPage.getValueOnTableCell(1, 5), adminPage.newUser.getStatus());
      
    }

   


}

