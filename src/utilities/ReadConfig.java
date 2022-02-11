package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
Properties prop;
	public ReadConfig()
	{
		try{
			File src=new File((System.getProperty("user.dir"))+"\\src\\resources\\data.properties");
			FileInputStream fis=new FileInputStream(src);
			prop=new Properties();
			prop.load(fis);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	public String getLocatorValue(String locator) {
		// TODO Auto-generated method stub
		String locatorValue=prop.getProperty(locator);
		return locatorValue;
	}
}
