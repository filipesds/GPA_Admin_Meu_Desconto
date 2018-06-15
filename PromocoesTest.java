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
        promocoesPage.InformarCodigoPromocao(promocaoSelecionada);

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
}