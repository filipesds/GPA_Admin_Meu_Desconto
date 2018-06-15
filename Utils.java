package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Utils {
    private WebDriver driver;

    public Utils(WebDriver driver) {
        this.driver = driver;
    }

    public void realizarScroll(WebElement webElement) throws InterruptedException{
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", webElement);
        System.out.println("Realizou o scroll na tela!");
    }
}