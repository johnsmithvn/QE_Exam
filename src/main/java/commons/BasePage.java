package commons;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * [REFACTOR] MỚI - thay thế cho commons.CommonFuntions (code cũ vẫn được giữ, đã comment trong file đó).
 *
 * Tại sao:
 * 1. Tên cũ "CommonFuntions" sai chính tả và không nói lên vai trò (lớp cha của các page object).
 * 2. Lớp cũ không giữ driver nên mọi lời gọi phải truyền "page" vào; giờ driver + wait nằm ở đây.
 * 3. Wait và action bị tách rời (waitToElementVisible rồi mới clickToElement), chỗ nào quên wait là
 *    phải dựa vào implicit wait. Giờ mỗi action tự chờ đúng điều kiện của nó.
 * 4. Mỗi lời gọi cũ tạo một WebDriverWait mới; giờ dùng chung một instance.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private final Random random = new Random();

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
    }

    /** Ghép giá trị động vào locator (thay cho getLocator cũ) và trả về By luôn. */
    protected By xpath(String locator, String... values) {
        return By.xpath(String.format(locator, (Object[]) values));
    }

    protected WebElement waitForVisible(String locator, String... values) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(xpath(locator, values)));
    }

    protected void waitForInvisible(String locator, String... values) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(xpath(locator, values)));
    }

    /** Chờ element click được rồi mới click (gộp waitToElementClickable + clickToElement cũ). */
    protected void click(String locator, String... values) {
        wait.until(ExpectedConditions.elementToBeClickable(xpath(locator, values))).click();
    }

    /**
     * Chờ element hiển thị rồi xóa và nhập (gộp waitToElementVisible + sendkeyToElement cũ).
     * Bản cũ gọi findElement 2 lần (một lần clear, một lần sendKeys); giờ chỉ tìm 1 lần.
     */
    protected void type(String locator, String valueInput, String... values) {
        WebElement element = waitForVisible(locator, values);
        element.clear();
        element.sendKeys(valueInput);
    }

    protected String getText(String locator, String... values) {
        return waitForVisible(locator, values).getText();
    }

    /** Trả về true nếu element hiển thị trong LONG_TIMEOUT, false nếu hết thời gian chờ. */
    protected boolean isDisplayed(String locator, String... values) {
        try {
            waitForVisible(locator, values);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Chờ đến khi số element khớp đúng expectedCount; false nếu hết thời gian chờ. */
    protected boolean waitForElementCount(int expectedCount, String locator, String... values) {
        try {
            wait.until(ExpectedConditions.numberOfElementsToBe(xpath(locator, values), expectedCount));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected List<WebElement> findAll(String locator, String... values) {
        return driver.findElements(xpath(locator, values));
    }

    /** Thay cho generateRandomNumber cũ; dùng lại một Random thay vì tạo mới mỗi lần gọi. */
    protected int randomIndex(int bound) {
        return random.nextInt(bound);
    }
}
