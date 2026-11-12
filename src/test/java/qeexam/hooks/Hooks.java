package qeexam.hooks;

// [REFACTOR] ĐỔI PACKAGE: cucumberOption -> qeexam.hooks (tên package Java viết thường toàn bộ, nằm dưới package gốc qeexam).

import java.util.logging.Logger;
// [REFACTOR] CŨ: các import chỉ phục vụ code đã được thay thế (phần tạo Chrome chuyển sang TestContext).
// import java.time.Duration;
// import java.util.HashMap;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.chrome.ChromeOptions;
//
// import io.github.bonigarcia.wdm.WebDriverManager;
//
// import commons.GlobalConstants;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;


public class Hooks {
	// [REFACTOR] CŨ: một driver static dùng chung cho mọi scenario, chỉ đóng khi JVM thoát (shutdown hook).
	// Các step class lấy driver bằng cách gọi thẳng Hooks.getAndCloseBrowser() trong constructor.
	// Lưu ý: TestRunner cũ khai báo glue = "stepDefinitions" nên Cucumber không hề nạp @Before này làm hook;
	// trình duyệt mở được chỉ nhờ các lời gọi static đó.
	//
	// private static WebDriver driver;
	private static final Logger log = Logger.getLogger(Hooks.class.getName());
	//
	// @Before
	// public synchronized static WebDriver getAndCloseBrowser() {
	//
	// 	if (driver == null) {
	//
	// 		try {
	// 			WebDriverManager.chromedriver().setup();
	// 			System.setProperty("webdriver.chrome.silentOutput", "true");
	//
	// 			ChromeOptions options = new ChromeOptions();
	// 			options.addArguments("--disable-notifications");
	// 			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
	// 			chromePrefs.put("profile.default_content_settings.popups", 0);
	// 			options.setExperimentalOption("prefs", chromePrefs);
	// 			driver = new ChromeDriver(options);
	//
	// 		} finally {
	// 			Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
	// 		}
	//
	// 		// driver.get(GlobalConstants.PAGE_URL);
	// 		driver.manage().window().maximize();
	// 		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.SHORT_TIMEOUT));
	// 		log.info("------------- Started the browser -------------");
	// 	}
	// 	return driver;
	// }
	//
	// private static class BrowserCleanup implements Runnable {
	// 	@Override
	// 	public void run() {
	// 		driver.manage().deleteAllCookies();
	// 		driver.quit();
	// 		driver = null;
	// 		log.info("------------- Closed the browser -------------");
	// 	}
	// }

	// [REFACTOR] MỚI: driver nằm trong TestContext (mỗi scenario một instance, do picocontainer inject).
	// Hook chỉ chạy cho scenario có tag @ui: mở trình duyệt trước scenario, đóng sau scenario.
	// Tại sao:
	// - Mỗi scenario một phiên trình duyệt sạch -> scenario độc lập nhau, chạy song song an toàn.
	// - Đóng trình duyệt trong @After thay vì shutdown hook -> không dồn Chrome đến cuối lần chạy, và nếu
	//   tạo driver lỗi thì không còn bị NullPointerException trong BrowserCleanup che mất lỗi gốc.
	// - Bỏ implicit wait: page object đã dùng explicit wait (BasePage). Trộn hai loại làm thời gian chờ
	//   cộng dồn khó đoán, và implicit wait che các chỗ thiếu wait.
	private final TestContext context;

	public Hooks(TestContext context) {
		this.context = context;
	}

	@Before("@ui")
	public void startBrowser() {
		context.getDriver();
		log.info("------------- Started the browser -------------");
	}

	@After("@ui")
	public void closeBrowser(Scenario scenario) {
		if (!context.hasDriver()) {
			return;
		}
		try {
			// Scenario fail thì đính kèm ảnh màn hình vào report để biết trang đang ở trạng thái nào.
			if (scenario.isFailed()) {
				byte[] screenshot = ((TakesScreenshot) context.getDriver()).getScreenshotAs(OutputType.BYTES);
				scenario.attach(screenshot, "image/png", scenario.getName());
			}
		} finally {
			context.quitDriver();
			log.info("------------- Closed the browser -------------");
		}
	}

}
