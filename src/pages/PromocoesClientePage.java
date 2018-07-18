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

public class PromocoesClientePage {
	private static final Logger logger = LogManager.getLogger();
	private WebDriver driver;
	private Utils utils;
	private WebElement campoCpf;
	private WebElement abaCliente;
	private WebElement campoMarca;
	private WebElement selecionaEstado;
	private WebElement campoAposClique;
	private WebElement opcaoAposClicarPA;
	private WebElement opcaoAposClicarEX;
	private WebElement estadoAposClicar;
	private WebElement clicarUfSelecionado;
	private WebElement botaoPesquisarCliente;
	private WebElement marcaPA;
	private WebElement marcaEX;

	public PromocoesClientePage(WebDriver driver) {
		this.driver = driver;
		this.utils = new Utils(driver);
	}

	public void clicarAbaPromocoesCliente() {
		abaCliente = driver.findElement(By.cssSelector(Seletores.ABAPROMOCOESCLIENTE));
		abaCliente.click();
		logger.info("Clicou na aba 'Promoções de clientes' no menu lateral do Admin.");
	}

	public void informarCpf() {
		campoCpf = driver.findElement(By.cssSelector(Seletores.CAMPOCPF));
		campoCpf.sendKeys(GeradorDeDados.geraCPF());
		String cpf = campoCpf.getText();
		logger.info("CPF inserido com sucesso.");
	}

	public void informarMarca(String marcaSelecionada) {
		campoMarca = driver.findElement(By.xpath(Seletores.SELECTMARCA));
		campoMarca.click();
		String brand = marcaSelecionada.isEmpty() ? GeradorDeDados.selecionaMarcaAleatoriamente() : marcaSelecionada;
		campoAposClique = driver.findElement(By.cssSelector(Seletores.CLICARITEMSELECIONADO));
		campoAposClique.sendKeys(brand);
		if(brand.equals("PA")){
			opcaoAposClicarPA = driver.findElement(By.xpath(Seletores.CLICAMARCAPA));
			opcaoAposClicarPA.click();
			logger.info("Marca informada: '" + brand + "'.");
		}
		if(brand.equals("EX")){
			opcaoAposClicarEX = driver.findElement(By.xpath(Seletores.CLICAMARCAEX));
			opcaoAposClicarEX.click();
			logger.info("Marca informada: '"+brand+ "'.");
		}
	}

	public void informarEstado(String ufSelecionado) {
		selecionaEstado = driver.findElement(By.xpath(Seletores.SELECTESTADO));
		selecionaEstado.click();
		String uf = ufSelecionado.isEmpty() ? GeradorDeDados.selecionaEstadoAleatoriamente() : ufSelecionado;
		estadoAposClicar = driver.findElement(By.xpath(Seletores.ESTADOAPOSCLICAR));
		estadoAposClicar.sendKeys(uf);
		clicarUfSelecionado = driver.findElement(By.cssSelector(Seletores.ESTADOSELECIONADO));
		clicarUfSelecionado.click();
		logger.info("Estado '" + uf + "' selecionado com sucesso.");
	}

	public void clicarBotaoPesquisarCliente() {
		botaoPesquisarCliente = driver.findElement(By.xpath(Seletores.BOTAPESQUISARCLIENTE));
		botaoPesquisarCliente.click();
		logger.info("Clicou no botão 'Pesquisar'.");
	}

	public boolean validarBuscaPromocaoCliente() {
		boolean promocaoEncontrada = false;
		promocaoEncontrada = !WaitTool.isElementPresent(driver, By.cssSelector(Seletores.PROMOCLIENTENAOENCONTRADA), 5);
		if (promocaoEncontrada == true) {
			promocaoEncontrada = true;
			logger.info("Busca realizada com sucesso.");
		}
		else {
			promocaoEncontrada = false;
			logger.info("Não foram encontradas promoções para esse cliente ou o cpf não está cadastrado.");
		}
		return promocaoEncontrada;
	}
}