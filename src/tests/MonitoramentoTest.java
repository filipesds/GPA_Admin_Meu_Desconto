package tests;

import org.junit.Test;
import pages.LoginPage;
import pages.ProdutoPage;
import setup.Setup;

public class MonitoramentoTest extends Setup {

	@Test
	public void realizarBuscaPorData(){
		LoginPage loginPage = new LoginPage(driver);
		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
		ProdutoPage produtoPage = new ProdutoPage(driver);

		//continuar desenvolvimento
	}
}