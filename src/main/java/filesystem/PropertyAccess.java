package filesystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author Arne Zelasko
 * 
 *         Class which reads the key 'PictureFolder' from
 *         Environment.properties(for an example C:\Pictures). From this
 *         Filename all Pictures, which are accepted by the GraphicFilesfilter
 *         are copied to the target, entered in the console
 * 
 */
public class PropertyAccess {

	public static final String DIRECTORIES_PROPERTIES_FILENAME = "Directories.properties";

	private Logger propertyAccess = Logger.getLogger(this.getClass());
	
	/**
	 * 
	 * creates an file-instance for the file DIRECTORIES_PROPERTIES_FILENAME and reads a value for a given key in this file
	 * 
	 * @param key a given key for which the value is to return
	 * @return the value of the key
	 */
	
	public String getProperty(String key) {
		Properties prop = new Properties();
		try {
			InputStream directoriesPropertiesInputStream = this.getClass().getClassLoader()
					.getResourceAsStream(DIRECTORIES_PROPERTIES_FILENAME);
			prop.load(new FileInputStream(FileUtils.convertInputStreamToFile(directoriesPropertiesInputStream,DIRECTORIES_PROPERTIES_FILENAME)));
		} catch (FileNotFoundException e) {
			propertyAccess.error("Kann folgende Datei nicht finden:"
					+ DIRECTORIES_PROPERTIES_FILENAME);
		} catch (IOException e) {
			propertyAccess.error("IO-Exception beim Lesen der Datei "
					+ DIRECTORIES_PROPERTIES_FILENAME);
		}
		return prop.getProperty(key);
	}

}
