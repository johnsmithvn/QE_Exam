package qeexam.hooks;

// [REFACTOR] ĐỔI PACKAGE: cucumberOption -> qeexam.hooks (tên package Java viết thường toàn bộ, nằm dưới package gốc qeexam).

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * [REFACTOR] MỚI - giữ WebDriver của MỘT scenario, thay cho field "private static WebDriver driver" trong Hooks.
 *
 * Cách hoạt động: cucumber-picocontainer tạo một TestContext mới cho mỗi scenario và truyền cùng instance
 * đó vào constructor của Hooks và của mọi step class trong scenario.
 *
 * Tại sao:
 * 1. Driver static dùng chung cho mọi scenario làm cookie/trạng thái đăng nhập rò từ scenario này sang
 *    scenario khác, nên kết quả phụ thuộc thứ tự chạy.
 * 2. pom.xml bật chạy song song (parallel=classes); driver static sẽ bị nhiều thread dùng chung.
 * 3. Trình duyệt chỉ mở khi có nơi gọi getDriver(), nên scenario API không mở Chrome.
 */
public class TestContext {

    private WebDriver driver;

    /** Mở Chrome ở lần gọi đầu tiên trong scenario, các lần sau trả về cùng driver đó. */
    public WebDriver getDriver() {
        if (driver == null) {
            driver = createChromeDriver();
        }
        return driver;
    }

    public boolean hasDriver() {
        return driver != null;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    // Phần cấu hình Chrome chuyển nguyên từ Hooks.getAndCloseBrowser() cũ sang.
    private WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.silentOutput", "true");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        options.setExperimentalOption("prefs", chromePrefs);

        // Tham số Chrome bổ sung lúc chạy, cách nhau bằng dấu phẩy. Ví dụ chạy không giao diện trên CI:
        //   mvn test -Dchrome.args=--headless=new,--window-size=1920,1080
        String extraArgs = System.getProperty("chrome.args", "").trim();
        if (!extraArgs.isEmpty()) {
            options.addArguments(extraArgs.split("\\s*,\\s*(?=--)"));
        }

        WebDriver newDriver = new ChromeDriver(options);
        newDriver.manage().window().maximize();
        return newDriver;
    }
}
