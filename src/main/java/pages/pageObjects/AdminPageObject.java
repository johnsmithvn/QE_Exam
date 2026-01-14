package pages.pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import commons.CommonFuntions;
import commons.GlobalConstants;
import pages.pageUIs.AdminPageUI;
import pages.pageUIs.CommonPageUI;

public class AdminPageObject extends CommonFuntions {
    WebDriver page;
    public User newUser;

    public class User {
        String userName;
        String password;
        String employeeName;
        String userRole;
        String status;

        public User(String userName, String password, String employeeName, String userRole, String status) {
            this.userName = userName;
            this.password = password;
            this.employeeName = employeeName;
            this.userRole = userRole;
            this.status = status;
        }

        public String getUserName() {
            return userName;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public String getUserRole() {
            return userRole;
        }

        public String getStatus() {
            return status;
        }

    }

    public AdminPageObject(WebDriver driver) {
        page = driver;
    }


    public void selectFromDropdown(String fieldName, String value) {
        // Click drop down menu
        waitToElementVisible(page, AdminPageUI.DYNAMIC_SELECT_FIELD, fieldName);
        clickToElement(page, AdminPageUI.DYNAMIC_SELECT_FIELD, fieldName);

        // Select value from drop down
        waitToElementVisible(page, AdminPageUI.DYNAMIC_ITEM, value);
        clickToElement(page, AdminPageUI.DYNAMIC_ITEM, value);

    }

    public void sendkeyToInputField(String fieldName, String valueInput) {
        waitToElementVisible(page, AdminPageUI.DYNAMIC_INPUT_FIELD, fieldName);
        sendkeyToElement(page, AdminPageUI.DYNAMIC_INPUT_FIELD, valueInput, fieldName);
    }

    public String selectEmployeeFromListBox() {

        sendkeyToInputField("Employee Name", "a");

        waitToElementVisible(page, AdminPageUI.LIST_BOX_ITEM);

        WebDriverWait wait = new WebDriverWait(page, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(AdminPageUI.LIST_BOX_ITEM), 0));

        List<WebElement> allItems = page.findElements(By.xpath(AdminPageUI.LIST_BOX_ITEM));
        String item = allItems.get(generateRandomNumber(allItems.size())).getText();
        for (WebElement listItem : allItems) {
            if (listItem.getText().equals(item)) {
                listItem.click();
                break;
            }
        }
        return item;

    }

    public void enteringValidInformation() {

        newUser = new User("FakeUser" + generateRandomNumber(1000), "Abc@12345", "a", "Admin", "Enabled");
        waitToElementVisible(page, AdminPageUI.ADD_USER_TITLE);
        // Select User Role
        selectFromDropdown("User Role", newUser.userRole);
        // Input Employee Name
        String employeeName = selectEmployeeFromListBox();
        newUser.employeeName = employeeName;
        // Select Status
        selectFromDropdown("Status", newUser.status);
        // Input Username
        sendkeyToInputField("Username", newUser.userName);
        // wait for input error message disappear
        waitToElementInvisible(page, AdminPageUI.INPUT_ERROR_MSG, "Username");
        // Input Password
        sendkeyToInputField("Password", newUser.password);
        // Input Confirm Password
        sendkeyToInputField("Confirm Password", newUser.password);
    }

    public void clickOnSaveButton() {
        waitToElementClickable(page, AdminPageUI.SAVE_BUTTON);
        clickToElement(page, AdminPageUI.SAVE_BUTTON);
    }

    public boolean isSuccessMessageDisplayed() {
        return page.findElement(By.xpath(CommonPageUI.TOAST_MSG)).isDisplayed();
    }

    public void searchUserByUsername() {
        waitToElementVisible(page, AdminPageUI.FILTER_USER_NAME_INPUT);
        sendkeyToElement(page, AdminPageUI.FILTER_USER_NAME_INPUT, newUser.userName);
        clickToElement(page, CommonPageUI.DYNAMIC_BUTTON, " Search ");
    }

    public boolean verifyNewUserIsDisplayedInSearchResults() {

        waitToElementInvisible(page, AdminPageUI.TABLE_SPINNER);
        List<WebElement> allRows = page.findElements(By.xpath(AdminPageUI.TABLE_ROW));
        return allRows.size() == 1;
    }

    public String getAllTableValue() {
        waitToElementInvisible(page, AdminPageUI.TABLE_SPINNER);
        List<WebElement> allRows = page.findElements(By.xpath(AdminPageUI.TABLE_ROW));
        String allValues = "";
        for (WebElement row : allRows) {
            allValues += row.getText() + "\n";
        }

        return allValues;
    }

    public String getValueOnTableCell(int rowIndex, int cellIndex) {
        String cellLocator = getLocator(AdminPageUI.TABLE_CELL, String.valueOf(rowIndex), String.valueOf(cellIndex));
        waitToElementVisible(page, cellLocator);
        return page.findElement(By.xpath(cellLocator)).getText();

    }
 
}
