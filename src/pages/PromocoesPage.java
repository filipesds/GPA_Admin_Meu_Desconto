package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.GeradorDeDados;
import utils.Seletores;
import utils.Utils;
import utils.WaitTool;

public class PromocoesPage {
	private static final Logger logger = LogManager.getLogger();
	private WebDriver driver;
	private Utils utils;
	private WebElement abaPromocoes;
	private WebElement botaoPesquisarTodas;
	private WebElement scrollBotaoDesativar;
	private WebElement campoCodigoPromocao;
	private WebElement botaoPesquisar;
	private WebElement codigoPromocao;
	private WebElement campoEstado;
	private WebElement botaoCalendarioDtFinal;
	private WebElement confirmaEstadoInserido;
	private WebElement tagPromocaoRegionalizada;
	private WebElement campoDescricaoPromocao;
	private WebElement campoCodigoProduto;

	public PromocoesPage(WebDriver driver) {
		this.driver = driver;
		this.utils = new Utils(driver);
	}

	public void clicarAbaPromocoes(){
		abaPromocoes = driver.findElement(By.cssSelector(Seletores.ABAPROMOCOES));
		abaPromocoes.click();
		logger.info("Clicou na aba 'Promo��es' no menu lateral do Admin.");
	}

	public void clicarBotaoPesquisarTodas(){
		botaoPesquisarTodas = driver.findElement(By.xpath(Seletores.BOTAOPESQUISARTODAS));
		botaoPesquisarTodas.click();
		logger.info("Clicou no bot�o 'Pesquisar Todas'.");
	}

	public boolean validarPesquisarTodasPromocoes() throws InterruptedException {
		boolean realizouBusca = false;
		if(WaitTool.isElementPresent(driver, By.cssSelector(Seletores.SCROLLBOTAODESATIVAR),10)) {
			realizouBusca = true;
			scrollBotaoDesativar = driver.findElement(By.cssSelector(Seletores.SCROLLBOTAODESATIVAR));
			utils.realizarScroll(scrollBotaoDesativar);
		}
		return realizouBusca;
	}

	public boolean validarPesquisarPromocoesPorCodigo(String promocaoSelecionada) throws InterruptedException{
		boolean realizouBusca = false;
		codigoPromocao = driver.findElement(By.xpath(Seletores.CODIGOPROMOCAO));
		String codePromocao = codigoPromocao.getText().substring(15);
		if(codePromocao.contains(promocaoSelecionada)) {
			logger.info("A promo��o '" + codePromocao + "' foi encontrada com sucesso.");
			realizouBusca = true;
		}
		else{
			System.out.println("Pesquisa n�o foi realizada.");
		}
		return realizouBusca;
	}

	public boolean validarPesquisaPorEstado() throws InterruptedException{
		boolean tagRegionalizada = false;
		tagPromocaoRegionalizada = driver.findElement(By.cssSelector(Seletores.TAGREGIONALIZADA));
		if(tagPromocaoRegionalizada.isDisplayed()){
			tagRegionalizada = true;
			logger.info("Promo��o Regionalizada encontrada.");
		}else{
			logger.info("N�o foram encontradas promo��es para essa pesquisa.");
		}
		return tagRegionalizada;
	}

	public void clicarCampoCodigoPromocao(){
		campoCodigoPromocao = driver.findElement(By.cssSelector(Seletores.CAMPOCODIGOPROMOCAO));
		campoCodigoPromocao.click();
		logger.info("Clicou no campo 'C�digo da promo��o'.");
	}

	public void clicarCampoCodigoProdutoPromocao(){
		campoCodigoProduto = driver.findElement(By.cssSelector(Seletores.CAMPOCODIGOPRODUTO));
		campoCodigoProduto.click();
		logger.info("Clicou no campo 'C�digo do produto'.");
	}

	public void clicarCampoDescricaoPromocao(){
		campoDescricaoPromocao = driver.findElement(By.cssSelector(Seletores.CAMPODESCRICAOPROMOCAO));
		campoDescricaoPromocao.click();
		logger.info("Clicou no campo 'Descri��o do produto'.");
	}

	public void informarCodigoPromocao(String promocaoSelecionada)throws InterruptedException{
		String promocao = promocaoSelecionada.isEmpty()? GeradorDeDados.selecionaCodigoPromocaoAleatoriamente() : promocaoSelecionada;
		campoCodigoPromocao.sendKeys(promocao);
		logger.info("Informou o c�digo da promo��o '" + promocao + "' para pesquisar por promo��es.");
		clicarBotaoPesquisar();
	}

	public void informarCodigoProdutoPromocao(String produtoSelecionado)throws InterruptedException{
		String produto = produtoSelecionado.isEmpty()? GeradorDeDados.selecionaCodigoProdutoPromocaoAleatoriamente() : produtoSelecionado;
		campoCodigoProduto.sendKeys(produto);
		System.out.println("Informou o c�digo do produto '" + produto + "' para pesquisar por promo��es.");
		clicarBotaoPesquisar();
	}

	public void informarDescricaoProdutoPromocao(String produtoSelecionado) throws InterruptedException{
		String produto = produtoSelecionado.isEmpty()?GeradorDeDados.selecionaDescricaoProdutoPromocaoAleatoriamente() : produtoSelecionado;
		campoDescricaoPromocao.sendKeys(produto);
		System.out.println("Informou a descri��o do produto '" + produto + "' para pesquisar por promo��es.");
		clicarBotaoPesquisar();
	}

	public void clicarBotaoPesquisar() throws InterruptedException {
		botaoPesquisar = driver.findElement(By.xpath(Seletores.BOTAOPESQUISAR));
		botaoPesquisar.click();
		logger.info("Clicou no bot�o 'Pesquisar'.");
		scrollBotaoDesativar = driver.findElement(By.cssSelector(Seletores.SCROLLBOTAODESATIVAR));
		utils.realizarScroll(scrollBotaoDesativar);
	}

	public void clicarCampoEstados(){
		campoEstado = driver.findElement(By.cssSelector(Seletores.CAMPOESTADO));
		campoEstado.click();
		logger.info("Clicou no campo 'Estados'.");
	}

	public void informarEstado(String estadoSelecionado)throws InterruptedException{
		String estado = estadoSelecionado.isEmpty()? GeradorDeDados.selecionaEstadoAleatoriamente() : estadoSelecionado;
		campoEstado.sendKeys(estado);
		logger.info("Informou o estado '" + estado + "' para pesquisar por promo��o Regionalizada.");
		confirmaEstadoInserido = driver.findElement(By.cssSelector(Seletores.CLICARESTADOINSERIDO));
		confirmaEstadoInserido.click();
		clicarCalendarioDataFinal();
		clicarBotaoPesquisar();
	}

	public void clicarCalendarioDataFinal(){
		botaoCalendarioDtFinal = driver.findElement(By.cssSelector(Seletores.CALENDARIODTFINAL));
		botaoCalendarioDtFinal.click();
	}
}