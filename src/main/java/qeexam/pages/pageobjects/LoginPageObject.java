package qeexam.pages.pageobjects;

// [REFACTOR] ĐỔI PACKAGE: pages.pageObjects -> qeexam.pages.pageobjects (tên package Java viết thường toàn bộ, nằm dưới package gốc qeexam).

import org.openqa.selenium.WebDriver;

// [REFACTOR] CŨ:
// import commons.CommonFuntions;
// import commons.GlobalConstants;
import qeexam.commons.BasePage;
import qeexam.commons.ConfigReader;
import qeexam.pages.pageuis.CommonPageUI;
import qeexam.pages.pageuis.LoginPageUI;

// [REFACTOR] CŨ:
// public class LoginPageObject extends CommonFuntions  {
//
//     WebDriver page;
//
//     public LoginPageObject(WebDriver driver) {
//         page = driver;
//     }
// [REFACTOR] MỚI: kế thừa BasePage, driver do BasePage giữ. Lý do: xem BasePage.java.
public class LoginPageObject extends BasePage {

    public LoginPageObject(WebDriver driver) {
        super(driver);
    }

    // [REFACTOR] ĐỔI TÊN HÀM: goToPage -> open (ngắn gọn, đọc tự nhiên: loginPage.open()).
    public void open(){
        // [REFACTOR] CŨ: URL lấy từ hằng số trong code.
        // page.get(GlobalConstants.PAGE_URL);
        // [REFACTOR] MỚI: URL lấy từ config.properties (key ui.url), ghi đè được bằng -Dui.url=...
        driver.get(ConfigReader.get("ui.url"));

    }

    // [REFACTOR] ĐỔI TÊN HÀM: inputToUsernameTextbox -> enterUsername (động từ + đối tượng, bỏ chi tiết loại control).
    public void enterUsername(String userName) {
        // [REFACTOR] CŨ:
        // waitToElementVisible(page, LoginPageUI.USERNAME_INPUT);
        // sendkeyToElement(page, LoginPageUI.USERNAME_INPUT, userName);
        // [REFACTOR] MỚI:
        type(LoginPageUI.USERNAME_INPUT, userName);
    }

    // [REFACTOR] ĐỔI TÊN HÀM: inputToPasswordTextbox -> enterPassword (động từ + đối tượng, bỏ chi tiết loại control).
    public void enterPassword(String password) {
        // [REFACTOR] CŨ:
        // waitToElementVisible(page, LoginPageUI.PASSWORD_INPUT);
        // sendkeyToElement(page, LoginPageUI.PASSWORD_INPUT, password);
        // [REFACTOR] MỚI:
        type(LoginPageUI.PASSWORD_INPUT, password);
    }

    // [REFACTOR] ĐỔI TÊN HÀM: clickToLoginButton -> clickLoginButton (thống nhất mẫu click<Tên>Button).
    public void clickLoginButton() {
        // [REFACTOR] CŨ:
        // waitToElementClickable(page, LoginPageUI.LOGIN_BUTTON);
        // clickToElement(page, LoginPageUI.LOGIN_BUTTON);
        // [REFACTOR] MỚI:
        click(LoginPageUI.LOGIN_BUTTON);
    }

    // [REFACTOR] ĐỔI TÊN HÀM: loginToTheWebsite -> login (ngắn gọn: loginPage.login(user, pass)).
    public void login(String userName, String password) {
        enterUsername(userName);
        enterPassword(password);
        clickLoginButton();
        // [REFACTOR] CŨ:
        // waitToElementVisible(page, CommonPageUI.USER_DROPDOWN);
        // [REFACTOR] MỚI:
        waitForVisible(CommonPageUI.USER_DROPDOWN);
    }

}
