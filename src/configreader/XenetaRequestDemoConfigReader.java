package configreader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * This class reads RequestDemo propeties file and sets the values in the getter & setter methods.
 * 
 * @author SaiJyoti Kamisetti
 * @since 30.11.2018
 * @version 1.0
 */

public class XenetaRequestDemoConfigReader {
	
	private Properties properties;
	private final String propertyFilePath= "config//XenetaRequestDemoConfig.properties";
	
	/**
	 * Method reads the properties file.
	 */
	public XenetaRequestDemoConfigReader(){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			properties.load(reader);
			reader.close();
		 } catch (FileNotFoundException fnfe) {
			 fnfe.printStackTrace();
			 throw new RuntimeException("XenetaRequestDemoConfig.properties is not found at " + propertyFilePath + fnfe.getMessage());
		 } catch (IOException ioe) {
			 ioe.printStackTrace();
		 }
	 }
	 
	 public String getBrowser(){
		 String browser = properties.getProperty("browser");
		 if(browser!= null) return browser;
		 else throw new RuntimeException("browser not specified in the XenetaRequestDemoConfig.properties file."); 
	 }
	 
	 public String getFirstname_l() { 
		 String firstname_locator = properties.getProperty("firstname_locator");
		 if(firstname_locator != null) return firstname_locator;
		 else throw new RuntimeException("firstname_locator not specified in the XenetaRequestDemoConfig.properties file."); 
	 }
	 
	 public String getLastname_l() {
		 String lastname_locator = properties.getProperty("lastname_locator");
		 if(lastname_locator != null) return lastname_locator;
		 else throw new RuntimeException("lastname_locator not specified in the XenetaRequestDemoConfig.properties file.");
	 }
	 
	 public String getCompany_l() {
		 String company_l = properties.getProperty("company_l");
		 if(company_l != null) return company_l;
		 else throw new RuntimeException("company_l not specified in the XenetaRequestDemoConfig.properties file.");
	 }
	 
	 public String getTitle_l() {
		 String title_l = properties.getProperty("title_l");
		 if(title_l != null) return title_l;
		 else throw new RuntimeException("title_l not specified in the XenetaRequestDemoConfig.properties file.");
	 }
	 
	 public String getEmail_l() {
		 String email_locator = properties.getProperty("email_locator");
		 if(email_locator != null) return email_locator;
		 else throw new RuntimeException("email_locator not specified in the XenetaRequestDemoConfig.properties file.");
	 }
	 
	 public String getPhone_l() {
		 String phone_l = properties.getProperty("phone_l");
		 if(phone_l != null) return phone_l;
		 else throw new RuntimeException("phone_l not specified in the XenetaRequestDemoConfig.properties file.");
	 }
	 
	 public String getEmailError_l() {
		 String email_error_l = properties.getProperty("email_error_l");
		 if(email_error_l != null) return email_error_l;
		 else throw new RuntimeException("email_error_l not specified in the XenetaRequestDemoConfig.properties file.");
	 }
	 
	 public String getDropdown1_l() {
		 String dropdown1_locator = properties.getProperty("dropdown1_locator");
		 if(dropdown1_locator != null) return dropdown1_locator;
		 else throw new RuntimeException("dropdown1_locator not specified in the XenetaRequestDemoConfig.properties file.");
	 }
	 
	 public String getDropdown2_l() {
		 String dropdown2_l = properties.getProperty("dropdown2_l");
		 if(dropdown2_l != null) return dropdown2_l;
		 else throw new RuntimeException("dropdown2_l not specified in the XenetaRequestDemoConfig.properties file.");
	 }
	 
	 public String getDropdown3_l() {
		 String dropdown3_l = properties.getProperty("dropdown3_l");
		 if(dropdown3_l != null) return dropdown3_l;
		 else throw new RuntimeException("dropdown3_l not specified in the XenetaRequestDemoConfig.properties file.");
	 }
	 
	 public String getCheckbox_l() {
		 String checkbox_l = properties.getProperty("checkbox_l");
		 if(checkbox_l != null) return checkbox_l;
		 else throw new RuntimeException("checkbox_l not specified in the XenetaRequestDemoConfig.properties file.");
	 }
	 
	 public String getButton_l() {
		 String button_l = properties.getProperty("button_l");
		 if(button_l != null) return button_l;
		 else throw new RuntimeException("button_l not specified in the XenetaRequestDemoConfig.properties file.");
	 }
	 
	 public String getDataError_l() {
		 String data_error_l = properties.getProperty("data_error_l");
		 if(data_error_l != null) return data_error_l;
		 else throw new RuntimeException("data_error_l not specified in the XenetaRequestDemoConfig.properties file.");
	 }
	 
	 public String getRequestConfirmation_l() {
		 String request_conf_l = properties.getProperty("request_conf_l");
		 if(request_conf_l != null) return request_conf_l;
		 else throw new RuntimeException("request_conf_l not specified in the XenetaRequestDemoConfig.properties file.");
	 }
	 
	 public String getVideo_l() {
		 String video_l = properties.getProperty("video_l");
		 if(video_l != null) return video_l;
		 else throw new RuntimeException("video_l not specified in the XenetaRequestDemoConfig.properties file.");
	 }
	 
	 public String getAlert_l() {
		 String alert_l = properties.getProperty("alert_l");
		 if(alert_l != null) return alert_l;
		 else throw new RuntimeException("alert_l not specified in the XenetaRequestDemoConfig.properties file.");
	 }

}
