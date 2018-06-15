package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Seletores;
import utils.Utils;
import utils.WaitTool;


public class LoginPage {
    private WebDriver driver;
    private Utils utils;
    private WebElement campoUsuario;
    private WebElement campoSenha;
    private WebElement botaoAcessarConta;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        this.utils = new Utils(driver);
    }

    public void RealizarLogin(String usuario, String senha){
        campoUsuario = driver.findElement(By.cssSelector(Seletores.CAMPOUSUARIOLOGIN));
        campoUsuario.sendKeys(usuario);
        System.out.println("O usuário '" + usuario + "' foi inserido no campo usuário.");

        campoSenha = driver.findElement(By.cssSelector(Seletores.CAMPOSENHALOGIN));
        campoSenha.sendKeys(senha);
        System.out.println("A senha '" + senha + "' foi inserida no campo senha.");

        botaoAcessarConta = driver.findElement(By.xpath(Seletores.BOTAOACESSARSUACONTA));
        botaoAcessarConta.click();
        System.out.println("Clicou no botão 'Acessar sua Conta'.");
    }

    public boolean verificaUsuarioDeslogado(){
        boolean isLoggedOut = WaitTool.isElementPresent(driver, By.cssSelector(Seletores.CAMPOUSUARIOLOGIN),5);
        if(isLoggedOut){
            System.out.println("O usuário realizou logout com sucesso.");
        }
        else{
            System.out.println("O usuário ainda está logado!");
        }
        return isLoggedOut;
    }
}