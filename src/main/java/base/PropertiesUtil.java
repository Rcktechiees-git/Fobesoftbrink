package base;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class PropertiesUtil {
	 public static Properties loadProperties() {
	        Properties properties = new Properties();
	        try {
	            FileInputStream fileInputStream = new FileInputStream("config.properties");
	            properties.load(fileInputStream);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return properties;
	    }
}
