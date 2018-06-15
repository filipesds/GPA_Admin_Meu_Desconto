package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.util.concurrent.TimeUnit;

public class WaitTool {
    public static final int DEFAULT_WAIT_4_ELEMENT = 10;

    @Deprecated
    public static WebElement waitForElementPresent(WebDriver driver, By by, int timeOutInSeconds){
        nullifyImplicitWait(driver);
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        resetImplicitWait(driver);

        return element;
    }

    public static WebElement fluentWaitForElementPresent(WebDriver driver, By by, int timeOutInSeconds){
        nullifyImplicitWait(driver);
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        resetImplicitWait(driver);

        return element;
    }

    public static boolean fluentWaitForElementNotPresent(WebDriver driver, By by, int timeOutInSeconds) throws InterruptedException{
        nullifyImplicitWait(driver);
        Thread.sleep(500);
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        boolean isElementInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        Thread.sleep(200);
        resetImplicitWait(driver);

        return isElementInvisible;
    }

    public static boolean isElementPresent(WebDriver driver, By by, int timeOutInSeconds){
        try {
            WaitTool.setImplicitWait(driver, timeOutInSeconds);
            boolean isElementPresent = !driver.findElements(by).isEmpty();
            boolean isElementDisplayed = driver.findElement(by).isDisplayed();
            WaitTool.resetImplicitWait(driver);
            return isElementPresent && isElementDisplayed;
        }
        catch (Exception e) {

            return false;
        }
    }

    public static void nullifyImplicitWait(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    public static void setImplicitWait(WebDriver driver, int waitTime_InSeconds) {
        driver.manage().timeouts().implicitlyWait(waitTime_InSeconds, TimeUnit.SECONDS);
    }

    public static void resetImplicitWait(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_ELEMENT, TimeUnit.SECONDS);
    }
}