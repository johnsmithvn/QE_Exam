package pages.pageObjects;

import org.openqa.selenium.WebDriver;

import commons.CommonFuntions;
import pages.pageUIs.CommonPageUI;

public class CommonPage extends CommonFuntions {

    WebDriver page;
    
    public CommonPage(WebDriver driver) {
        page = driver;
    }

    public void clickOntheMenuItem( String menuName) {
        waitToElementVisible(page,CommonPageUI.DYNAMIC_MENU_ITEM, menuName);
        clickToElement(page, CommonPageUI.DYNAMIC_MENU_ITEM, menuName);
    }
    
    public void clickOnButton(String buttonName) {
		waitToElementVisible(page, CommonPageUI.DYNAMIC_BUTTON, buttonName);
        waitToElementClickable(page, CommonPageUI.DYNAMIC_BUTTON, buttonName);
		clickToElement(page, CommonPageUI.DYNAMIC_BUTTON, buttonName);
	}
}

    
    
