package commons;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonFuntions {

    public void sleepInSeconds(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getLocator(String locator, String... values) {
        return String.format(locator, (Object[]) values);
    }

    public void waitToElementVisible(WebDriver driver, String locator, String... values) {
        String newLocator = getLocator(locator, values);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(newLocator)));
    }

    public void sendkeyToElement(WebDriver driver, String locator, String valueInput, String... values) {
        String newLocator = getLocator(locator, values);
        driver.findElement(By.xpath(newLocator)).clear();
        driver.findElement(By.xpath(newLocator)).sendKeys(valueInput);
    }

    public void waitToElementClickable(WebDriver driver, String locator, String... values) {
        String newLocator = getLocator(locator, values);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(newLocator)));

    }

    public void clickToElement(WebDriver driver, String locator, String... values) {
        String newLocator = getLocator(locator, values);
        driver.findElement(By.xpath(newLocator)).click();
    }

    public int generateRandomNumber(int length) {
        Random rand = new Random();
        return rand.nextInt(length);
    }
}
