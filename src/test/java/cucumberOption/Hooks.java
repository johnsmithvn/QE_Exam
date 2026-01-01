package cucumberOption;

import java.util.logging.Logger;
import java.time.Duration;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

import commons.GlobalConstants;
import cucumber.api.java.Before;

public class Hooks {
	private static WebDriver driver;
	private static final Logger log = Logger.getLogger(Hooks.class.getName());

	@Before
	public synchronized static WebDriver getAndCloseBrowser() {

		if (driver == null) {

			try {
				WebDriverManager.chromedriver().setup();
				System.setProperty("webdriver.chrome.silentOutput", "true");

				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-notifications");
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				options.setExperimentalOption("prefs", chromePrefs);
				driver = new ChromeDriver(options);

			} finally {
				Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
			}

			// driver.get(GlobalConstants.PAGE_URL);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.SHORT_TIMEOUT));
			log.info("------------- Started the browser -------------");
		}
		return driver;
	}

	private static class BrowserCleanup implements Runnable {
		@Override
		public void run() {
			driver.manage().deleteAllCookies();
			driver.quit();
			driver = null;
			log.info("------------- Closed the browser -------------");
		}
	}

}