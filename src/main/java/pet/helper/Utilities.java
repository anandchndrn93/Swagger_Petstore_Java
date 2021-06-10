
package pet.helper;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * All the required general purpose utility methods can be added here.
 * 
 * @author Anand Chandran
 */
public class Utilities {
	private static Utilities myConfig = null;
	/**
	 * Map to store all key value pairs from property file
	 */
	public static Map<String, String> bundle;
	/**
	 * Logger object to add all logs
	 */
	private static  Logger log = LogManager.getLogger(Utilities.class.getName());

	/**
	 * Reads all the values from properties file
	 * 
	 * @return Map with all the values from properties file
	 */
	public static Map<String, String> getBundle() {
		if (myConfig == null) {
			myConfig = new Utilities();
			bundle = readAllConfig();
		}
		return bundle;
	}

	/**
	 * Reads all properties files from mentioned path and creates a hashmap with all
	 * key value pairs as per the properties file
	 * 
	 * @return Hashmap with all the values from properties file
	 */
	private static Map<String, String> readAllConfig() {
		String[] allFiles = { "src/main/resources/config/" };
		Properties prop = new Properties();
		for (String filePath : allFiles) {
			File file = new File(filePath);
			// get all properties file
			File[] matchingFiles = file.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(".properties");
				}
			});

			try {
				for (File matchineFile : matchingFiles) {
					log.info("Current file loading : " + matchineFile.getAbsolutePath());
					prop.load(new FileReader(matchineFile)); // read properties file
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new HashMap(prop);
	}
}
