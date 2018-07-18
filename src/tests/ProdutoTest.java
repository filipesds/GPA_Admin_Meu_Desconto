package tests;

import org.junit.Test;
import pages.LoginPage;
import pages.ProdutoPage;
import setup.Setup;
import utils.GeradorDeDados;
import static org.junit.Assert.assertTrue;

public class ProdutoTest extends Setup {

	@Test
	public void realizarLogout(){
		LoginPage loginPage = new LoginPage(driver);
		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
		ProdutoPage produtoPage = new ProdutoPage(driver);
		produtoPage.clicarBotaoSair();

		assertTrue("Ocorreu algum erro ao tentar realizar o logout!",loginPage.verificaUsuarioDeslogado());
	}

	@Test
	public void realizarBuscaProdutoPorDescricao() throws InterruptedException {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
		ProdutoPage produtoPage = new ProdutoPage(driver);
		produtoPage.clicarCampoBuscaDescricao();
		String descricaoSelecionada = GeradorDeDados.selecionaDescricaoProdutoAleatoriamente();
		produtoPage.informarDescricaoPorBusca(descricaoSelecionada);

		assertTrue("Ocorreu algum erro ao tentar realizar busca por descrição!",produtoPage.validarBuscaProdutoPorDescricao(descricaoSelecionada));
	}

	@Test
	public void realizarBuscaProdutoPorCodigo() throws InterruptedException{
		LoginPage loginPage = new LoginPage(driver);
		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
		ProdutoPage produtoPage = new ProdutoPage(driver);
		produtoPage.clicarCampoBuscaCodigo();
		String codigoSelecionado = GeradorDeDados.selecionaCodigoProdutoAleatoriamente();
		produtoPage.informarCodigoPorBusca(codigoSelecionado);

		assertTrue("Ocorreu algum erro ao tentar realizar busca por descrição!",produtoPage.validarBuscaProdutoPorCodigo(codigoSelecionado));
	}

	@Test
	public void editarProdutoEspecifico(){
		LoginPage loginPage = new LoginPage(driver);
		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
		ProdutoPage produtoPage = new ProdutoPage(driver);
		produtoPage.clicarCampoBuscaCodigo();
		produtoPage.inserirCodigoEspecifico();
		produtoPage.clicarBotaoEditarProduto();
		produtoPage.editarCamposProduto();
		produtoPage.salvarAlteracoesProduto();

		//assert
	}

	@Test
	public void adicionarProduto(){
		LoginPage loginPage = new LoginPage(driver);
		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
		ProdutoPage produtoPage = new ProdutoPage(driver);
		produtoPage.clicarBotaoAdicionarProduto();
		produtoPage.inserirInformacoesNovoProduto();
		String medidaSelecionada = GeradorDeDados.selecionaMedidaAleatoriamente();
		produtoPage.informarMedida(medidaSelecionada);
		produtoPage.salvarProdutoNovo();

		//assert
	}
	
//	@Test
//	public void ExcluirTagsProduto(){
//		LoginPage loginPage = new LoginPage(driver);
//		loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
//		ProdutoPage produtoPage = new ProdutoPage(driver);
//		//continuar}
}