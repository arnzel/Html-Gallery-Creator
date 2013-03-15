package logic;

import html.HtmlFileCreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.log4j.Logger;

import filesystem.GraphicFilesFilter;
import filesystem.PropertyAccess;

public class Console {

	Logger consoleLogger = Logger.getLogger(this.getClass());
	
	/**
	 *  in Directories.properties this key leads to the folder 
	 *  in which the pictures are saved, which are needed for the creation of the html files
	 * 
	 */
	
	public static String PICTURE_FOLDER_KEY = "PictureFolder";
	
	/**
	 * 
	 * the directory where the html files are saved. 
	 * this directory is entered in the console at the start of this programm
	 * 
	 */
	
	private File htmlVerzeichnis;

	/**
	 * 
	 * reads the directory for the html-files from the console.
	 * 
	 * 
	 */
	public void start() {
		System.out.println("Bitte geben Sie einen Pfad ein:");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		try {
			String eingabe = br.readLine();
			htmlVerzeichnis = new File(eingabe);
			boolean successCreateDir = htmlVerzeichnis.mkdir();
			
			consoleLogger.info("Verzeichnis " + htmlVerzeichnis.getAbsolutePath() + " wurde erzeugt " + successCreateDir);
			
			if (!htmlVerzeichnis.exists()) {
				consoleLogger
						.info("Verzeichnis "
								+ htmlVerzeichnis.getAbsolutePath()
								+ " gibt es nicht. Das Programm kann nicht fortfahren.");
				return;
			} else {
				consoleLogger.info("Verzeichnis " + htmlVerzeichnis.getAbsolutePath() + " existiert");
			}

		} catch (IOException e) {
			consoleLogger
					.error("IO-Exception beim Einlesen des Verzeichnisses");
		}
		createHtmlFiles();
		consoleLogger.info("Import der Dateien ist fertig");
	}

	/**
	 * 
	 * copies the arrow-pictures in a sub-directory of the directory for the html-files
	 * reads the names of all files in the pictureFolder filtered by GraphicFilesFilter
	 * creates a html-file for the filtered-files
	 * 
	 */
	public void createHtmlFiles() {

		HtmlFileCreator html = new HtmlFileCreator(htmlVerzeichnis);

		html.copyArrowPictures();

		PropertyAccess propertyAccess = new PropertyAccess();
		String pictureFolder = propertyAccess.getProperty(PICTURE_FOLDER_KEY);
		File pictureDir = new File(pictureFolder);

		GraphicFilesFilter graphicFilesFilter = new GraphicFilesFilter();
		String[] pictureFileNames = pictureDir.list(graphicFilesFilter);
		if(pictureFileNames == null){
			consoleLogger.error("Das in "  + PropertyAccess.DIRECTORIES_PROPERTIES_FILENAME +" angegebene Verzeichnis ist nicht gültig");
			return;
		}
		
		if (pictureFileNames.length != 0) {
			Arrays.sort(pictureFileNames);
			html.createHtmlFiles(pictureFileNames, pictureFolder);
		} else {
			consoleLogger.info("Das Verzeichnis " + pictureDir.getAbsolutePath()
					+ " ist leer ");
		}

	}

}
