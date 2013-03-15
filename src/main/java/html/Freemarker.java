package html;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * @author Arne Zelasko
 * 
 *         Class for creating html files from the templates in
 *         TEMPLATE_DIR. this class uses the Template Engine
 *         Freemarker :http://freemarker.sourceforge.net/
 * 
 */

public class Freemarker {

	Logger freeMarkerLogger = Logger.getLogger(this.getClass());
	
	public static final String TEMPLATE_DIR = "/templates";
	
	Configuration cfg = new Configuration();
	
	/**
	 * 
	 * sets the directory for the templates freemarker uses.
	 * 
	 */
	
	public Freemarker() {
		TemplateLoader templateLoader = new ClassTemplateLoader(this.getClass(),
				TEMPLATE_DIR);
		 cfg.setTemplateLoader( templateLoader ); 

	}
	
	/**
	 * creates the template for the site with the first picture
	 * 
	 * @return the created template
	 * @throws IOException if a part of the template-file cannot be read 
	 */
	public Template getStartHtmlTemplate() throws IOException {
		Template template = cfg.getTemplate("pictureSiteStart.ftl");
		return template;
	}
	
	/**
	 * creates the template for the site which are used for all sites except of the first an last one
	 * 
	 * @return the created template
	 * @throws IOException if a part of the template-file cannot be read 
	 */

	public Template getMiddleHtmlTemplate() throws IOException {
		Template template = cfg.getTemplate("pictureSite.ftl");
		return template;
	}
	
	/**
	 * creates the template for the site with the last picture
	 * 
	 * @return the created template
	 * @throws IOException if a part of the template-file cannot be read 
	 */

	public Template getEndHtmlTemplate() throws IOException {
		Template template = cfg.getTemplate("pictureSiteEnd.ftl");
		return template;
	}

	/**
	 * 
	 * inserts the placeholders in the template and saves the generated html-file
	 * .placeholders are marked with ${placeholdername}
	 * 
	 * @param template
	 *            the name of the template.must be a file with ending .tfl
	 * @param input
	 *            a map which maps the name of the placeholders to concrete
	 *            values
	 * @param file
	 *            the created html-file from the template
	 */
	public void saveTemplate(Template template, Map<String, Object> input,
			File file) {
		try {
			FileWriter fileWriter = new FileWriter(file);
			try {
				template.process(input, fileWriter);
			} catch (TemplateException e) {
				e.printStackTrace();
				freeMarkerLogger.error("Fehler beim Erstellen des Templates");
			}
			fileWriter.flush();
		} catch (IOException e) {
			freeMarkerLogger.error("IO-Fehler beim Erstellen des Templates");
		}

	}

}
