package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class TestRule extends TestWatcher {
	private WebDriver driver;
	private String errorMensagemThrowableOriginal;
	private StackTraceElement[] errorMensagemDescriptionOriginal;
	private static final Logger logger = LogManager.getLogger();
	private Path logFile;
	private String testCase = "";
	private String methodName;
	private int tentativas = 3;
	private String className;
	private String methodParam = "";
	private String idBugAssociado = "";
	private String nomeClasse = "";
	private Utils utils;
	private static EvidenciasPDF evidenciasPDF;

	public void setTestCase(String testCase){
		this.testCase = testCase;
		this.utils = new Utils(driver);

		ExtentReport.createTest(testCase, getNomeDaClasse());
	}

	public String getNomeDaClasse(){
		String[] partes = nomeClasse.split("\\.");
		String classeExecutando = partes[2].replace(")", "");
		return classeExecutando;
	}

	public static EvidenciasPDF getEvidenciasPDF() {
		return evidenciasPDF;
	}

	public void setMethodParam(String methodParam) {
		this.methodParam = methodParam;
	}

	public void setIdBugAssociado(String idBugAssociado) {
		this.idBugAssociado = idBugAssociado;
	}

	public TestRule(WebDriver driver) {
		this.driver =  driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver =  driver;
	}

	@Override
	public Statement apply(Statement base, Description description) {
		return statement(base, description);
	}

	private Statement statement(final Statement base, final Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				Throwable caughtThrowable = null;
				for (int i = 0; i < tentativas; i++) {
					try {
						starting(description);
						base.evaluate();
						finished(description);
						return;
					} 
					catch (Throwable t) {
						caughtThrowable = t;
						logger.error("TENTATIVA " + (i + 1) + " FALHOU");
						boolean ultimaTentativa = (i + 1) == tentativas;
						if (!ultimaTentativa) {
							try {
								finished(description);
							}
							catch (Exception e) {
								e.printStackTrace();
								logger.error("TENTATIVA DE FINALIZAR DRIVER FALHOU");
							}
						}
					}
				}
				logger.error("Finalizando após " + tentativas + " TENTATIVAS");
				failed(caughtThrowable, description);
				finished(description);
				throw caughtThrowable;
			}
		};
	}

	@Override
	protected void starting(Description description) {
		logger.info("*** Teste sendo iniciado...***");
		this.nomeClasse = description.toString();
		ExtentReport.getExtentReport(description);
		evidenciasPDF = new EvidenciasPDF();
		limparLogFile();
	}  

	@Override
	protected void failed(Throwable e, Description description) {
		logger.error("*** TESTE FALHOU ***");
		logger.error("URL FALHA: " + driver.getCurrentUrl());
		errorMensagemThrowableOriginal = e.getMessage();
		errorMensagemDescriptionOriginal = e.getStackTrace();
		className = description.getClassName();
		methodName = description.getMethodName();
		String testName = description.getMethodName().toString();
		methodParam = methodParam.isEmpty() ? "" : "(" + methodParam + ")";
		String nomePrinterror = "cenario_falhou-"+testName;

		ExtentReport.falhaReport(utils.logPrintFail(nomePrinterror), e);
		evidenciasPDF.setError(e.getLocalizedMessage());

		// Salva/atualiza resumo:
		salvarResumoDaFalha();
		// Salva o print da tela:
		salvarPrint();
		// salva PDF
		salvarPDF();
	}

	private void salvarResumoDaFalha() {
		try {
			File file = new File("logs/resumo.txt");
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			pw.println("<tr valign='top'>");
			pw.println("<td style=' background:#eeeee0;'><a>"+ (testCase.isEmpty() ? "<span style='color: red'>" + "undefined" + "</span>" : testCase)+"</a></td>");
			pw.println("<td style=' background:#eeeee0;'><a>"+ className +"</a></td>");
			if(idBugAssociado.isEmpty()){
				pw.println("<td style=' background:#eeeee0;'><a></a></td>");
			}
			else{
				pw.println("<td style=' background:#eeeee0;'><a href='http://10.128.7.35:8080/tfs/Mobile/Mobile/_workitems#_a=edit&id=" + idBugAssociado + "'>ID:"+ idBugAssociado +"</a></td>");
			}
			pw.println("</tr>");
			pw.close();
		} 
		catch (Exception e) {
			logger.error("ERROR: Não foi possível gravar os dados no arquivo de resumo." + e.getMessage());
		}
	}

	private void salvarPrint() {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File("screenshots", methodName + methodParam + ".png"));
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void salvarPDF() {
		Document document = new Document();
		try {
			new File("anexos/").mkdir();
			PdfWriter.getInstance(document, new FileOutputStream("anexos/" + methodName + methodParam + ".pdf"));
			document.open();
			// adicionando imagem cabecalho
			Image imageCabecalho = Image.getInstance("mailheader/" + "mailheader" + ".png");
			imageCabecalho.scaleToFit(500, 90);
			imageCabecalho.setAlignment(Element.ALIGN_CENTER);
			imageCabecalho.setSpacingAfter(20);
			document.add(imageCabecalho);

			// adicionando paragrafo principal
			Font f = new Font(FontFamily.HELVETICA, 14, Font.BOLD);
			Paragraph p1 = new Paragraph("Resumo do cenário de teste " + "[M"+ new PropertiesGPA().getSeleniumProperties("url.site") + "] ", f);
			p1.setAlignment(Element.ALIGN_CENTER);
			document.add(p1);

			Paragraph p1_1 = new Paragraph(methodName + methodParam, f);
			p1_1.setAlignment(Element.ALIGN_CENTER);
			p1_1.setSpacingAfter(20);
			document.add(p1_1);

			Font f1 = new Font(FontFamily.HELVETICA, 8, Font.BOLD);
			Paragraph p2 = new Paragraph("Test Case: " + (testCase.isEmpty() ? "undefined" : testCase), f1);
			document.add(p2);
			Paragraph p3 = new Paragraph("Test Class: " + className, f1);
			document.add(p3);
			Paragraph p4 = new Paragraph("Test Method: " + methodName + methodParam + " FALHOU", f1);
			p4.setSpacingAfter(10);
			document.add(p4);

			Paragraph p5 = new Paragraph();
			Font f2 = new Font(FontFamily.HELVETICA, 8);
			p5.setFont(f2);
			ElementList list = XMLWorkerHelper.parseToElementList(ajustaLog(), null);
			for (Element element : list) {
				p5.add(element);
			}
			p5.setSpacingAfter(10);
			document.add(p5);

			Paragraph p6 = new Paragraph();
			p6.setFont(f2);
			ElementList list2 = XMLWorkerHelper.parseToElementList(ajustaStackTrace(), null);
			for (Element element : list2) {
				p6.add(element);
			}
			p6.setSpacingAfter(10);
			document.add(p6);

			// adicionando uma imagem
			Image figura = Image.getInstance("screenshots/" + methodName + methodParam + ".png");
			figura.scaleToFit(517, 310);
			document.add(figura);
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		document.close();
	}

	private String ajustaLog() {
		// Le o arquivo de logs
		String log = ArquivoUtils.lerArquivo("logs/test.log");
		String[] logArray = log.split("\n");
		StringBuffer message = new StringBuffer();
		message.append("<h3>Log:</h3>");
		message.append("<span style='font-family: Arial; font-size: 10px'>");
		for (String linha : logArray) {
			if (!linha.contains("[DESTAQUE]")) {
				linha = "[DESTAQUE]" + linha;
			}
			if (!linha.contains("[/DESTAQUE]")) {
				linha = linha + "[/DESTAQUE]";
			}
			linha = escapeHtml(linha);
			linha = linha.replace("[DESTAQUE]", "<span style='color: blue'>");
			linha = linha.replace("[/DESTAQUE]", "</span>");
			message.append("<br><p>" + linha + "</p></br>");
		}
		message.append("</span>");

		return message.toString();
	}

	private String ajustaStackTrace() {
		StringBuffer message = new StringBuffer();
		message.append("<h3>StackTrace:</h3> \n");
		message.append("<span style='color: darkred; font-family: Arial; font-size: 10px'>");
		if (errorMensagemThrowableOriginal == null) {
			errorMensagemThrowableOriginal = "java.lang.NullPointerException";
		}
		message.append("<br><p>"+escapeHtml(errorMensagemThrowableOriginal)+"</p></br>");
		for (StackTraceElement error : errorMensagemDescriptionOriginal) {
			message.append("<br><p>"+escapeHtml(error.toString())+"</p></br>");
		}
		message.append("</span>");

		return message.toString();
	}

	private String escapeHtml(String string) {

		return string.replace("*", "&#42;").replace("<", "&#60;");
	}

	private void limparLogFile(){
		logFile = Paths.get("logs/test.log");
		try { Files.write(logFile, "".getBytes("UTF-8")); }
		catch (IOException e1) { e1.printStackTrace(); }
	}

	@Override
	protected void finished(Description description) {
		logger.info("*** TESTE FINALIZADO ***");

		if (driver != null) {
			logger.info("URL Final : " + driver.getCurrentUrl());
		}
		ProjectProperties prop = new ProjectProperties();
		String seleniumBrowser = prop.getSeleniumProperties("selenium.browser");
		if ("googlechromemobilelog".equals(seleniumBrowser)) {
			if (SeleniumUtils.proxy != null) {
				SeleniumUtils.proxy.stop();
			}
		}
		if (driver != null) {
			driver.close();
			try {
				driver.quit();
			} catch (RuntimeException re) {
				logger.error("Erro 'driver.quit()'.");
			}
		}
		ExtentReport.finalizaExtentTest();
		String[] suiteCenario = description.toString().split("\\(");
		String suite = suiteCenario[1].replace(")", "");
		String[] suitePartes = suite.split("\\.");
		evidenciasPDF.generateEvidenceReport(evidenciasPDF.getEvidenceReport(), suitePartes[2]+"-"+suiteCenario[0].replace(")", ""), 
				"GPA - Meu Desconto", testCase);
	}
}