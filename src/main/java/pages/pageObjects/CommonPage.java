package pages.pageObjects;

import org.openqa.selenium.WebDriver;

// [REFACTOR] CŨ:
// import commons.CommonFuntions;
import commons.BasePage;
import pages.pageUIs.CommonPageUI;

// [REFACTOR] CŨ:
// public class CommonPage extends CommonFuntions {
//
//     WebDriver page;
//
//     public CommonPage(WebDriver driver) {
//         page = driver;
//     }
// [REFACTOR] MỚI: kế thừa BasePage, driver do BasePage giữ. Lý do: xem BasePage.java.
public class CommonPage extends BasePage {

    public CommonPage(WebDriver driver) {
        super(driver);
    }

    public void clickOntheMenuItem( String menuName) {
        // [REFACTOR] CŨ:
        // waitToElementVisible(page,CommonPageUI.DYNAMIC_MENU_ITEM, menuName);
        // clickToElement(page, CommonPageUI.DYNAMIC_MENU_ITEM, menuName);
        // [REFACTOR] MỚI: click() tự chờ element click được.
        click(CommonPageUI.DYNAMIC_MENU_ITEM, menuName);
    }

    public void clickOnButton(String buttonName) {
        // [REFACTOR] CŨ:
        // waitToElementVisible(page, CommonPageUI.DYNAMIC_BUTTON, buttonName);
        // waitToElementClickable(page, CommonPageUI.DYNAMIC_BUTTON, buttonName);
        // clickToElement(page, CommonPageUI.DYNAMIC_BUTTON, buttonName);
        // [REFACTOR] MỚI: "clickable" đã bao gồm "visible" nên chỉ cần một lần chờ.
        click(CommonPageUI.DYNAMIC_BUTTON, buttonName);
    }
}
