package filesystem;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.w3c.dom.html.HTMLDirectoryElement;

import com.google.common.io.Files;
/**
 * 
 * @author Arne Zelasko
 * 
 * Helper Class for copying Files to the directory, where the Html-Files are created
 *
 */
public class FileSystemAccess {
	
	private Logger fileSystemLogger = Logger.getLogger(this.getClass());
	
	File htmlDirectory;
	
	public FileSystemAccess(File htmlVerzeichnis){
		this.htmlDirectory = htmlVerzeichnis;
	}
	/**
	 * 
	 * Copies a files to the a subdir of htmlDirectory
	 * 
	 * @param file a given file
	 * @param subdir the name of the subdirectory
	 */
	public void copyPictureToTargetDirSubdir(File file,String subdir) {
		
		// Create the subdirectory
		
		File subdirFile = new File(htmlDirectory.getAbsolutePath()
				+ File.separatorChar + subdir);
		subdirFile.mkdir();
		
		// Creates File Object for the new file in htmlDirectory
		File from = new File(file.getAbsolutePath());
		File to = new File(htmlDirectory.getAbsolutePath()
				+ File.separatorChar + subdir + File.separatorChar
				+ file.getName());
		
		//Copies the files
		
		try {
			Files.copy(from, to);
		} catch (IOException e) {
			fileSystemLogger.error("Kann Datei " + from.getAbsolutePath()
					+ " nicht ins Zielverzeichnis kopieren");
		}
	}


}
