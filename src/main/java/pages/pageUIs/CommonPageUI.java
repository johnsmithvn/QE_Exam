package pages.pageUIs;

public class CommonPageUI {

    public static final String DASHBOARD_HEADER = "//h1[text()='Dashboard']";

    public static final String USER_DROPDOWN = "//p[@class='oxd-userdropdown-name']";

    public static final String DYNAMIC_MENU_ITEM ="//aside//a[@class='oxd-main-menu-item']//span[text()='%s']";

    // [REFACTOR] CŨ: so khớp chính xác text node, nên feature phải viết " Add " kèm khoảng trắng của HTML.
    // public static final String DYNAMIC_BUTTON = "//button[text()='%s']";
    // [REFACTOR] MỚI: normalize-space() bỏ khoảng trắng đầu/cuối, feature chỉ cần viết "Add", "Search".
    public static final String DYNAMIC_BUTTON = "//button[normalize-space()='%s']";

    public static final String TOAST_MSG = "//div[contains(@class,'oxd-toast--success')]";


    
}
