package tests;

import org.junit.Test;
import pages.LoginPage;
import pages.ProdutoPage;
import setup.Setup;
import static org.junit.Assert.assertTrue;

public class LoginTest extends Setup {

    @Test
    public void realizarLogin(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.RealizarLogin(usuario.getUsuarioDev(), usuario.getSenhaDev());
        ProdutoPage produtoPage = new ProdutoPage(driver);

        assertTrue("Ocorreu algum erro ao tentar realizar o login!",produtoPage.verificarUsuarioLogado());
    }
}