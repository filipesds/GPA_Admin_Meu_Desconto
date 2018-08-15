package tests;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import utils.PropertiesGPA;
import utils.RelatorioUtils;
import utils.TestRule;

public class RelatorioSimplesTest {

	private static WebDriver driver;

	@Rule 
	public TestRule testRule = new TestRule(driver);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void salvarRelatorioSimples() throws Exception {
		testRule.setTestCase("Test Case: Envio relatório de falhas.");

		//Le o arquivo de propriedades
		PropertiesGPA prop = new PropertiesGPA();
		boolean germud = Boolean.parseBoolean( prop.getSeleniumProperties("germud") );

		//salva pdf se estiver setado no arquivo de propriedades:
		if (germud) {
			RelatorioUtils.salvaRelatorioSimplesPDF();
		}else{
			System.out.println("Properts 'germud' nao foi setado ou e nulo no arquivo 'selenium.properties'!");
			assertTrue(false);
		}
		assertTrue(true);
	}
}