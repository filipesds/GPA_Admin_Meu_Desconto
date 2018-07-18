package tests;

import org.junit.Test;
import pages.LoginPage;
import pages.PromocoesPage;
import setup.Setup;
import utils.GeradorDeDados;

import static junit.framework.TestCase.assertTrue;

public class PromocoesTest extends Setup {

	@Test
	public void pesquisarTodasPromocoes() throws InterruptedException {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
		PromocoesPage promocoesPage = new PromocoesPage(driver);
		promocoesPage.clicarAbaPromocoes();
		promocoesPage.clicarBotaoPesquisarTodas();

		assertTrue("Ocorreu um erro ao tentar realizar busca por todas as promoções!", promocoesPage.validarPesquisarTodasPromocoes());
	}

	@Test
	public void pesquisarPromocaoPorCodigo()throws InterruptedException{
		LoginPage loginPage = new LoginPage(driver);
		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
		PromocoesPage promocoesPage = new PromocoesPage(driver);
		promocoesPage.clicarAbaPromocoes();
		promocoesPage.clicarCampoCodigoPromocao();
		String promocaoSelecionada = GeradorDeDados.selecionaCodigoPromocaoAleatoriamente();
		promocoesPage.informarCodigoPromocao(promocaoSelecionada);

		assertTrue("Ocorreu um erro ao tentar realizar busca por promoção através do código!", promocoesPage.validarPesquisarPromocoesPorCodigo(promocaoSelecionada));
	}
	@Test
	public void pesquisarPromocaoRegionalizada() throws InterruptedException{
		LoginPage loginPage = new LoginPage(driver);
		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
		PromocoesPage promocoesPage = new PromocoesPage(driver);
		promocoesPage.clicarAbaPromocoes();
		promocoesPage.clicarCampoEstados();
		String estadoSelecionado = GeradorDeDados.selecionaEstadoAleatoriamente();
		promocoesPage.informarEstado(estadoSelecionado);

		assertTrue("Ocorreu um erro ao tentar realizar busca por promoção através de estado!",promocoesPage.validarPesquisaPorEstado());
	}

	@Test
	public void pesquisarPromocaoPorCodigoProduto() throws InterruptedException{
		LoginPage loginPage = new LoginPage(driver);
		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
		PromocoesPage promocoesPage = new PromocoesPage(driver);
		promocoesPage.clicarAbaPromocoes();
		promocoesPage.clicarCampoCodigoProdutoPromocao();
		String produtoSelecionado = GeradorDeDados.selecionaCodigoProdutoPromocaoAleatoriamente();
		promocoesPage.informarCodigoProdutoPromocao(produtoSelecionado);

		//  assertTrue("",);
	}

	@Test
	public void pesquisarPromocaoPorDescricao() throws InterruptedException{
		LoginPage loginPage = new LoginPage(driver);
		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
		PromocoesPage promocoesPage = new PromocoesPage(driver);
		promocoesPage.clicarAbaPromocoes();
		promocoesPage.clicarCampoDescricaoPromocao();
		String produtoSelecionado = GeradorDeDados.selecionaDescricaoProdutoPromocaoAleatoriamente();
		promocoesPage.informarDescricaoProdutoPromocao(produtoSelecionado);

		//assertTrue("",);
	}

//	@Test
//	public void pesquisarPromocaoPorData() throws InterruptedException{
//		LoginPage loginPage = new LoginPage(driver);
//		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
//		PromocoesPage promocoesPage = new PromocoesPage(driver);
//		promocoesPage.clicarAbaPromocoes();
//
//		//assertTrue("",);
//
//	}
//
//	@Test
//	public void desativarPromocao() throws InterruptedException{
//		LoginPage loginPage = new LoginPage(driver);
//		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
//		PromocoesPage promocoesPage = new PromocoesPage(driver);
//		promocoesPage.clicarAbaPromocoes();
//
//		//assertTrue("",);
//
//	}
//
//	//    @Test
//	//    public void reativarPromocao() throws InterruptedException{
//	//        LoginPage loginPage = new LoginPage(driver);
//	//        loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
//	//        PromocoesPage promocoesPage = new PromocoesPage(driver);
//	//        promocoesPage.clicarAbaPromocoes();
//	//    }
//
//	@Test
//	public void visualizarProdutosFamiliaProdutos() throws InterruptedException{
//		LoginPage loginPage = new LoginPage(driver);
//		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
//		PromocoesPage promocoesPage = new PromocoesPage(driver);
//		promocoesPage.clicarAbaPromocoes();
//
//		//assertTrue("",);
//	}
//
//	@Test
//	public void editarPromocao() throws InterruptedException{
//		LoginPage loginPage = new LoginPage(driver);
//		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
//		PromocoesPage promocoesPage = new PromocoesPage(driver);
//		promocoesPage.clicarAbaPromocoes();
//
//	}
}