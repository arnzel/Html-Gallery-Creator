package filesystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * 
 * class for helper methods for handling file system operations
 * 
 * @author arnzel
 *
 */
public class FileUtils {
	
	/**
	 * 
	 * helper-method to create file objects for inpustreams. can be used to get file objects for resources in jar-files
	 * 
	 * @param inputStream the stram which is to convert to a file object
	 * @param pathname th anme of the new file
	 * @return a file object which value is identical to the inputstream
	 */
	public static File convertInputStreamToFile(InputStream inputStream,String pathname) {
		try {
			
			File file = new File(pathname);
			
			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(file);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			inputStream.close();
			out.flush();
			out.close();

			return file;
		} catch (IOException e) {
			return null;
		}
	}

}
