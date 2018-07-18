package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Utils {
	private static final Logger logger = LogManager.getLogger();
	private WebDriver driver;

	public Utils(WebDriver driver) {
		this.driver = driver;
	}

	public void realizarScroll(WebElement webElement) throws InterruptedException{
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", webElement);
		logger.info("Realizou o scroll na tela!");
	}
}