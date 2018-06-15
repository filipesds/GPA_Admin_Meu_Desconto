package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.GeradorDeDados;
import utils.Seletores;
import utils.Utils;
import utils.WaitTool;

public class PromocoesClientePage {
    private WebDriver driver;
    private Utils utils;
    private WebElement campoCpf;
    private WebElement abaCliente;
    private WebElement campoMarca;
    private WebElement selecionaEstado;
    private WebElement campoAposClique;
    private WebElement opcaoAposClicar;
    private WebElement estadoAposClicar;
    private WebElement clicarUfSelecionado;
    private WebElement botaoPesquisarCliente;

    public PromocoesClientePage(WebDriver driver) {
        this.driver = driver;
        this.utils = new Utils(driver);
    }

    public void clicarAbaPromocoesCliente() {
        abaCliente = driver.findElement(By.cssSelector(Seletores.ABAPROMOCOESCLIENTE));
        abaCliente.click();
        System.out.println("Clicou na aba 'Promoções de clientes' no menu lateral do Admin.");
    }

    public void informarCpf() {
        campoCpf = driver.findElement(By.cssSelector(Seletores.CAMPOCPF));
        campoCpf.sendKeys(GeradorDeDados.geraCPF());
        String cpf = campoCpf.getText();
        System.out.println("CPF inserido com sucesso.");
    }

    public void informarMarca(String marcaSelecionada) {
        campoMarca = driver.findElement(By.xpath(Seletores.SELECTMARCA));
        campoMarca.click();
        String brand = marcaSelecionada.isEmpty() ? GeradorDeDados.selecionaMarcaAleatoriamente() : marcaSelecionada;
        campoAposClique = driver.findElement(By.cssSelector(Seletores.CLICARITEMSELECIONADO));
        campoAposClique.sendKeys(brand);
        opcaoAposClicar = driver.findElement(By.cssSelector(Seletores.OPCAOAPOSCLICAR));
        opcaoAposClicar.click();
        System.out.println("Marca informada: '" + brand + "'.");
    }

    public void informarEstado(String ufSelecionado) {
        selecionaEstado = driver.findElement(By.xpath(Seletores.SELECTESTADO));
        selecionaEstado.click();
        String uf = ufSelecionado.isEmpty() ? GeradorDeDados.selecionaEstadoAleatoriamente() : ufSelecionado;
        estadoAposClicar = driver.findElement(By.xpath(Seletores.ESTADOAPOSCLICAR));
        estadoAposClicar.sendKeys(uf);
        clicarUfSelecionado = driver.findElement(By.cssSelector(Seletores.ESTADOSELECIONADO));
        clicarUfSelecionado.click();
        System.out.println("Estado '" + uf + "' selecionado com sucesso.");
    }

    public void clicarBotaoPesquisarCliente() {
        botaoPesquisarCliente = driver.findElement(By.xpath(Seletores.BOTAPESQUISARCLIENTE));
        botaoPesquisarCliente.click();
        System.out.println("Clicou no botão 'Pesquisar'.");
    }

    public boolean validarBuscaPromocaoCliente() {
        boolean naoEncontrada = true;
        naoEncontrada = WaitTool.isElementPresent(driver, By.cssSelector(Seletores.PROMOCLIENTENAOENCONTRADA), 10);
        if (naoEncontrada == true) {
            System.out.println("Não foram encontradas promoções para esse cliente ou o cpf não está cadastrado.");
        } else {
            naoEncontrada = false;
            System.out.println("Promoção encontrada.");
        }
        return naoEncontrada;
    }
}