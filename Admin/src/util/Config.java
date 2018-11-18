package util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

@SuppressWarnings("unused")
public class Config {
	
	private static Properties p = null;
	
	static {
    try {
    	p=new Properties();
    	//p.load(new FileInputStream("config/sqlserver.properties"));
    	p.load(Config.class.getClassLoader().getResourceAsStream("config/server.properties"));
    }catch (Exception e) {
    	e.printStackTrace();
    }
	}

    public static String getValue(String key) {
    	return p.get(key).toString();
    }
}