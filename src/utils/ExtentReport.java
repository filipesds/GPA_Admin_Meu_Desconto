package utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.runner.Description;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport {
	private static ExtentReports extent;
	private static ExtentTest test;
	private static ExtentHtmlReporter htmlReporter;

	public static ExtentHtmlReporter getHTMLReporter(Description description){
		if (description.toString().contains("enviarRelatorio")) {
			htmlReporter = new ExtentHtmlReporter("relatorios/enviarRelatorio.html");
		}else{
			htmlReporter = new ExtentHtmlReporter("relatorios/RegressaoMD.html");
		}
		htmlReporter.config().setDocumentTitle("Report Automação CWI Software");
		htmlReporter.config().setEncoding("ISO-8859-1");
		htmlReporter.config().setProtocol(Protocol.HTTPS);
		htmlReporter.config().setReportName("<img src='"+System.getProperty("user.dir")+"/relatorios/logo.png' /> / REPORT DE TESTES AUTOMATIZADOS");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setChartVisibilityOnOpen(false);
		htmlReporter.config().setCSS("nav {padding-left: 4px;}"
				+ ".blue.darken-3 {display: none;}"
				+ ".report-name {width: 203px; padding-left: 5px; font-size: 18px;} "
				+ ".report-name > img {float: left; height: 42px; width: 200px; margin-top: 3px;}"
				+ ".card-panel > pre {white-space: pre-line;}");
		return htmlReporter;		
	}
	
	public static ExtentReports getExtentReport(Description descricaoCenario){
		if (extent != null)
			return extent;
		extent = new ExtentReports();		
		extent.attachReporter(getHTMLReporter(descricaoCenario));
		extent.setSystemInfo("Usuário", System.getProperty("user.name"));
		try {
			extent.setSystemInfo("Host", InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return extent;
	}
	
	public static ExtentTest createTest(String testCase, String nomeClasse){
		test = extent.createTest(testCase);
		test.assignAuthor("CWI Software");
		test.assignCategory(nomeClasse);
    	test.info("<span class='label white-text blue'>TESTE INICIOU</span>");
    	
    	extent.setTestRunnerOutput("</pre><br><b>"+testCase+"</b><pre>");
		return test;
	}
	
	public static ExtentTest getExtentTest(){
		return test;
	}
	
	public static ExtentReports getExtent(){
		return extent;
	}
	
	public static void falhaReport(String nomePrint, Throwable e){
		try {
			test.fail("<span class='label white-text red'>Cenário falhou.</span>").addScreenCaptureFromPath("../relatorios/img/" + nomePrint);
			Throwable t = new Throwable(e.getLocalizedMessage());
			test.fail(t);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		extent.flush();
	}
	
	public static void finalizaExtentTest(){
		test.info("<span class='label white-text blue'>TESTE FINALIZADO</span>");
		extent.flush();
	}
}