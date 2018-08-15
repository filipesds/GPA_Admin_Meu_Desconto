package utils;

import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class Utils {
	private static final Logger logger = LogManager.getLogger();
	private WebDriver driver;
	protected ExtentTest extentTest = ExtentReport.getExtentTest();
	protected ExtentReports extent = ExtentReport.getExtent();

	public Utils(WebDriver driver) {
		this.driver = driver;
	}

	public void realizarScroll(WebElement webElement) throws InterruptedException{
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", webElement);
		logger.info("Realizou o scroll na tela!");
	}
	
	public static boolean ehFinalDeSemana() {
		Calendar data = Calendar.getInstance();
		if (data.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY  || data.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			
			return true;
		} 
		else {
			
			return false;
		}
	}
	
	private String saveScreenshotInRelatoriosPath(String log){
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		String datahora = ""+day+month+year+"_"+hours+minutes+seconds;
		int maxLength = log.length() < 35 ? log.length() : 35;
		String screenShotName = log.toLowerCase().replace(" ", "-").replace("/", "-").replace(":", "-").replace("'", "").replace("<", "").replace(">", "").substring(0, maxLength) + "_" + datahora + ".png";
		try {
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("relatorios/img/" + screenShotName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return screenShotName;
	}
	
	public void logPrint(String log) {
		String screenShotName = saveScreenshotInRelatoriosPath(log);
		try {
			extentTest.pass(log).addScreenCaptureFromPath("../relatorios/img/" + screenShotName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		extent.setTestRunnerOutput("- "+log+"&#013");
		EvidenciasPDF evidenciasPDF = TestRule.getEvidenciasPDF();
		evidenciasPDF.addEvidence(log, driver);
	}
	
	public String logPrintFail(String log) {
		String screenShotName = saveScreenshotInRelatoriosPath(log);
		return screenShotName;
	}
}