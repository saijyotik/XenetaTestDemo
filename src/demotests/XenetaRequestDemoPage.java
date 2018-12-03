package demotests;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import configreader.XenetaRequestDemoConfigReader;

/**
 * This class consists of automation test cases for the Xeneta Web Page, "Request Demo".
 * 
 * @author SaiJyoti Kamisetti
 * @since 27.11.2018
 * @version 1.0
 */

@Listeners(listeners.ListenerTestNG.class)
public class XenetaRequestDemoPage {
	
	//Browser Driver object
	WebDriver driver = null;
	//Properties Reader class
	XenetaRequestDemoConfigReader configreader = new XenetaRequestDemoConfigReader();
	
	/**
	 * This method creates the web browser driver. For this demo,
	 * only Firefox driver is being used. For Chrome & IE, the drivers are 
	 * not provided by default and hence have to be downloaded & copied to 
	 * a directory and then used in the tests.
	 */
	@BeforeMethod
	@Parameters({"url"})
	public void initDriver(String url) {
		
		//Firefox
		driver = new FirefoxDriver();
		
		/* not implemented now - but this method can be refactored as a factory class if required 
		//Chrome
		//System.setProperty("webdriver.chrome.driver", "c://chromedriver.exe");
		//driver = new ChromeDriver();
		
		//IE
		//System.setProperty("webdriver.ie.driver", "c://IEDriverServer.exe");
		//driver = new InternetExplorerDriver();
		*/
		
		driver.get(url);
		driver.manage().window().maximize();
	}
	
	/**
	 * This test takes a screenshot of the fully loaded webpage and
	 * saves at a directory pre-defined in the properties file.
	 * This is to have a post test refernce of the page snapshot on 
	 * which all tests are run.
	 * 
	 * @param screenshot_loc
	 * @throws Exception 
	 */
	@Test(enabled=true, priority=1)
	@Parameters({"screenshot_loc"})
	public void testTakeScreenshot(String screenshot_loc) throws Exception {
		
		File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		try {
		    // now copy the  screenshot to desired location using copyFile method
		    FileUtils.copyFile(src, new File(screenshot_loc+System.currentTimeMillis() + "_DemoRequest.png")); 
		} 
		catch (IOException ioe) {
		    Reporter.log("Error in testTakeScreenshot: " + ioe.getMessage());
			System.out.println(ioe.getMessage());
		}
	}
	
	/**
	 * This method tests the section, "Request Your Demo Now" for following:
	 * 1. Working of text field - "FIRST NAME" (not checking all text fields)
	 * 2. Working of drop down select field - "I AM A"(not checking all drop down elements)
	 * 3. Working of check box - ""Subscribe to Xeneta Industry Blog"
	 * 4. Working of "SUBMIT" button
	 * 5. A error message is provided in case of incomplete fields
	 * 
	 * @param fname
	 * @throws Exception
	 */
	@Test(enabled=true, priority=2)
	@Parameters({"fname"})
	public void testFillForm(String fname) throws Exception{
		/* Steps to perform:
	      1. It identifies FIRST NAME field and enters value as passed in parameter.
	      2. Select First Option of type of prospect drop down.
	      3. Click Subscribe Check Box.
	      4. Click Submit button.
	      5. Verifies if required error message for incomplete fields is displayed. */
		
		try{	
			
			//Enter first name field.
			WebElement tab = driver.findElement(By.name(configreader.getFirstname_l()));
			tab.sendKeys(fname);
			
			driver.manage().timeouts().implicitlyWait(500, TimeUnit.SECONDS);	
			
			//Select drop down list
			WebElement dropdown_1 = driver.findElement(By.name(configreader.getDropdown1_l()));
			Select select_option = new Select(dropdown_1);
			select_option.selectByIndex(1);
			
			//Select checkbox
			WebElement checkbox = driver.findElement(By.xpath(configreader.getCheckbox_l()));
			//It verifies if check box is enabled to click.
			if(checkbox.isEnabled()) {
				checkbox.click();
			} 
			else {
				Assert.assertTrue("Subscribe Checkbox is disabled", false);
			}
			
			//Select button to submit
			WebElement button = driver.findElement(By.xpath(configreader.getButton_l()));
			//It verifies if submit button is enabled to click.
			if(button.isEnabled()) {
				button.click();
			} 
			else {
				Assert.assertTrue("SUBMIT button is disabled", false);
			}
			
			//To verify the required data entry error.
			WebElement error = driver.findElement(By.xpath(configreader.getDataError_l()));
		}		
		catch(NoSuchElementException nse) {
			Reporter.log("Element not found in page: " + nse.getMessage());
			Assert.assertTrue("Submit button is disabled", false);
		}		
		catch(ElementNotVisibleException e) {
			Reporter.log("Element didnot load");
			Assert.assertTrue("Element didnot load", false);
		}
		
	}
	
	/**
	 * This test verifies the email field format check by providing a wrong email.
	 * Steps to perform:
	 * 1. Identify email field.
	 * 2. Enter invalid format email-id.
	 * 3. Check for user email error.
	 * 
	 * @throws Exception
	 */
	@Test(enabled=true, priority=3)
	public void testEmailField() throws Exception{
		
		try {
			WebElement tab = driver.findElement(By.name(configreader.getEmail_l()));
			
			//Provided invalid format email.
			tab.sendKeys("abcd#123_com");
					
			//Verifies User Email Error.
			WebElement error = driver.findElement(By.xpath(configreader.getEmailError_l()));
		}
		catch(NoSuchElementException nse) {
			Reporter.log("Element/Error not found in page: " + nse.getMessage());
			Assert.assertTrue("Element/Error not found in page: " + nse.getMessage(), false);
		}
	}
	
	/**
	 * This test is to fill the Request Demo request form, submit and verify the submission page.
	 * Steps to perform:
     * 1. Provide value to all text fields of form as passed through parameters.
	 * 2. Select first option in drop down fields.
	 * 3. Click check box.
	 * 4. Click submit button.
	 * 5. Wait for submission to complete and message to appear on page.
	 * 
	 * @param fname
	 * @param lname
	 * @param cname
	 * @param title
	 * @param email
	 * @param phone
	 * @throws Exception
	 */
	@Test(enabled=true, priority=4)
	@Parameters({"fname", "lname", "cname", "title", "email", "phone"})
	public void testRequestDemo(String fname, String lname, String cname, 
			String title, String email, String phone) throws Exception{

		try{				
			//Enter values in all text fileds.
			driver.findElement(By.name(configreader.getFirstname_l())).sendKeys(fname);
			
			driver.findElement(By.name(configreader.getLastname_l())).sendKeys(lname);
			
			driver.findElement(By.name(configreader.getCompany_l())).sendKeys(cname);
			
			driver.findElement(By.name(configreader.getTitle_l())).sendKeys(title);
			
			driver.findElement(By.name(configreader.getEmail_l())).sendKeys(email);
			
			driver.findElement(By.name(configreader.getPhone_l())).sendKeys(phone);
			
			//Select first option in all three drop down fields.
			WebElement dropdown_1 = driver.findElement(By.name(configreader.getDropdown1_l()));
			Select select_option_1 = new Select(dropdown_1);
			select_option_1.selectByIndex(1);
			
			WebElement dropdown_2 = driver.findElement(By.name(configreader.getDropdown2_l()));
			Select select_option_2 = new Select(dropdown_2);
			select_option_2.selectByIndex(1);
			
			WebElement dropdown_3 = driver.findElement(By.name(configreader.getDropdown3_l()));
			Select select_option_3 = new Select(dropdown_3);
			select_option_3.selectByIndex(1);
						
			// select checkbox
			WebElement checkbox = driver.findElement(By.xpath(configreader.getCheckbox_l()));
			checkbox.click();
					
					
			// Click to submit
			WebElement button = driver.findElement(By.xpath(configreader.getButton_l()));
			button.click();
			
			//Wait for submission completion message to appear on page.
			WebDriverWait wait = new WebDriverWait(driver,100);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(configreader.getRequestConfirmation_l())));
						
			System.out.println("Submitted: " + driver.findElement(By.xpath(configreader.getRequestConfirmation_l())).getText());
		}		
		catch(NoSuchElementException nse) {
			Reporter.log("Element not found in page: " + nse.getMessage());
			Assert.assertTrue("Element not found in page: " + nse.getMessage(), false);
		}		
		catch(ElementNotVisibleException enve) {
			Reporter.log("Element did not load: " + enve.getMessage());
			Assert.assertTrue("Element did not load: " + enve.getMessage(), false);
		}
		
	}
	
	/**
	 * This test is to identify if the video player element is loaded fully and
     * simulate the play and pause action.
     * Steps to perform:
     * 1. Identify video player element.
     * 2. Click on the video frame to play.
     * 3. Click on the video frame to pause.
     * 
	 * @throws InterruptedException
	 */
	@Test(enabled=true, priority=5)
	public void testPlayVideo() throws InterruptedException {
		
		try {
			driver.findElement(By.xpath(configreader.getVideo_l())).click();
			
			driver.manage().timeouts().implicitlyWait(500, TimeUnit.SECONDS);	
			
			driver.findElement(By.xpath(configreader.getVideo_l())).click();
		}
		catch(NoSuchElementException nse) {
			Reporter.log("Element not found in page: " + nse.getMessage());
			Assert.assertTrue("Element not found in page: " + nse.getMessage(), false);
		}		
		catch(ElementNotVisibleException enve) {
			Reporter.log("Element did not load: " + enve.getMessage());
			Assert.assertTrue("Element did not load: " + enve.getMessage(), false);
		}
		catch (Exception e) {
			Reporter.log("Error in playing video: " + e.getMessage());
		}
	}
	
	/**
	 * This test verifies the phone field format check by providing a alphanumeric.
	 * Steps to perform:
	 * 1. Identify phone field.
	 * 2. Enter alphanumeric email-id.
	 * 3. Check for user email error.
	 * 
	 * @throws Exception
	 */
	@Test(enabled=true, priority=6)
	public void testPhoneField() throws Exception{
		
		try {
			WebElement tab = driver.findElement(By.name(configreader.getPhone_l()));
			
			//Provided invalid format email.
			tab.clear();
			tab.sendKeys("abcd#123_com");
						
			//Verifies User Email Error.
			WebElement error = driver.findElement(By.xpath(configreader.getAlert_l()));
			
		}
		catch(NoSuchElementException nse) {
			Reporter.log("Element/Error not found in page: " + nse.getMessage());
			Assert.assertTrue("Element/Error not found in page: " + nse.getMessage(), false);
		}
	}
	
	
	/**
	 * This method closes the driver after every test is run.
	 */
	@AfterMethod
	public void cleanup() {
		driver.close();
	}
	
	
}
