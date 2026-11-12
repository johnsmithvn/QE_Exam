package pages.pageObjects;

import org.openqa.selenium.WebDriver;

// [REFACTOR] CŨ:
// import commons.CommonFuntions;
// import commons.GlobalConstants;
import commons.BasePage;
import commons.ConfigReader;
import pages.pageUIs.CommonPageUI;
import pages.pageUIs.LoginPageUI;

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

    public void goToPage(){
        // [REFACTOR] CŨ: URL lấy từ hằng số trong code.
        // page.get(GlobalConstants.PAGE_URL);
        // [REFACTOR] MỚI: URL lấy từ config.properties (key ui.url), ghi đè được bằng -Dui.url=...
        driver.get(ConfigReader.get("ui.url"));

    }

    public void inputToUsernameTextbox(String userName) {
        // [REFACTOR] CŨ:
        // waitToElementVisible(page, LoginPageUI.USERNAME_INPUT);
        // sendkeyToElement(page, LoginPageUI.USERNAME_INPUT, userName);
        // [REFACTOR] MỚI:
        type(LoginPageUI.USERNAME_INPUT, userName);
    }

    public void inputToPasswordTextbox(String password) {
        // [REFACTOR] CŨ:
        // waitToElementVisible(page, LoginPageUI.PASSWORD_INPUT);
        // sendkeyToElement(page, LoginPageUI.PASSWORD_INPUT, password);
        // [REFACTOR] MỚI:
        type(LoginPageUI.PASSWORD_INPUT, password);
    }

    public void clickToLoginButton() {
        // [REFACTOR] CŨ:
        // waitToElementClickable(page, LoginPageUI.LOGIN_BUTTON);
        // clickToElement(page, LoginPageUI.LOGIN_BUTTON);
        // [REFACTOR] MỚI:
        click(LoginPageUI.LOGIN_BUTTON);
    }

    public void loginToTheWebsite(String userName, String password) {
        inputToUsernameTextbox(userName);
        inputToPasswordTextbox(password);
        clickToLoginButton();
        // [REFACTOR] CŨ:
        // waitToElementVisible(page, CommonPageUI.USER_DROPDOWN);
        // [REFACTOR] MỚI:
        waitForVisible(CommonPageUI.USER_DROPDOWN);
    }

}
