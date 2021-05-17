package testBase;


import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import org.apache.commons.codec.binary.Base64;

import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

import reusableComponents.ActionEngine;
import reusableComponents.PropertiesOperations;
import testReportManager.ExtentTestManager;


public class TestBase {
    public WebDriver driver ;
    public BrowserFactory bf;
    public ActionEngine actionEngine;

    By btnSave = By.id("MainContent_ChildContent_btnSave");
    By btnDraft = By.xpath("//input[@value='Draft']");
    By btnEdit = By.xpath("//input[@value='Edit']");
    By btnNext = By.xpath("//input[@value='Next']");
    By btnClose = By.xpath("//input[@value='Close']");
    By btnSearch = By.xpath("//input[@value='Search']");

    By btnDelete = By.xpath("//input[@value='Delete']");
    public static String alertValue="";

    public void setup() throws Exception{

          bf = new BrowserFactory();


        DriverFactory.getInstance().setDriver(bf.createBrowserInstance(PropertiesOperations.getPropertyValueByKey("browser")));
        driver = DriverFactory.getInstance().getDriver();

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
        // driver.get(PropertiesOperations.getPropertyValueByKey("baseURL"));
        driver.get(System.getProperty("appURL"));



        actionEngine = new ActionEngine(driver);

       Robot rb = new Robot();
        Thread.sleep(5000);
       String decodedUserName="R0xPQkFMVEVTVFxzbmVoYWNhZG1pbg==";
       String EncodeddecodedUserName=new String(Base64.decodeBase64(decodedUserName));

        //String Uname = PropertiesOperations.getPropertyValueByKey("uname");
        StringSelection user = new StringSelection(EncodeddecodedUserName);

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(user,null);
        Thread.sleep(5000);
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(5000);

        //
        rb.keyPress(KeyEvent.VK_TAB);
        rb.keyRelease(KeyEvent.VK_TAB);


        //
       String decodepassword="U3RyaW5nJTEyMzQ1";
       String EncodedPassword=new String(Base64.decodeBase64(decodepassword));

        // String Pname = PropertiesOperations.getPropertyValueByKey("password");
        StringSelection pwd = new StringSelection(EncodedPassword);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(pwd,null);
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(5000);


        //
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(5000);



    }
//@aashi

    public void Delete() throws Exception {
        actionEngine.click_custom(driver.findElement(btnDelete),"Delete Button");
        Thread.sleep(2000);
        //alertValue = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        Thread.sleep(2000);
        alertValue = driver.switchTo().alert().getText();
        driver.switchTo().defaultContent();
        Thread.sleep(1000);
    }

    public void CustomDelete() throws Exception {
        actionEngine.click_custom(driver.findElement(btnDelete),"Delete Button");
        Thread.sleep(2000);
        //alertValue = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        Thread.sleep(2000);
       // alertValue = driver.switchTo().alert().getText();
        driver.switchTo().defaultContent();
        Thread.sleep(1000);
        //return alertValue;
    }


    public void Draft() throws Exception {
        Thread.sleep(2000);
        actionEngine.click_custom(driver.findElement(btnDraft),"Draft Button");

        Thread.sleep(5000);
        driver.switchTo().alert().accept();
        driver.switchTo().defaultContent();

    }

    public void Save() throws Exception {
        Thread.sleep(2000);
        actionEngine.click_custom(driver.findElement(btnSave),"Save Button");

        Thread.sleep(2000);
        alertValue = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        Thread.sleep(2000);
        driver.switchTo().defaultContent();
    }

    public void btnSave() throws Exception {

        actionEngine.click_custom(driver.findElement(btnSave),"Save Button");
        Thread.sleep(2000);

    }

    public void Edit() throws Exception {
        Thread.sleep(2000);
        actionEngine.click_custom(driver.findElement(btnEdit),"Edit Button");

        Thread.sleep(2000);

    }
    public void Next() throws Exception {

        Thread.sleep(2000);
        actionEngine.click_custom(driver.findElement(btnNext),"Next Button");
        Thread.sleep(2000);
    }

    public void Search() throws Exception {

        Thread.sleep(5000);
        actionEngine.click_custom(driver.findElement(btnSearch),"Search Button");
        Thread.sleep(5000);
    }


    public void tearDown(){

        DriverFactory.getInstance().closeDriver();
        try {
           Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
           Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
            Thread.sleep(10000);
        }
        catch (Exception e){

            ExtentTestManager.getTest().log(Status.FAIL,"Error occurred while killing the browser instance"+e.getMessage());
        }


    }
}
