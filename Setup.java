package setup;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import utils.ProjectProperties;
import utils.SeleniumUtils;
import utils.Users;
import utils.WaitTool;


public class Setup {
    protected static WebDriver driver;
    protected static Users usuario = new Users();

   @Before
    public void Setup() throws Exception{
        SeleniumUtils sel = new SeleniumUtils();
        ProjectProperties prop = new ProjectProperties();
        driver = sel.getDriver(prop.getSeleniumProperties("selenium.browser"));
        WaitTool.setImplicitWait(driver, WaitTool.DEFAULT_WAIT_4_ELEMENT);
    }

   @After
    public void tearDown(){
        driver.quit();
        System.out.println("O navegador foi fechado. Teste finalizado!");
    }
}