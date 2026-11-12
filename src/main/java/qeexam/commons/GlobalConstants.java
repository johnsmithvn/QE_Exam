package qeexam.commons;

// [REFACTOR] ĐỔI PACKAGE: commons -> qeexam.commons (tên package Java viết thường toàn bộ, nằm dưới package gốc qeexam).

public class GlobalConstants {


    // [REFACTOR] CŨ: URL trang test nằm cứng trong code.
    // public static final String PAGE_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    // [REFACTOR] MỚI: chuyển sang key "ui.url" trong src/test/resources/config.properties (đọc bằng ConfigReader)
    // để đổi môi trường không phải sửa code.

    public static final String GITHUB_API_URL = "https://api.github.com";

    public static final int SHORT_TIMEOUT = 5;
    public static final int LONG_TIMEOUT = 30;

}
