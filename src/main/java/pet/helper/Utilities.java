/**
* @description: all the required general purpose utility methods can be added here. 
* @author:-Anand Chandran
*/
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

public class Utilities {
	private static Utilities myConfig = null;
	public static Map<String, String> bundle;
	public static Logger log = LogManager.getLogger(Utilities.class.getName());

	private Utilities() {
	}

	public static Map<String, String> getBundle() {
// returns a map with all the values from properties file
		if (myConfig == null) {
			myConfig = new Utilities();
			bundle = readAllConfig();
		}
		return bundle;
	}

	private static Map<String, String> readAllConfig() {
// reads all properties files from mentioned path and creates a hashmap with all key value pairs as per the properties file
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
