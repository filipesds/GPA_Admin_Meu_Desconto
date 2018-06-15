package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.GeradorDeDados;
import utils.Seletores;
import utils.Utils;
import utils.WaitTool;

public class ProdutoPage {
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

    public ProdutoPage(WebDriver driver) {
        this.driver = driver;
        this.utils = new Utils(driver);
    }

    public void clicarBotaoSair() {
        botaoSair = driver.findElement(By.cssSelector(Seletores.BOTAOSAIR));
        botaoSair.click();
        System.out.println("Clicou no botão 'Sair da Aplicação'.");
    }

    public boolean verificarUsuarioLogado() {
        boolean isLoggedIn = WaitTool.isElementPresent(driver, By.cssSelector(Seletores.BOTAOADICIONARPRODUTO), 5);
        if (isLoggedIn) {
            System.out.println("O usuário está logado.");
        } else {
            System.out.println("O usuário não está logado!");
        }
        return isLoggedIn;
    }

    public void clicarCampoBuscaDescricao() {
        campoDescricao = driver.findElement(By.cssSelector(Seletores.CAMPODESCRICAO));
        campoDescricao.click();
        System.out.println("Clicou no campo de descrição.");
    }

    public void informarDescricaoPorBusca(String descricaoSelecionada) throws InterruptedException {
        String descricao = descricaoSelecionada.isEmpty() ? GeradorDeDados.selecionaDescricaoProdutoAleatoriamente() : descricaoSelecionada;
        campoDescricao.sendKeys(descricao);
        System.out.println("Informou o termo '" + descricao + "' para pesquisa.");

        botaoPesquisar = driver.findElement(By.xpath(Seletores.BOTAOPESQUISAR));
        botaoPesquisar.click();
        System.out.println("Clicou no botão 'Pesquisar'.");

        if(WaitTool.isElementPresent(driver, By.cssSelector(Seletores.SCROLLPROXIMAPAGE),10)){
            scrollProximaPage = driver.findElement(By.cssSelector(Seletores.SCROLLPROXIMAPAGE));
            utils.realizarScroll(scrollProximaPage);
        }
        else{
            System.out.println("Não foi necessário realizar scroll na tela para visualizar o resultado da busca.");
        }
    }

    public void informarCodigoPorBusca(String codigoSelecionado)throws InterruptedException{
        String codigo = codigoSelecionado.isEmpty() ? GeradorDeDados.selecionaCodigoProdutoAleatoriamente(): codigoSelecionado;
        campoCodigo.sendKeys(codigo);
        System.out.println("Informou o código '" + codigo + "' para pesquisa.");
        campoDescricao = driver.findElement(By.cssSelector(Seletores.CAMPODESCRICAO));
        campoDescricao.click();

        botaoPesquisar = driver.findElement(By.xpath(Seletores.BOTAOPESQUISAR));
        botaoPesquisar.click();
        System.out.println("Clicou no botão 'Pesquisar'.");
    }

    public boolean validarBuscaProdutoPorDescricao(String descricaoSelecionada){
        boolean pesquisaValida = false;
        nomeProdutoDescricao = driver.findElement(By.cssSelector(Seletores.NOMEPRODUTODESCRICAO));
        String descricaoTermo = nomeProdutoDescricao.getText();
        if(descricaoTermo.toLowerCase().contains(descricaoSelecionada.toLowerCase())){
            System.out.println("Pesquisa realizada com sucesso.");
            pesquisaValida = true;
        }
        else{
            System.out.println("Pesquisa não foi realizada.");
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
            System.out.println("Pesquisa realizada com sucesso. O produto '" +nomeProduto+ "' foi encontrado para o código '"+plu+"' informado.");
            pesquisaValida = true;
        }
        else{
            System.out.println("Pesquisa não foi realizada.");
        }
        return pesquisaValida;
    }

    public void clicarCampoBuscaCodigo(){
        campoCodigo = driver.findElement(By.cssSelector(Seletores.CAMPOCODIGO));
        campoCodigo.click();
        System.out.println("Clicou no campo de código.");
    }
}