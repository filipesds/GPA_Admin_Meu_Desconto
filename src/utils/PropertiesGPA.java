package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertiesGPA {
	private static final Logger logger = LogManager.getLogger();

	public String getSeleniumProperties(String name) {
		Properties properties = new Properties();
		String value = null;
		try {
			properties.load(new FileInputStream("properties/selenium.properties"));
			value = properties.getProperty(name);
			logger.info("Properties : " +name+ " - Valor do properties : " + value);
		} 
		catch (IOException e) {
			logger.error("Erro ao carregar o properties: ");
			logger.error(e.getStackTrace());
		}
		return value;
	}
}