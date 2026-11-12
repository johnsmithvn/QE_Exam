package pages.pageUIs;

public class AdminPageUI {
    public static final String ADD_BUTTON = "//button[text()=' Add ']";
    
    public static final String ADD_USER_TITLE = "//h6[text()='Add User']";

    public static final String DYNAMIC_SELECT_FIELD ="//label[contains(text(),'%s')]/parent::div/following-sibling::div//div[contains(@class,'oxd-select-text-input')]";
    
    public static final String DYNAMIC_INPUT_FIELD ="//label[contains(text(),'%s')]/parent::div/following-sibling::div//input";
    public static final String INPUT_ERROR_MSG = "//label[contains(text(),'%s')]/parent::div/following-sibling::div//span[contains(@class,'oxd-input-field-error-message')]";

    public static final String LIST_BOX_ITEM ="//div[@role='listbox']//span";

    public static final String DYNAMIC_ITEM ="//div[@class='oxd-select-option']//span[text()='%s']";

    public static final String SAVE_BUTTON = "//button[@type='submit']";

    // table ui
    public static final String TABLE_ROW = "//div[@class='oxd-table-body']//div[@role='row']";
    public static final String TABLE_CELL = "(//div[@class='oxd-table-body']//div[@role='row'])[%s]/div[@role='cell'][%s]/div";
    public static final String TABLE_SPINNER = "//div[@class='oxd-table-loader']//div[@class='oxd-loading-spinner']";
    public static final String FILTER_USER_NAME_INPUT = "//div[@class='oxd-table-filter']//label[text()='Username']/parent::div/following-sibling::div/input";
    // [REFACTOR] MỚI: nút xóa (icon thùng rác) ở dòng đầu của bảng, dùng cho bước dọn dữ liệu test.
    public static final String FIRST_ROW_DELETE_BUTTON = "(//div[@class='oxd-table-body']//div[@role='row'])[1]//button[i[contains(@class,'bi-trash')]]";


}
