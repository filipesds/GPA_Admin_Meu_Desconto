package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.GeradorDeDados;
import utils.Seletores;
import utils.Utils;
import utils.WaitTool;

public class PromocoesPage {
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
    private WebElement ufSelecionado;

    public PromocoesPage(WebDriver driver) {
        this.driver = driver;
        this.utils = new Utils(driver);
    }

    public void clicarAbaPromocoes(){
        abaPromocoes = driver.findElement(By.cssSelector(Seletores.ABAPROMOCOES));
        abaPromocoes.click();
        System.out.println("Clicou na aba 'Promoções' no menu lateral do Admin.");
    }

    public void clicarBotaoPesquisarTodas(){
        botaoPesquisarTodas = driver.findElement(By.xpath(Seletores.BOTAOPESQUISARTODAS));
        botaoPesquisarTodas.click();
        System.out.println("Clicou no botão 'Pesquisar Todas'.");
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
                System.out.println("A promoção '" + codePromocao + "' foi encontrada com sucesso.");
                realizouBusca = true;
            }
            else{
                System.out.println("Pesquisa não foi realizada.");
                }
             return realizouBusca;
    }

    public boolean validarPesquisaPorEstado() throws InterruptedException{
        boolean tagRegionalizada = false;
        tagPromocaoRegionalizada = driver.findElement(By.cssSelector(Seletores.TAGREGIONALIZADA));
        if(tagPromocaoRegionalizada.isDisplayed()){
            tagRegionalizada = true;
            System.out.println("Promoção Regionalizada encontrada.");
        }else{

            System.out.println("Não foram encontradas promoções para essa pesquisa.");
        }

        return tagRegionalizada;
    }

    public void clicarCampoCodigoPromocao(){
        campoCodigoPromocao = driver.findElement(By.cssSelector(Seletores.CAMPOCODIGOPROMOCAO));
        campoCodigoPromocao.click();
        System.out.println("Clicou no campo 'Código da promoção'.");
    }

    public void InformarCodigoPromocao(String promocaoSelecionada)throws InterruptedException{
        String promocao = promocaoSelecionada.isEmpty()? GeradorDeDados.selecionaCodigoPromocaoAleatoriamente() : promocaoSelecionada;
        campoCodigoPromocao.sendKeys(promocao);
        System.out.println("Informou o código '" + promocao + "' para pesquisar por promoções.");
        clicarBotaoPesquisar();
    }

    public void clicarBotaoPesquisar() throws InterruptedException {
        botaoPesquisar = driver.findElement(By.xpath(Seletores.BOTAOPESQUISAR));
        botaoPesquisar.click();
        System.out.println("Clicou no botão 'Pesquisar'.");

        scrollBotaoDesativar = driver.findElement(By.cssSelector(Seletores.SCROLLBOTAODESATIVAR));
        utils.realizarScroll(scrollBotaoDesativar);
    }

    public void clicarCampoEstados(){
        campoEstado = driver.findElement(By.cssSelector(Seletores.CAMPOESTADO));
        campoEstado.click();
        System.out.println("Clicou no campo 'Estados'.");
    }

    public void informarEstado(String estadoSelecionado)throws InterruptedException{
        String estado = estadoSelecionado.isEmpty()? GeradorDeDados.selecionaEstadoAleatoriamente() : estadoSelecionado;
        campoEstado.sendKeys(estado);
        System.out.println("Informou o estado '" + estado + "' para pesquisar por promoção Regionalizada.");
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