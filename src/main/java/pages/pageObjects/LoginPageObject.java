package pages.pageObjects;

import org.openqa.selenium.WebDriver;

import commons.CommonFuntions;
import commons.GlobalConstants;
import pages.pageUIs.CommonPageUI;
import pages.pageUIs.LoginPageUI;

public class LoginPageObject extends CommonFuntions  {

    WebDriver page;

    public LoginPageObject(WebDriver driver) {
        page = driver;
    }

    public void goToPage(){
        page.get(GlobalConstants.PAGE_URL);

    }

    public void inputToUsernameTextbox(String userName) {
        waitToElementVisible(page, LoginPageUI.USERNAME_INPUT);
        sendkeyToElement(page, LoginPageUI.USERNAME_INPUT, userName);
    }

    public void inputToPasswordTextbox(String password) {
        waitToElementVisible(page, LoginPageUI.PASSWORD_INPUT);
        sendkeyToElement(page, LoginPageUI.PASSWORD_INPUT, password);
    }

    public void clickToLoginButton() {
        waitToElementClickable(page, LoginPageUI.LOGIN_BUTTON);
        clickToElement(page, LoginPageUI.LOGIN_BUTTON);
    }

    public void loginToTheWebsite(String userName, String password) {
        inputToUsernameTextbox(userName);
        inputToPasswordTextbox(password);
        clickToLoginButton();
        waitToElementVisible(page, CommonPageUI.USER_DROPDOWN);
    }


    
}
