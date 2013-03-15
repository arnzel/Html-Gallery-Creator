package filesystem;
import java.io.File;
import java.io.FilenameFilter;

/**
 * 
 * 
 * @author Arne Zelasko
 * 
 * Filter which  list only the Graphic Files PNG,Gif and JPG when files.list()
 * is used.
 *
 */
public class GraphicFilesFilter implements FilenameFilter{

	String[] acceptedEndings = new String[]{".png",".gif",".jpg"}; 
	
	public boolean accept(File dir, String fileName) {
		for (int i = 0; i < acceptedEndings.length; i++) {
			String ending = acceptedEndings[i];
			if(fileName.endsWith(ending)){
				return true;
			}
		}
		return false;
	}

}
