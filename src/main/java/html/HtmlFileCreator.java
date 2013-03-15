package html;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import filesystem.FileSystemAccess;
import filesystem.FileUtils;
import freemarker.template.Template;

public class HtmlFileCreator {

	private Logger htmlLogger = Logger.getLogger(this.getClass());

	File htmlVerzeichnis;

	FileSystemAccess fileSystemAccess;

	public static final String IMAGE_SUB_DIR = "images";

	public static final String BUTTONS_SUB_DIR = "buttons";

	public HtmlFileCreator(File htmlVerzeichnis) {
		this.htmlVerzeichnis = htmlVerzeichnis;
		fileSystemAccess = new FileSystemAccess(htmlVerzeichnis);
	}
	
	/**
	 * 
	 * creates inputstreams for "pictures/arrow_right.png" and "pictures/arrow_left.png"
	 * converts the streams to files
	 * copies the files to the subdir BUTTONS_SUB_DIR in the directory entered at the beginning of the programm
	 * 
	 */
	
	public void copyArrowPictures() {
		String arrowLeftFilename = "arrow_left.png";
		InputStream leftArrowInputStream = this.getClass().getClassLoader()
				.getResourceAsStream("pictures/" + arrowLeftFilename);
		File leftArrowPictureFile = FileUtils
				.convertInputStreamToFile(leftArrowInputStream,arrowLeftFilename);
		fileSystemAccess.copyPictureToTargetDirSubdir(leftArrowPictureFile,
				BUTTONS_SUB_DIR);
		
		String arrowRightFilename = "arrow_right.png";
		InputStream rightArrowStream = this.getClass().getClassLoader()
				.getResourceAsStream("pictures/" + arrowRightFilename);
		File rightArrowPictureFile = FileUtils
				.convertInputStreamToFile(rightArrowStream,arrowRightFilename);
		fileSystemAccess.copyPictureToTargetDirSubdir(rightArrowPictureFile,
				BUTTONS_SUB_DIR);

	}
	/**
	 * 
	 * creates the html-files. 
	 * 
	 * 
	 * @param pictureFileNames the name of all Files which are in the directory "PictureFolder". 
	 * "PictureFolder" is an key which can be found in the Directries.properties
	 * 
	 * @param pictureFolder the folder defined by the key "PictureFolder" in the Directories.properties.
	 */
	public void createHtmlFiles(String[] pictureFileNames,
			String pictureFolder) {
		// Creates first html-file,witch has only the arrow-right-button
		createFirstPicture(pictureFileNames, pictureFolder);

		// Creates html-files with arrows to right and left
		for (int i = 1; i < pictureFileNames.length - 1; i++) {
			createPictureMiddle(pictureFileNames, pictureFolder, i);
		}

		// Creates last html-file,witch has only the arrow-left-button
		createLastPicture(pictureFileNames, pictureFolder);
	}

	private void createPictureMiddle(String[] pictureFileNames,
			String pictureFolder, int index) {
		
		String middlePictureName = pictureFileNames[index];
		
		//creates html-file name for the previous and next picture.
		
		String previousPictureHtmlName = pictureFileNames[index - 1]
				.split("\\.")[0] + ".html";
		String nextPictureHtmlName = pictureFileNames[index + 1].split("\\.")[0]
				+ ".html";
		
		//copies the file to the entered directory in a subdir defined by IMAGE_SUB_DIR
		
		File middlePictureFile = new File(pictureFolder + File.separatorChar
				+ middlePictureName);
		fileSystemAccess.copyPictureToTargetDirSubdir(middlePictureFile,
				IMAGE_SUB_DIR);
		
		// inserts the values into the template pictureSite.tfl
		
		Freemarker freemarker = new Freemarker();
		String src = "images" + File.separatorChar
				+ middlePictureFile.getName();
		try {
			Template firstPageTemplate = freemarker.getMiddleHtmlTemplate();
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("src", src);
			input.put("height", "200");
			input.put("width", "200");
			input.put("picture_left", previousPictureHtmlName);
			input.put("picture_right", nextPictureHtmlName);
			String lastHtmlPath = htmlVerzeichnis.getAbsolutePath()
					+ File.separatorChar + middlePictureName.split("\\.")[0]
					+ ".html";
			freemarker.saveTemplate(firstPageTemplate, input, new File(
					lastHtmlPath));
		} catch (IOException e) {
			htmlLogger
					.error("Kann das Template für die mittlere Seite nicht finden");
		}
	}

	private void createLastPicture(String[] pictureFileNames,
			String pictureFolder) {
		String lastPictureName = pictureFileNames[pictureFileNames.length - 1];
		
		//creates html-file name for the previous picture.
		
		String previousPictureHtmlName = pictureFileNames[pictureFileNames.length - 2]
				.split("\\.")[0] + ".html";
		
		//copies the file to the entered directory in a subdir defined by IMAGE_SUB_DIR
		
		File lastPictureFile = new File(pictureFolder + File.separatorChar
				+ lastPictureName);
		fileSystemAccess.copyPictureToTargetDirSubdir(lastPictureFile,
				IMAGE_SUB_DIR);
		
		// inserts the values into the template pictureSiteEnd.tfl
		
		Freemarker freemarker = new Freemarker();
		String src = "images" + File.separatorChar + lastPictureFile.getName();
		try {
			Template firstPageTemplate = freemarker.getEndHtmlTemplate();
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("src", src);
			input.put("height", "200");
			input.put("width", "200");
			input.put("picture_left", previousPictureHtmlName);
			
			
			// save the created html-file to picturename.html, for an examplem "mypicture.html" for "mypicture.jpg"
			
			String lastHtmlPath = htmlVerzeichnis.getAbsolutePath()
					+ File.separatorChar + lastPictureName.split("\\.")[0]
					+ ".html";
			freemarker.saveTemplate(firstPageTemplate, input, new File(
					lastHtmlPath));
			
		} catch (IOException e) {
			htmlLogger
					.error("Kann das Template für die letzte Seite nicht finden");
		}

	}

	private void createFirstPicture(String[] pictureFileNames,
			String pictureFolder) {

		String firstPictureFileName = pictureFileNames[0];
		
		//creates html-file name for the next picture.
		
		String nextPictureHtmlName = pictureFileNames[1].split("\\.")[0]
				+ ".html";
		
		//copies the file to the entered directory in a subdir defined by IMAGE_SUB_DIR
		
		File firstPictureFile = new File(pictureFolder + File.separatorChar
				+ firstPictureFileName);
		fileSystemAccess.copyPictureToTargetDirSubdir(firstPictureFile,
				IMAGE_SUB_DIR);
		
		// inserts the values into the template pictureSiteStart.tfl
		
		Freemarker freemarker = new Freemarker();
		String src = IMAGE_SUB_DIR + File.separatorChar + firstPictureFile.getName();
		try {
			Template firstPageTemplate = freemarker.getStartHtmlTemplate();
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("src", src);
			input.put("height", "200");
			input.put("width", "200");
			input.put("picture_right", nextPictureHtmlName);
			
			// save the created html-file to picturename.html, for an examplem "mypicture.html" for "mypicture.jpg"
			
			String firstHtmlPath = htmlVerzeichnis.getAbsolutePath()
					+ File.separatorChar + firstPictureFileName.split("\\.")[0]
					+ ".html";

			freemarker.saveTemplate(firstPageTemplate, input, new File(
					firstHtmlPath));
		} catch (IOException e) {
			htmlLogger
					.error("Kann das Template für die erste Seite nicht finden");
		}

	}

}
