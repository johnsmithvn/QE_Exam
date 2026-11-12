package qeexam.pages.pageobjects;

// [REFACTOR] ĐỔI PACKAGE: pages.pageObjects -> qeexam.pages.pageobjects (tên package Java viết thường toàn bộ, nằm dưới package gốc qeexam).

// [REFACTOR] CŨ: các import chỉ phục vụ code đã được thay thế.
// import java.time.Duration;
// import org.openqa.selenium.support.ui.WebDriverWait;
// import commons.CommonFuntions;
// import commons.GlobalConstants;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import qeexam.commons.BasePage;
import qeexam.models.User;
import qeexam.pages.pageuis.AdminPageUI;
import qeexam.pages.pageuis.CommonPageUI;

// [REFACTOR] CŨ:
// public class AdminPageObject extends CommonFuntions {
// [REFACTOR] MỚI: kế thừa BasePage (giữ driver + wait, action tự chờ). Lý do: xem BasePage.java.
public class AdminPageObject extends BasePage {

    // [REFACTOR] CŨ: page object tự giữ driver và giữ luôn dữ liệu test (newUser) dưới dạng field public.
    // WebDriver page;
    // public User newUser;
    //
    // public class User {
    //     String userName;
    //     String password;
    //     String employeeName;
    //     String userRole;
    //     String status;
    //
    //     public User(String userName, String password, String employeeName, String userRole, String status) {
    //         this.userName = userName;
    //         this.password = password;
    //         this.employeeName = employeeName;
    //         this.userRole = userRole;
    //         this.status = status;
    //     }
    //
    //     public String getUserName() {
    //         return userName;
    //     }
    //
    //     public String getEmployeeName() {
    //         return employeeName;
    //     }
    //
    //     public String getUserRole() {
    //         return userRole;
    //     }
    //
    //     public String getStatus() {
    //         return status;
    //     }
    //
    // }
    //
    // public AdminPageObject(WebDriver driver) {
    //     page = driver;
    // }
    // [REFACTOR] MỚI: driver nằm ở BasePage; User tách ra models.User; dữ liệu test do step class giữ và
    // truyền vào qua tham số. Lý do: page object chỉ nên mô tả thao tác trên trang, không giữ state của test.
    public AdminPageObject(WebDriver driver) {
        super(driver);
    }

    public void selectFromDropdown(String fieldName, String value) {
        // [REFACTOR] CŨ: mỗi thao tác phải gọi wait riêng rồi mới click.
        // // Click drop down menu
        // waitToElementVisible(page, AdminPageUI.DYNAMIC_SELECT_FIELD, fieldName);
        // clickToElement(page, AdminPageUI.DYNAMIC_SELECT_FIELD, fieldName);
        //
        // // Select value from drop down
        // waitToElementVisible(page, AdminPageUI.DYNAMIC_ITEM, value);
        // clickToElement(page, AdminPageUI.DYNAMIC_ITEM, value);
        // [REFACTOR] MỚI: click() tự chờ element click được.
        // Click drop down menu
        click(AdminPageUI.DYNAMIC_SELECT_FIELD, fieldName);
        // Select value from drop down
        click(AdminPageUI.DYNAMIC_ITEM, value);
    }

    // [REFACTOR] ĐỔI TÊN HÀM: sendkeyToInputField -> enterTextIntoField (mô tả hành động người dùng thay vì tên API Selenium).
    public void enterTextIntoField(String fieldName, String valueInput) {
        // [REFACTOR] CŨ:
        // waitToElementVisible(page, AdminPageUI.DYNAMIC_INPUT_FIELD, fieldName);
        // sendkeyToElement(page, AdminPageUI.DYNAMIC_INPUT_FIELD, valueInput, fieldName);
        // [REFACTOR] MỚI: type() tự chờ element hiển thị rồi nhập.
        type(AdminPageUI.DYNAMIC_INPUT_FIELD, valueInput, fieldName);
    }

    // [REFACTOR] ĐỔI TÊN HÀM: selectEmployeeFromListBox -> selectRandomEmployee (nói rõ là chọn NGẪU NHIÊN một nhân viên).
    public String selectRandomEmployee() {

        enterTextIntoField("Employee Name", "a");

        // [REFACTOR] CŨ: chờ 2 lần, lấy text của một item ngẫu nhiên rồi lặp lại cả danh sách để tìm
        // item có text đó. Nếu 2 nhân viên trùng tên thì có thể click nhầm item khác với item đã chọn.
        // waitToElementVisible(page, AdminPageUI.LIST_BOX_ITEM);
        //
        // WebDriverWait wait = new WebDriverWait(page, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        // wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(AdminPageUI.LIST_BOX_ITEM), 0));
        //
        // List<WebElement> allItems = page.findElements(By.xpath(AdminPageUI.LIST_BOX_ITEM));
        // String item = allItems.get(generateRandomNumber(allItems.size())).getText();
        // for (WebElement listItem : allItems) {
        //     if (listItem.getText().equals(item)) {
        //         listItem.click();
        //         break;
        //     }
        // }
        // return item;
        // [REFACTOR] MỚI: một lần chờ (tất cả item hiển thị), chọn một item và click đúng item đó.
        List<WebElement> allItems = wait
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(AdminPageUI.LIST_BOX_ITEM)));
        WebElement chosenItem = allItems.get(randomIndex(allItems.size()));
        String employeeName = chosenItem.getText();
        chosenItem.click();
        return employeeName;

    }

    // [REFACTOR] CŨ: tự tạo dữ liệu test bên trong page object; username chỉ có 1000 giá trị (0-999)
    // nên dễ trùng trên site demo dùng chung.
    // public void enteringValidInformation() {
    //
    //     newUser = new User("FakeUser" + generateRandomNumber(1000), "Abc@12345", "a", "Admin", "Enabled");
    //     waitToElementVisible(page, AdminPageUI.ADD_USER_TITLE);
    //     // Select User Role
    //     selectFromDropdown("User Role", newUser.userRole);
    //     // Input Employee Name
    //     String employeeName = selectEmployeeFromListBox();
    //     newUser.employeeName = employeeName;
    //     // Select Status
    //     selectFromDropdown("Status", newUser.status);
    //     // Input Username
    //     sendkeyToInputField("Username", newUser.userName);
    //     // wait for input error message disappear
    //     waitToElementInvisible(page, AdminPageUI.INPUT_ERROR_MSG, "Username");
    //     // Input Password
    //     sendkeyToInputField("Password", newUser.password);
    //     // Input Confirm Password
    //     sendkeyToInputField("Confirm Password", newUser.password);
    // }
    // [REFACTOR] MỚI: nhận User từ step (step tạo dữ liệu, xem AdminPageSteps). Thứ tự thao tác giữ nguyên.
    // [REFACTOR] ĐỔI TÊN HÀM: enteringValidInformation -> fillAddUserForm (tên hàm nên là động từ nguyên thể và nói rõ form nào).
    public void fillAddUserForm(User user) {
        waitForVisible(AdminPageUI.ADD_USER_TITLE);
        // Select User Role
        selectFromDropdown("User Role", user.getUserRole());
        // Input Employee Name
        user.setEmployeeName(selectRandomEmployee());
        // Select Status
        selectFromDropdown("Status", user.getStatus());
        // Input Username
        enterTextIntoField("Username", user.getUserName());
        // wait for input error message disappear
        waitForInvisible(AdminPageUI.INPUT_ERROR_MSG, "Username");
        // Input Password
        enterTextIntoField("Password", user.getPassword());
        // Input Confirm Password
        enterTextIntoField("Confirm Password", user.getPassword());
    }

    // [REFACTOR] ĐỔI TÊN HÀM: clickOnSaveButton -> clickSaveButton (thống nhất mẫu click<Tên>Button).
    public void clickSaveButton() {
        // [REFACTOR] CŨ:
        // waitToElementClickable(page, AdminPageUI.SAVE_BUTTON);
        // clickToElement(page, AdminPageUI.SAVE_BUTTON);
        // [REFACTOR] MỚI:
        click(AdminPageUI.SAVE_BUTTON);
    }

    public boolean isSuccessMessageDisplayed() {
        // [REFACTOR] CŨ: không có explicit wait, chạy được là nhờ implicit wait 5s (nay đã bỏ).
        // return page.findElement(By.xpath(CommonPageUI.TOAST_MSG)).isDisplayed();
        // [REFACTOR] MỚI: chờ tường minh toast hiển thị; hết thời gian thì trả false để assert báo lỗi rõ ràng.
        return isDisplayed(CommonPageUI.TOAST_MSG);
    }

    // [REFACTOR] CŨ: đọc username từ field newUser của page object.
    // public void searchUserByUsername() {
    //     waitToElementVisible(page, AdminPageUI.FILTER_USER_NAME_INPUT);
    //     sendkeyToElement(page, AdminPageUI.FILTER_USER_NAME_INPUT, newUser.userName);
    //     clickToElement(page, CommonPageUI.DYNAMIC_BUTTON, " Search ");
    // }
    // [REFACTOR] MỚI: nhận username qua tham số; "Search" không cần khoảng trắng vì DYNAMIC_BUTTON
    // đã dùng normalize-space().
    public void searchUserByUsername(String userName) {
        type(AdminPageUI.FILTER_USER_NAME_INPUT, userName);
        click(CommonPageUI.DYNAMIC_BUTTON, "Search");
    }

    // [REFACTOR] ĐỔI TÊN HÀM: verifyNewUserIsDisplayedInSearchResults -> isOnlyOneUserDisplayedInSearchResults
    // (hàm trả về boolean nên đặt tên dạng is...; việc "verify" là của step, không phải của page object).
    public boolean isOnlyOneUserDisplayedInSearchResults() {

        // [REFACTOR] CŨ: chờ spinner biến mất rồi đếm dòng một lần. Nếu spinner chưa kịp hiện sau khi bấm
        // Search thì "đã biến mất" đúng ngay lập tức và đếm phải bảng cũ (chưa lọc). Trước đây implicit wait
        // 5s vô tình che lỗi này.
        // waitToElementInvisible(page, AdminPageUI.TABLE_SPINNER);
        // List<WebElement> allRows = page.findElements(By.xpath(AdminPageUI.TABLE_ROW));
        // return allRows.size() == 1;
        // [REFACTOR] MỚI: chờ thẳng điều kiện cần kiểm tra - bảng còn đúng 1 dòng.
        return waitForElementCount(1, AdminPageUI.TABLE_ROW);
    }

    // [REFACTOR] ĐỔI TÊN HÀM: getAllTableValue -> getAllTableValues (trả về nhiều giá trị nên dùng số nhiều).
    public String getAllTableValues() {
        // [REFACTOR] CŨ:
        // waitToElementInvisible(page, AdminPageUI.TABLE_SPINNER);
        // List<WebElement> allRows = page.findElements(By.xpath(AdminPageUI.TABLE_ROW));
        // [REFACTOR] MỚI: cùng logic, dùng helper của BasePage.
        waitForInvisible(AdminPageUI.TABLE_SPINNER);
        List<WebElement> allRows = findAll(AdminPageUI.TABLE_ROW);
        String allValues = "";
        for (WebElement row : allRows) {
            allValues += row.getText() + "\n";
        }

        return allValues;
    }

    // [REFACTOR] ĐỔI TÊN HÀM: getValueOnTableCell -> getTableCellText (nói rõ giá trị trả về là text của ô).
    public String getTableCellText(int rowIndex, int cellIndex) {
        // [REFACTOR] CŨ:
        // String cellLocator = getLocator(AdminPageUI.TABLE_CELL, String.valueOf(rowIndex), String.valueOf(cellIndex));
        // waitToElementVisible(page, cellLocator);
        // return page.findElement(By.xpath(cellLocator)).getText();
        // [REFACTOR] MỚI: getText() tự ghép locator, chờ hiển thị rồi lấy text.
        return getText(AdminPageUI.TABLE_CELL, String.valueOf(rowIndex), String.valueOf(cellIndex));

    }

    // [REFACTOR] MỚI: xóa user vừa tạo để test không để lại dữ liệu rác trên site dùng chung.
    // Gọi sau khi đã lọc bảng còn đúng 1 dòng (dòng của user đó).
    public void deleteUserInFirstRow() {
        click(AdminPageUI.FIRST_ROW_DELETE_BUTTON);
        click(CommonPageUI.DYNAMIC_BUTTON, "Yes, Delete");
    }

    public String removeMiddleName(String fullName) {
        String[] name  = fullName.split(" ");
        if (name.length <= 2) {
            return fullName;
        }

        return name[0] + " " + name[name.length - 1];


    }
}
