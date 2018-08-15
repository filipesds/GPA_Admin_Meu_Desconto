package tests;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import utils.PropertiesGPA;
import utils.RelatorioUtils;
import utils.TestRule;

public class RelatorioTest {
	private static WebDriver driver;

	@Rule 
	public TestRule testRule = new TestRule(driver);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void enviarRelatorio() throws Exception {
		testRule.setTestCase("Test Case: Envio relatório de falhas.");

		//Le o arquivo de propriedades
		PropertiesGPA prop = new PropertiesGPA();
		boolean enviarEmail = Boolean.parseBoolean( prop.getSeleniumProperties("send.email") );

		//Envia email com print anexo se estiver setado no arquivo de propriedades:
		if (enviarEmail) {
			RelatorioUtils.enviarRelatorioFalhas();
		}
		assertTrue(true);
	}
}