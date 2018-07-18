package tests;

import org.junit.Test;
import pages.LoginPage;
import pages.PromocoesClientePage;
import setup.Setup;
import utils.GeradorDeDados;
import static org.junit.Assert.assertTrue;

public class PromocoesClienteTest extends Setup {
	@Test
	public void realizarBuscaPromocoesRegionalizadasCliente(){
		LoginPage loginPage = new LoginPage(driver);
		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
		PromocoesClientePage promocoesClientePage = new PromocoesClientePage(driver);
		promocoesClientePage.clicarAbaPromocoesCliente();
		promocoesClientePage.informarCpf();
		String marcaSelecionada = GeradorDeDados.selecionaMarcaAleatoriamente();
		promocoesClientePage.informarMarca(marcaSelecionada);
		String ufSelecionado = GeradorDeDados.selecionaEstadoAleatoriamente();
		promocoesClientePage.informarEstado(ufSelecionado);
		promocoesClientePage.clicarBotaoPesquisarCliente();

		assertTrue("Ocorreu um erro ao tentar realizar busca por promo��o atrav�s de estado!",promocoesClientePage.validarBuscaPromocaoCliente());
	}

	@Test
	public void realizarBuscaPromocoesNaoRegionalizadasCliente(){
		LoginPage loginPage = new LoginPage(driver);
		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
		PromocoesClientePage promocoesClientePage = new PromocoesClientePage(driver);
		promocoesClientePage.clicarAbaPromocoesCliente();
		promocoesClientePage.informarCpf();

		//continuar cen�rio
	}

	@Test
	public void ativarPromocaoCliente(){
		LoginPage loginPage = new LoginPage(driver);
		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
		PromocoesClientePage promocoesClientePage = new PromocoesClientePage(driver);
		promocoesClientePage.clicarAbaPromocoesCliente();
		promocoesClientePage.informarCpf();

		//continuar cen�rio
	}
}