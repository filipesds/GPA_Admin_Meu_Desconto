package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.GeradorDeDados;
import utils.Seletores;
import utils.Utils;
import utils.WaitTool;

public class ProdutoPage {
	private static final Logger logger = LogManager.getLogger();
	private WebDriver driver;
	private Utils utils;
	private WebElement botaoSair;
	private WebElement campoDescricao;
	private WebElement botaoPesquisar;
	private WebElement scrollProximaPage;
	private WebElement nomeProdutoDescricao;
	private WebElement campoCodigo;
	private WebElement codigoProduto;
	private WebElement descricaoProduto;
	private WebElement botaoEditar;
	private WebElement campoDescricaoProduto;
	private WebElement campoTag;
	private WebElement botaoSalvar;
	private WebElement modalSalvar;
	private WebElement botaoExcluirTag;
	private WebElement botaoVoltar;
	private WebElement botaoAdicionar;
	private WebElement campoCodigoNovoProduto;
	private WebElement campoDescricaoNovoProduto;
	private WebElement campoMedidaNovoProduto;
	private WebElement campoUrlImagem;
	private WebElement campoTagNovoProduto;
	private WebElement cliqueMedida;
	private WebElement opcaoUnidade;
	private WebElement opcaoKg;
	private WebElement botaoSalvarProdutoNovo;
	private WebElement confirmaCriarProduto;

	public ProdutoPage(WebDriver driver) {
		this.driver = driver;
		this.utils = new Utils(driver);
	}

	public void clicarBotaoSair() {
		botaoSair = driver.findElement(By.cssSelector(Seletores.BOTAOSAIR));
		botaoSair.click();
		logger.info("Clicou no bot�o 'Sair da Aplica��o'.");
	}

	public boolean verificarUsuarioLogado() {
		boolean isLoggedIn = WaitTool.isElementPresent(driver, By.cssSelector(Seletores.BOTAOADICIONARPRODUTO), 5);
		if (isLoggedIn) {
			logger.info("O usu�rio est� logado.");
		} else {
			logger.info("O usu�rio n�o est� logado!");
		}
		return isLoggedIn;
	}

	public void clicarCampoBuscaDescricao() {
		campoDescricao = driver.findElement(By.cssSelector(Seletores.CAMPODESCRICAO));
		campoDescricao.click();
		logger.info("Clicou no campo de descri��o.");
	}

	public void informarDescricaoPorBusca(String descricaoSelecionada) throws InterruptedException {
		String descricao = descricaoSelecionada.isEmpty() ? GeradorDeDados.selecionaDescricaoProdutoAleatoriamente() : descricaoSelecionada;
		campoDescricao.sendKeys(descricao);
		logger.info("Informou o termo '" + descricao + "' para pesquisa.");

		botaoPesquisar = driver.findElement(By.xpath(Seletores.BOTAOPESQUISAR));
		botaoPesquisar.click();
		logger.info("Clicou no bot�o 'Pesquisar'.");

		if(WaitTool.isElementPresent(driver, By.cssSelector(Seletores.SCROLLPROXIMAPAGE),10)){
			scrollProximaPage = driver.findElement(By.cssSelector(Seletores.SCROLLPROXIMAPAGE));
			utils.realizarScroll(scrollProximaPage);
		}
		else{
			logger.info("N�o foi necess�rio realizar scroll na tela para visualizar o resultado da busca.");
		}
	}

	public void informarCodigoPorBusca(String codigoSelecionado)throws InterruptedException{
		String codigo = codigoSelecionado.isEmpty() ? GeradorDeDados.selecionaCodigoProdutoAleatoriamente(): codigoSelecionado;
		campoCodigo.sendKeys(codigo);
		logger.info("Informou o c�digo '" + codigo + "' para pesquisa.");
		campoDescricao = driver.findElement(By.cssSelector(Seletores.CAMPODESCRICAO));
		campoDescricao.click();

		botaoPesquisar = driver.findElement(By.xpath(Seletores.BOTAOPESQUISAR));
		botaoPesquisar.click();
		logger.info("Clicou no bot�o 'Pesquisar'.");
	}

	public boolean validarBuscaProdutoPorDescricao(String descricaoSelecionada){
		boolean pesquisaValida = false;
		nomeProdutoDescricao = driver.findElement(By.cssSelector(Seletores.NOMEPRODUTODESCRICAO));
		String descricaoTermo = nomeProdutoDescricao.getText();
		if(descricaoTermo.toLowerCase().contains(descricaoSelecionada.toLowerCase())){
			logger.info("Pesquisa realizada com sucesso.");
			pesquisaValida = true;
		}
		else{
			logger.info("Pesquisa n�o foi realizada.");
		}
		return pesquisaValida;
	}

	public boolean validarBuscaProdutoPorCodigo(String codigoSelecionado){
		boolean pesquisaValida = false;
		codigoProduto = driver.findElement(By.xpath(Seletores.CODIGOPRODUTO));
		String plu  = codigoProduto.getText().substring(19);
		descricaoProduto = driver.findElement(By.xpath(Seletores.DESCRICAOPRODUTOCODIGO));
		String nomeProduto = descricaoProduto.getText();

		if(plu.equals(codigoSelecionado)){
			logger.info("Pesquisa realizada com sucesso. O produto '" +nomeProduto+ "' foi encontrado para o c�digo '"+plu+"' informado.");
			pesquisaValida = true;
		}
		else{
			logger.info("Pesquisa n�o foi realizada.");
		}
		return pesquisaValida;
	}

	public void clicarCampoBuscaCodigo(){
		campoCodigo = driver.findElement(By.cssSelector(Seletores.CAMPOCODIGO));
		campoCodigo.click();
		logger.info("Clicou no campo de c�digo.");
	}

	public void inserirCodigoEspecifico(){
		campoCodigo = driver.findElement(By.cssSelector(Seletores.CAMPOCODIGO));
		campoCodigo.sendKeys("0000103");
		logger.info("Inseriu o c�digo para busca de produto.");
		campoCodigo.sendKeys(Keys.TAB);
		botaoPesquisar = driver.findElement(By.xpath(Seletores.BOTAOPESQUISAR));
		botaoPesquisar.click();
	}

	public void clicarBotaoEditarProduto(){
		botaoEditar = driver.findElement(By.xpath(Seletores.BOTAOEDITARPRODUTO));
		botaoEditar.click();
		logger.info("Clicou no bot�o 'Editar Produto.");
	}

	public void editarCamposProduto(){
		campoDescricaoProduto = driver.findElement(By.cssSelector(Seletores.EDITARDESCRICAOPROD));
		campoDescricaoProduto.clear();
		campoDescricaoProduto.sendKeys("Produto n�o comercial - Edi��o "+GeradorDeDados.randomizar(90));
		logger.info("Descri��o do produto foi editada.");

		campoTag = driver.findElement(By.cssSelector(Seletores.CAMPOTAGPRODUTO));
		campoTag.sendKeys("Editada "+GeradorDeDados.randomizar(90));
		campoTag.sendKeys(Keys.TAB);
		logger.info("Nova tag inserida.");

		botaoSalvar = driver.findElement(By.xpath(Seletores.CLICARSALVARALTERACOES));
		botaoSalvar.click();
		logger.info("Clicou no bot�o 'Salvar Altera��es'.");
	}

	public void salvarAlteracoesProduto(){
		modalSalvar = driver.findElement(By.cssSelector(Seletores.CLICARSALVARMODAL));
		modalSalvar.click();
		logger.info("Clicou no bot�o 'Salvar' na modal de altera��es.");
	}

	public void excluirTag(){
		botaoExcluirTag = driver.findElement(By.cssSelector(Seletores.EXCLUIRTAG));
		botaoExcluirTag.click();
		logger.info("Clicou no bot�o para excluir tag.");
	}

	public void clicarBotaoVoltar(){
		botaoVoltar = driver.findElement(By.xpath(Seletores.BOTAOVOLTAR));
		botaoVoltar.click();
		logger.info("Clicou no bot�o 'Voltar'.");
	}

	public void clicarBotaoAdicionarProduto(){
		botaoAdicionar = driver.findElement(By.cssSelector(Seletores.BOTAOADICIONARPRODUTO));
		botaoAdicionar.click();
		logger.info("Clicou no bot�o 'Adicionar Produto'.");
	}

	public void informarMedida(String medidaSelecionada){
		campoMedidaNovoProduto = driver.findElement(By.cssSelector(Seletores.CAMPOMEDIDANOVOPRODUTO));
		campoMedidaNovoProduto.click();
		String medida = medidaSelecionada.isEmpty()? GeradorDeDados.selecionaMedidaAleatoriamente() : medidaSelecionada;
		cliqueMedida = driver.findElement(By.cssSelector(Seletores.CLICACAMPOMEDIDA));
		cliqueMedida.sendKeys(medida);
		if(medida.equals("Unidade")){
			opcaoUnidade = driver.findElement(By.xpath(Seletores.CLICARMEDIDAUNIDADE));
			opcaoUnidade.click();
			logger.info("Medida informada: '" + medida + "'.");
		}
		if(medida.equals("KG")){
			opcaoKg = driver.findElement(By.xpath(Seletores.CLICARMEDIDAKG));
			opcaoKg.click();
			logger.info("Medida informada: '" + medida + "'.");
		}
	}

	public void inserirInformacoesNovoProduto(){
		campoCodigoNovoProduto = driver.findElement(By.cssSelector(Seletores.CAMPOCODIGONOVOPRODUTO));
		campoCodigoNovoProduto.sendKeys(GeradorDeDados.informaCodigoNovoProduto());
		logger.info("C�digo inserido: ");

		campoDescricaoNovoProduto = driver.findElement(By.cssSelector(Seletores.CAMPODESCRICAONOVOPRODUTO));
		campoDescricaoNovoProduto.sendKeys("Produto novo - Somente para testes " + GeradorDeDados.randomizar(90));
		logger.info("Descri��o inserida: ");

		campoUrlImagem = driver.findElement(By.cssSelector(Seletores.CAMPOURLNOVOPRODUTO));
		campoUrlImagem.sendKeys("https://tinyurl.com/Imagem-Default");
		logger.info("URL inserida: ");

		campoTagNovoProduto = driver.findElement(By.cssSelector(Seletores.CAMPOTAGNOVOPRODUTO));
		campoTagNovoProduto.sendKeys("New_Product "+GeradorDeDados.randomizar(90));
		campoTagNovoProduto.sendKeys(Keys.TAB);
		logger.info("Nova tag inserida.");
	}

	public void salvarProdutoNovo(){
		botaoSalvarProdutoNovo = driver.findElement(By.xpath(Seletores.BOTAOSALVARNOVOPRODUTO));
		botaoSalvarProdutoNovo.click();
		logger.info("Clicou no bot�o 'Adicionar Produto'.");

		confirmaCriarProduto = driver.findElement(By.cssSelector(Seletores.SALVARNOVOPRODUTO));
		confirmaCriarProduto.click();
		logger.info("Clicou no bot�o 'Salvar'.");
	}
}