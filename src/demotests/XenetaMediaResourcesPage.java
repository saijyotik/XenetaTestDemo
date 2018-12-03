package demotests;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import configreader.XenetaMediaResourceConfigReader;
import mx4j.log.Log;

/**
 * This class consists of automation test cases for the Xeneta Web Page, "Media Resources".
 * 
 * @author SaiJyoti Kamisetti
 * @since 27.11.2018
 * @version 1.0
 */

@Listeners(listeners.ListenerTestNG.class)
public class XenetaMediaResourcesPage {
	
	//Browser Driver object
	WebDriver driver = null;
	//Properties Reader class
	XenetaMediaResourceConfigReader configreader = new XenetaMediaResourceConfigReader();

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
		    FileUtils.copyFile(src, new File(screenshot_loc + System.currentTimeMillis() + "_MediaResources.png"));		       
		}
		catch (IOException ioe) {
		    Reporter.log("Error in testTakeScreenshot: " + ioe.getMessage());
			System.out.println(ioe.getMessage());
		}
	}
	
	/**
	 * This method opens the web page, navigates to one of the tab(section), clicks the tab and
	 * the page scrolls down to that section. Out of the 4 tabs in the page, any tab could be 
	 * selected based on the tab no given in the properties file.
	 * @throws Exception
	 */
	@Test(enabled=true, priority=2)
	public void testOpenWebPage() throws Exception {
		
		try {
			WebElement tab, newtag;
			System.out.println("config:" + configreader.getTabLocator());
			tab = driver.findElement(By.xpath(configreader.getTabLocator()));
			tab.click();
			
			newtag = driver.findElement(By.xpath(configreader.getSectionTitle()));
			System.out.println(newtag.getText());
			
			Assert.assertEquals(newtag.getText(), tab.getText());
		}
		//The required tab element is not present.
		catch (NoSuchElementException nse) {
			Reporter.log("Element not found in page: " + nse.getMessage());
			Assert.assertTrue(nse.getMessage(), false);
		}
		//If the required section of page is not loaded.
		catch (ElementNotVisibleException enve) {
			Reporter.log("Element did not load: " + enve.getMessage());
			Assert.assertTrue(enve.getMessage(), false);
		}	
	}
	
	/**
	 * This test is to identify the scroll bar and navigate till end of page.
	 * It identifies the scroll bar element, selects it and scrolls till the end of page.
	 * @throws Exception
	 */
	@Test(enabled=true, priority=3)
	public void testScrollWebPage() throws Exception {
		
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0, document.body.scrollHeight)");	
		}
		catch(NoSuchElementException nse){
			Reporter.log("Scroll bar does not exist: " + nse.getMessage());
			Assert.assertTrue(nse.getMessage(), false);
		}
		
	}
	
	/**
	 * This test is to verify the 'language' option of the webpage.
	 * Steps to perform:
	 *	1. It identifies the language option element.
	 *  2. Clicks on it.
	 *	3. It waits for the web page to load in the new language.
	 *	4. Verifies if the title of page is in the new language.
	 *
	 * @param language
	 * @param l_string
	 */
	@Test(enabled=true, priority=4)
	@Parameters({"language", "l_string"})
	public void testLanguage(String language, String l_string) throws Exception {
		
		try {
			WebElement lang = driver.findElement(By.linkText(language));
			lang.click();
			
			driver.manage().timeouts().implicitlyWait(500, TimeUnit.SECONDS);
			Assert.assertEquals(driver.getTitle(), l_string);
		}
		//The required tab element is not present.
		catch (NoSuchElementException nse) {
			Reporter.log("Element not found in page: " + nse.getMessage());
			Assert.assertTrue(nse.getMessage(), false);
		}
		
	}
	
	/**
	 * This test is to verify the screenshot image (thumbnail) of the web page.
	 *	Steps to perform:
	 *	1. It identifies one of the screenshot image on the webpage.
	 *	2. Clicks on the image.
	 *	3. A popup of the image comes up.
	 *	4. Clicks on the popup image.
	 *	5. A window opens with the enlarged image of the screenshot.
	 *
	 * @throws Exception
	 */
	@Test(enabled=true, priority=5)
	public void testImageThumbnail() throws Exception{
		
		try {
			//It identifies screenshot image element on page.
			WebElement image = driver.findElement(By.xpath(configreader.getImageLocator()));
		    image.click();
		
		    //It waits for image pop up to appear and click on it.
			driver.manage().timeouts().implicitlyWait(500, TimeUnit.SECONDS);
			driver.findElement(By.xpath(configreader.getImagePopupLocator())).click();
		
			//To save main web page window handle and get window handles of new windows.	
			String parent = driver.getWindowHandle();
			Set<String> s1 = driver.getWindowHandles();
			
			//Now we will iterate using Iterator through all window handles
			//in order to move control to newly opened child window.
			Iterator<String> itr = s1.iterator();
		
			while(itr.hasNext())
			{
			   String child_window = itr.next();
		
				//It will compare if parent window is not equal to child window and
				//move to child window.It will print new window title.
				if(!parent.equals(child_window))
				{
					driver.switchTo().window(child_window);
					System.out.println(driver.switchTo().window(child_window).getTitle());
				}
			}
		}
		catch(NoSuchElementException nse) {
			Reporter.log("Element not found in page: " + nse.getMessage());
			Assert.assertTrue(nse.getMessage(), false);
		}
		catch(ElementNotVisibleException enve) {
			Reporter.log("Element did not load: " + enve.getMessage());
			Assert.assertTrue(enve.getMessage(), false);
		}
		catch(Exception e) {
			Reporter.log("Error in testImageThumbnail : " + e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
		}
	}
	
	/**
	 * This method closes the driver after every test is run.
	 */
	@AfterMethod
	public void cleanup() {
		driver.quit();
	}
	
	/**
	 * This method tests the Download button functionality on the Request Demo page.
	 * It verifies if the 'File Open' popup appears, after the Download button is clicked. 
	 * 
	 * @throws Exception
	 */
	
	@Test(enabled=true, priority=6)
	@Parameters({"screenshot_loc"})
	public void testDownload(String screenshot_loc) throws Exception {
		
		try {
		
			WebElement tab = driver.findElement(By.xpath("//a[contains(@href,'#xeneta-logo')]"));
	    
			tab.click();
			Thread.sleep(90);
			
			WebDriverWait wait = new WebDriverWait(driver,90);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[(text() = 'Download')]")));
			driver.findElement(By.xpath("//a[(text() = 'Download')]")).click();
			
			//File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			//FileUtils.copyFile(src, new File(screenshot_loc + System.currentTimeMillis() + "_MediaResources_Download.png"));
		}
		catch(TimeoutException et){	
			Reporter.log("Time out, could not find element: " + et.getMessage());
			Assert.assertTrue(et.getMessage(), false);	
		}	
	}
	
}
