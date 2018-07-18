package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProjectProperties {
	public String getSeleniumProperties(String name){
		Properties properties = new Properties();
		String value = null;

		try{
			properties.load(new FileInputStream("properties/selenium.properties"));
			value = properties.getProperty(name);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return value;
	}
}