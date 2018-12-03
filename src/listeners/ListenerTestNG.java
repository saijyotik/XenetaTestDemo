package listeners;
 
import org.testng.ITestContext; 
import org.testng.ITestListener; 
import org.testng.ITestResult; 
 
public class ListenerTestNG implements ITestListener 
{ 
 
	@Override 
    public void onStart(ITestContext testCtx) 
    { 
    	System.out.println("\n**** Begin of tests for " + testCtx.getName() + " page ****\n");
    }
	
	@Override 
    public void onFinish(ITestContext testCtx) 
    { 
    	System.out.println("\n**** End of tests for " + testCtx.getName() + " page 	****\n");
    } 
 
    @Override 
    public void onTestFailedButWithinSuccessPercentage(ITestResult testResult) 
    { 
     
    } 
 
    // When Test case get failed, this method is called. 
    @Override 
    public void onTestFailure(ITestResult testResult) 
    { 
    	System.out.println("The name of the testcase failed is : " + testResult.getName()); 
    } 
 
    // When Test case get Skipped, this method is called. 
    @Override 
    public void onTestSkipped(ITestResult testResult) 
    { 
    	System.out.println("The name of the testcase skipped is : " + testResult.getName()); 
    } 
 
    @Override 
    public void onTestStart(ITestResult testResult) 
    { 
   
    } 
 
    // When Test case get passed, this method is called. 
    @Override 
    public void onTestSuccess(ITestResult testResult) 
    { 
    	System.out.println("The name of the testcase passed is : " + testResult.getName()); 
    } 
 
}