package qeexam.pages.pageobjects;

// [REFACTOR] ĐỔI PACKAGE: pages.pageObjects -> qeexam.pages.pageobjects (tên package Java viết thường toàn bộ, nằm dưới package gốc qeexam).
// [REFACTOR] ĐỔI TÊN LỚP: CommonPage -> CommonPageObject (thống nhất hậu tố PageObject với LoginPageObject, AdminPageObject).

import org.openqa.selenium.WebDriver;

// [REFACTOR] CŨ:
// import commons.CommonFuntions;
import qeexam.commons.BasePage;
import qeexam.pages.pageuis.CommonPageUI;

// [REFACTOR] CŨ:
// public class CommonPage extends CommonFuntions {
//
//     WebDriver page;
//
//     public CommonPage(WebDriver driver) {
//         page = driver;
//     }
// [REFACTOR] MỚI: kế thừa BasePage, driver do BasePage giữ. Lý do: xem BasePage.java.
public class CommonPageObject extends BasePage {

    public CommonPageObject(WebDriver driver) {
        super(driver);
    }

    // [REFACTOR] ĐỔI TÊN HÀM: clickOntheMenuItem -> clickSidebarMenuItem (sửa lỗi viết hoa (Onthe) và nói rõ là menu ở sidebar).
    public void clickSidebarMenuItem( String menuName) {
        // [REFACTOR] CŨ:
        // waitToElementVisible(page,CommonPageUI.DYNAMIC_MENU_ITEM, menuName);
        // clickToElement(page, CommonPageUI.DYNAMIC_MENU_ITEM, menuName);
        // [REFACTOR] MỚI: click() tự chờ element click được.
        click(CommonPageUI.DYNAMIC_MENU_ITEM, menuName);
    }

    // [REFACTOR] ĐỔI TÊN HÀM: clickOnButton -> clickButton (thống nhất mẫu click<Tên>).
    public void clickButton(String buttonName) {
        // [REFACTOR] CŨ:
        // waitToElementVisible(page, CommonPageUI.DYNAMIC_BUTTON, buttonName);
        // waitToElementClickable(page, CommonPageUI.DYNAMIC_BUTTON, buttonName);
        // clickToElement(page, CommonPageUI.DYNAMIC_BUTTON, buttonName);
        // [REFACTOR] MỚI: "clickable" đã bao gồm "visible" nên chỉ cần một lần chờ.
        click(CommonPageUI.DYNAMIC_BUTTON, buttonName);
    }
}
