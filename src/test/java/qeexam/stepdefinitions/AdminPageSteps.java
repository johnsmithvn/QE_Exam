package qeexam.stepdefinitions;

// [REFACTOR] ĐỔI PACKAGE: stepDefinitions -> qeexam.stepdefinitions (tên package Java viết thường toàn bộ, nằm dưới package gốc qeexam).

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
// [REFACTOR] CŨ:
// import cucumberOption.Hooks;
import qeexam.hooks.TestContext;
import qeexam.models.User;
import qeexam.pages.pageobjects.AdminPageObject;

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
    public void clickSaveButton() {
        adminPage.clickSaveButton();
    }

    @And("I fill in the add user form with valid data")
    public void fillInTheAddUserFormWithValidData() {
        // [REFACTOR] CŨ: page object tự tạo user với username "FakeUser" + số ngẫu nhiên 0-999.
        // adminPage.enteringValidInformation();
        // [REFACTOR] MỚI: step tạo dữ liệu rồi truyền vào page. Username gắn timestamp (mili giây) nên
        // không trùng giữa các lần chạy; độ dài 21 ký tự, trong giới hạn 5-40 của OrangeHRM.
        newUser = new User("FakeUser" + System.currentTimeMillis(), "Abc@12345", "a", "Admin", "Enabled");
        adminPage.fillAddUserForm(newUser);

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
        Assert.assertTrue(adminPage.isOnlyOneUserDisplayedInSearchResults());
        // [REFACTOR] CŨ: đọc dữ liệu mong đợi từ adminPage.newUser.
        // Assert.assertEquals(adminPage.getValueOnTableCell(1, 2), adminPage.newUser.getUserName());
        // Assert.assertEquals(adminPage.getValueOnTableCell(1, 3), adminPage.newUser.getUserRole());
        // // employee name not middle name in results
        // String employeeNameWithoutMiddle = adminPage.removeMiddleName(adminPage.newUser.getEmployeeName());
        // Assert.assertEquals(adminPage.getValueOnTableCell(1, 4), employeeNameWithoutMiddle);
        // Assert.assertEquals(adminPage.getValueOnTableCell(1, 5), adminPage.newUser.getStatus());
        // [REFACTOR] MỚI: đọc từ newUser của step. Đồng thời đổi thứ tự tham số thành (expected, actual)
        // đúng quy ước JUnit, để khi fail thông báo "expected ... but was ..." không bị ngược.
        Assert.assertEquals(newUser.getUserName(), adminPage.getTableCellText(1, 2));
        Assert.assertEquals(newUser.getUserRole(), adminPage.getTableCellText(1, 3));
        // employee name not middle name in results
        String employeeNameWithoutMiddle = adminPage.removeMiddleName(newUser.getEmployeeName());
        Assert.assertEquals(employeeNameWithoutMiddle, adminPage.getTableCellText(1, 4));
        Assert.assertEquals(newUser.getStatus(), adminPage.getTableCellText(1, 5));
    }

    // [REFACTOR] MỚI: dọn dữ liệu test - xóa user vừa tạo (bảng đang lọc còn đúng dòng của user đó).
    @And("I delete the new user")
    public void iDeleteTheNewUser() {
        adminPage.deleteUserInFirstRow();
        // Kiểm tra bằng kết quả thật (user không còn trong bảng) thay vì chỉ dựa vào toast, vì toast "Success"
        // của bước tạo user có thể vẫn còn hiển thị.
        Assert.assertTrue("Deleted user should no longer be listed", adminPage.isNoUserDisplayedInSearchResults());
    }




}

