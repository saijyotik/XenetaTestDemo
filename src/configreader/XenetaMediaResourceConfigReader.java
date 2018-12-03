package configreader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
 
/**
 * This class reads Media Resources propeties file and sets the values in the getter & setter methods.
 * 
 * @author SaiJyoti Kamisetti
 * @since 30.11.2018
 * @version 1.0
 */

public class XenetaMediaResourceConfigReader {

	 private Properties properties;
	 private final String propertyFilePath= "config//XenetaMediaResourseConfig.properties";
	 
	 /**
	  * Method reads the properties file.
	  */
	 public XenetaMediaResourceConfigReader(){
		 
		 BufferedReader reader;
		 try {
			 reader = new BufferedReader(new FileReader(propertyFilePath));
			 properties = new Properties();
			 properties.load(reader);
			 reader.close();
		 } catch (FileNotFoundException fnfe) {
			 fnfe.printStackTrace();
			 throw new RuntimeException("XenetaManageResourceConfig.properties is not found at " + propertyFilePath + fnfe.getMessage());
		 } catch (IOException ioe) {
			 ioe.printStackTrace();
		 }
	 }
	 
	 public String getBrowser(){
		 String browser = properties.getProperty("browser1");
		 if(browser!= null) return browser;
		 else throw new RuntimeException("browser1 not specified in the XenetaManageResourceConfig.properties file."); 
	 }
	 
	 public String getTabLocator() { 
		 String tablocator = properties.getProperty("openpage_tab");
		 if(tablocator != null) return tablocator;
		 else throw new RuntimeException("openpage_tab not specified in the XenetaManageResourceConfig.properties file."); 
	 }
	 
	 public String getSectionTitle() {
		 String title_locator = properties.getProperty("openpage_newtag");
		 if(title_locator != null) return title_locator;
		 else throw new RuntimeException("openpage_newtag not specified in the XenetaManageResourceConfig.properties file.");
	 }
	 
	 public String getImageLocator() {
		 String image_locator = properties.getProperty("multiwindow_image");
		 if(image_locator != null) return image_locator;
		 else throw new RuntimeException("multiwindow_image not specified in the XenetaManageResourceConfig.properties file.");
	 }
	 
	 public String getImagePopupLocator() {
		 String imagepop_locator = properties.getProperty("multiwindow_imagepop");
		 if(imagepop_locator != null) return imagepop_locator;
		 else throw new RuntimeException("openpage_newta not specified in the XenetaManageResourceConfig.properties file.");
	 }
	
}
