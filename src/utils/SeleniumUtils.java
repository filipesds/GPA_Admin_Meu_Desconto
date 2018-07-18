package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.*;
import java.io.File;
import java.io.IOException;

public class SeleniumUtils implements SeleniumInterface {
	private WebDriver driver = null;
	ProjectProperties prop = new ProjectProperties();
	ConfiguraUrl url = new ConfiguraUrl();
	public static ChromeDriverService service;

	public WebDriver getDriver(String browser) throws IOException, InterruptedException {

		if (driver == null) {
			if (browser.equals(GOOGLECHROME)) {
				service = new ChromeDriverService.Builder()
						.usingDriverExecutable(new File(".\\lib\\drivers\\chromedriver.exe"))
						.usingAnyFreePort()
						.build();
				service.start();
				driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
				driver.manage().window().maximize();
				driver.get(url.getURL(prop.getSeleniumProperties("url.site")));
			}
		}
		return driver;
	}
}