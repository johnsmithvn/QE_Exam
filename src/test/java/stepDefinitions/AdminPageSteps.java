package stepDefinitions;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
// [REFACTOR] CŨ:
// import cucumberOption.Hooks;
import cucumberOption.TestContext;
import models.User;
import pages.pageObjects.AdminPageObject;

public class AdminPageSteps {
    WebDriver driver;
    AdminPageObject adminPage;
    // [REFACTOR] MỚI: dữ liệu test do step class giữ (trước đây là field public newUser của AdminPageObject).
    User newUser;

    // [REFACTOR] CŨ: lấy driver static dùng chung qua lời gọi static.
    // public AdminPageSteps() {
    //     driver = Hooks.getAndCloseBrowser();
    //     adminPage = new AdminPageObject(driver);
    //
    // }
    // [REFACTOR] MỚI: picocontainer truyền TestContext của scenario hiện tại vào constructor.
    public AdminPageSteps(TestContext context) {
        driver = context.getDriver();
        adminPage = new AdminPageObject(driver);
    }

    @And("I click on the Save button")
    public void clickOnSaveButton() {
        adminPage.clickOnSaveButton();
    }

    @And("I fill in the add user form with valid data")
    public void fillInTheAddUserFormWithValidData() {
        // [REFACTOR] CŨ: page object tự tạo user với username "FakeUser" + số ngẫu nhiên 0-999.
        // adminPage.enteringValidInformation();
        // [REFACTOR] MỚI: step tạo dữ liệu rồi truyền vào page. Username gắn timestamp (mili giây) nên
        // không trùng giữa các lần chạy; độ dài 21 ký tự, trong giới hạn 5-40 của OrangeHRM.
        newUser = new User("FakeUser" + System.currentTimeMillis(), "Abc@12345", "a", "Admin", "Enabled");
        adminPage.enteringValidInformation(newUser);

    }
    @Then("Verify new user is created successfully")
    public void verifyNewUserIsCreatedSuccessfully() {
        Assert.assertTrue(adminPage.isSuccessMessageDisplayed());
    }
    @And("I search for the new user by username")
    public void iSearchForTheNewUserByUsername() {
        // [REFACTOR] CŨ:
        // adminPage.searchUserByUsername();
        // [REFACTOR] MỚI:
        adminPage.searchUserByUsername(newUser.getUserName());
    }
    @Then("Verify new user is displayed in search results")
    public void verifyNewUserIsDisplayedInSearchResults() {
        Assert.assertTrue(adminPage.verifyNewUserIsDisplayedInSearchResults());
        // [REFACTOR] CŨ: đọc dữ liệu mong đợi từ adminPage.newUser.
        // Assert.assertEquals(adminPage.getValueOnTableCell(1, 2), adminPage.newUser.getUserName());
        // Assert.assertEquals(adminPage.getValueOnTableCell(1, 3), adminPage.newUser.getUserRole());
        // // employee name not middle name in results
        // String employeeNameWithoutMiddle = adminPage.removeMiddleName(adminPage.newUser.getEmployeeName());
        // Assert.assertEquals(adminPage.getValueOnTableCell(1, 4), employeeNameWithoutMiddle);
        // Assert.assertEquals(adminPage.getValueOnTableCell(1, 5), adminPage.newUser.getStatus());
        // [REFACTOR] MỚI: đọc từ newUser của step. Đồng thời đổi thứ tự tham số thành (expected, actual)
        // đúng quy ước JUnit, để khi fail thông báo "expected ... but was ..." không bị ngược.
        Assert.assertEquals(newUser.getUserName(), adminPage.getValueOnTableCell(1, 2));
        Assert.assertEquals(newUser.getUserRole(), adminPage.getValueOnTableCell(1, 3));
        // employee name not middle name in results
        String employeeNameWithoutMiddle = adminPage.removeMiddleName(newUser.getEmployeeName());
        Assert.assertEquals(employeeNameWithoutMiddle, adminPage.getValueOnTableCell(1, 4));
        Assert.assertEquals(newUser.getStatus(), adminPage.getValueOnTableCell(1, 5));
    }

    // [REFACTOR] MỚI: dọn dữ liệu test - xóa user vừa tạo (bảng đang lọc còn đúng dòng của user đó).
    @And("I delete the new user")
    public void iDeleteTheNewUser() {
        adminPage.deleteUserInFirstRow();
        Assert.assertTrue("Delete success message should be displayed", adminPage.isSuccessMessageDisplayed());
    }




}

