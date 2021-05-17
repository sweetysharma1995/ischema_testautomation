package reusableComponents;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import testReportManager.ExtentTestManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.By;


/**
 * @Author: Sweety
 * Date created on Dec 9,2020
 */

public class ActionEngine {

    private WebDriver driver;
    WebDriverWait wait ;
    public ActionEngine(WebDriver driver) {
        // TODO Auto-generated constructor stub

        this.driver=driver;

    }

    //Customized sendkeys method-> To log sendkeys message for every occ.
    public void sendKeys_custom(WebElement element, String fieldName, String valueToBeSent) {
        try {
            element.sendKeys(valueToBeSent);
            //log success message in exgent report
            ExtentTestManager.getTest().log(Status.INFO,fieldName+"==> Entered value as: "+valueToBeSent);

        } catch (Exception e) {
            //log failure in extent

            ExtentTestManager.getTest().log(Status.PASS,fieldName+"==> Entered value as: "+valueToBeSent);
             }
    }



    //custom click method to log evey click action in to extent report
    public void click_custom(WebElement element, String fieldName) {
        try {
            System.out.println("Line 50 executed in actionengine");
            element.click();
            System.out.println("Line 52 executed in actionengine");
            //log success message in exgent report
            ExtentTestManager.getTest().log(Status.PASS, fieldName+"==> Clicked Successfully! ");
        } catch (Exception e) {
            //log failure in extent
            ExtentTestManager.getTest().log(Status.FAIL, "Unable to click on field: " +fieldName +" due to exception: "+e);
        }
    }
    //custom click method to log evey click action in to extent report
    public void click_Javascript_custom(WebElement element, String fieldName) {
        try {
            Utility.clickOnElement(element, driver);
            //log success message in exgent report
            ExtentTestManager.getTest().log(Status.PASS, fieldName+"==> Clicked Successfully using Javascript! ");
        } catch (Exception e) {
            //log failure in extent
            ExtentTestManager.getTest().log(Status.FAIL, "Unable to click on field using Javascript: " +fieldName +" due to exception: "+e);
        }
    }


    //clear data from field
    public void clear_custom(WebElement element,String fieldName) {
        try {
            element.clear();
            Thread.sleep(250);
            ExtentTestManager.getTest().log(Status.PASS, fieldName+"==> Data Cleared Successfully! ");
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "Unable to clear Data on field: " +fieldName +" due to exception: "+e);

        }
    }

    //custom mouseHover
    public void moveToElement_custom(WebElement element,String fieldName){
        try{
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].scrollIntoView(true);", element);
            Actions actions = new Actions(driver);
            actions.moveToElement(element).build().perform();
            ExtentTestManager.getTest().log(Status.PASS, fieldName+"==> Mouse hovered Successfully! ");
            Thread.sleep(1000);
        }catch(Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Unable to hover mouse on field: " +fieldName +" due to exception: "+e);

        }
    }


    //check if element is Present
    public boolean isElementPresent_custom(WebElement element,String fieldName){
        boolean flag = false;
        try {
            flag = element.isDisplayed();
            ExtentTestManager.getTest().log(Status.PASS, fieldName+"==> Presence of field is: "+ flag);
            return flag;
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "Checking for presence of field: " +fieldName +" not tested due to exception: "+e);
            return flag;
        }
    }


    //Select dropdown value value by visibleText
    public void selectDropDownByVisibleText_custom(WebElement element, String fieldName, String ddVisibleText) {
        try {
            Select s = new Select(element);
            s.selectByVisibleText(ddVisibleText);
            ExtentTestManager.getTest().log(Status.PASS, fieldName+"==> Dropdown Value Selected by visible text: "+ ddVisibleText);
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "Dropdown value not selected for field: " +fieldName +"  due to exception: "+e);
        }
    }

    //Select dropdown value value by value
    public void selectDropDownByValue_custom(WebElement element, String fieldName, String ddValue){
        try {
            Select s = new Select(element);
            s.selectByValue(ddValue);
            ExtentTestManager.getTest().log(Status.PASS, fieldName+"==> Dropdown Value Selected by visible text: "+ ddValue);
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "Dropdown value not selected for field: " +fieldName +"  due to exception: "+e);
        }
    }

    //String Asserts
    public void assertEqualsString_custom(String expvalue, String actualValue, String locatorName){
     
            if(actualValue.equals(expvalue)) {
                ExtentTestManager.getTest().log(Status.PASS, "String Assertion is successful on field "+ locatorName + " Expected value was: "+ expvalue + " actual value is: "+actualValue);
            }else {
                ExtentTestManager.getTest().log(Status.FAIL, "String Assertion FAILED on field "+ locatorName + " Expected value was: "+ expvalue + " actual value is: "+actualValue);
               
            }
        
    }
    
    //String Asserts
    public void assertContainsString_custom(String expvalue, String actualValue, String locatorName){
       
            if(actualValue.contains(expvalue)) {
                ExtentTestManager.getTest().log(Status.PASS, "String Assertion is successful on field "+ locatorName + " Expected value was: "+ expvalue + " actual value is: "+actualValue);
            }else {
                ExtentTestManager.getTest().log(Status.FAIL, "String Assertion FAILED on field "+ locatorName + " Expected value was: "+ expvalue + " actual value is: "+actualValue);
                
            }
     
    }
    //Boolean Asserts
    public void assertEqualsBoolean_custom(Boolean value, String successmessage, String errormessage){
        try {
            Assert.assertTrue(value);
            ExtentTestManager.getTest().log(Status.PASS, "Success Message is==> " +successmessage);


        } catch (AssertionError e) {
            ExtentTestManager.getTest().log(Status.FAIL, "Error Message is==>  "+errormessage);


        }
    }

    //Get text from webelement
    public String getText_custom(WebElement element, String fieldName) {
        String text = "";
        try {
            text = element.getText();
            ExtentTestManager.getTest().log(Status.PASS, fieldName+"==> Text retrived is: "+ text);
            return text;
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, fieldName+"==> Text not retrived due to exception: "+ e);

        }
        return text;
    }



   // get Alert text

    public String getAlertText_custom() {
        String text = "";
        try {
            text = driver.switchTo().alert().getText();

            driver.switchTo().alert().accept();
            driver.switchTo().defaultContent();
            ExtentTestManager.getTest().log(Status.PASS, "==> Text retrived is: "+ text);
            return text;
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "==> Text not retrived due to exception: "+ e);

        }
        return text;
    }

    //Wait for presence of element
   public void waitForElementToBePresent(By element){
        ExtentTestManager.getTest().log(Status.INFO, "==> wait element to be present : "+ element);
        wait = new WebDriverWait(driver, 10 /*timeout in seconds*/);
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
       // wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(driver.findElement(element))));

    }
    //Wait for visibility of element
    public void waitForElementToBeVisible(WebElement element){
        ExtentTestManager.getTest().log(Status.INFO, "==> wait element to be visible         : "+ element);
        wait = new WebDriverWait(driver, 10 /*timeout in seconds*/);
        wait.until(ExpectedConditions.visibilityOf(element));


    }




    public boolean isAlertPresent(){
        boolean foundAlert = false;
         wait = new WebDriverWait(driver, 5/*timeout in seconds*/);
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            foundAlert = true;


        } catch (TimeoutException eTO) {
            foundAlert = false;

        }
        return foundAlert;
    }
}
