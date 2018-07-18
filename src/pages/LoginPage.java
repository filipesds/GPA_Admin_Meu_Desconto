package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Seletores;
import utils.Utils;
import utils.WaitTool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginPage {
	private static final Logger logger = LogManager.getLogger();
	private WebDriver driver;
	private Utils utils;
	private WebElement campoUsuario;
	private WebElement campoSenha;
	private WebElement botaoAcessarConta;

	public LoginPage(WebDriver driver){
		this.driver = driver;
		this.utils = new Utils(driver);
	}

	public ProdutoPage RealizarLogin(String usuario, String senha){
		campoUsuario = driver.findElement(By.cssSelector(Seletores.CAMPOUSUARIOLOGIN));
		campoUsuario.sendKeys(usuario);
		logger.info("O usuário '" + usuario + "' foi inserido no campo usuário.");

		campoSenha = driver.findElement(By.cssSelector(Seletores.CAMPOSENHALOGIN));
		campoSenha.sendKeys(senha);
		logger.info("A senha '" + senha + "' foi inserida no campo senha.");

		botaoAcessarConta = driver.findElement(By.xpath(Seletores.BOTAOACESSARSUACONTA));
		botaoAcessarConta.click();
		logger.info("Clicou no botão 'Acessar sua Conta'.");

		return new ProdutoPage(driver);
	}

	public boolean verificaUsuarioDeslogado(){
		boolean isLoggedOut = WaitTool.isElementPresent(driver, By.cssSelector(Seletores.CAMPOUSUARIOLOGIN),5);
		if(isLoggedOut){
			logger.info("O usuário realizou logout com sucesso.");
		}
		else{
			logger.info("O usuário ainda está logado!");
		}
		return isLoggedOut;
	}
}