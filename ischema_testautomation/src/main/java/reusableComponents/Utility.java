package reusableComponents;

import java.util.Random;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import org.testng.annotations.DataProvider;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import testReportManager.ExtentTestManager;

public class Utility {

public static String setTimestamp () {
	   Date date = new Date();
	      //This method returns the time in millis
	      long timeMilli = date.getTime();
	      //System.out.println("Time in milliseconds using Date class: " + timeMilli);

	return Long.toString(timeMilli) ;
	
	
	
}


public static String extractInt(String str) 
{ 
    // Replacing every non-digit number 
    // with a space(" ") 
    str = str.replaceAll("[^\\d]", " "); 

    // Remove extra spaces from the beginning 
    // and the ending of the string 
    str = str.trim(); 

    // Replace all the consecutive white 
    // spaces with a single space 
    str = str.replaceAll(" +", " "); 

    if (str.equals("")) 
        return "-1"; 

    return str; 
} 

public static ArrayList<String>  readDataFromWebTable(WebElement webtable,WebDriver driver) {
	//List<ArrayList<String>> rowsData = new ArrayList<ArrayList<String>>();
	ArrayList<String> rowsData = new ArrayList<String>();
	 
List<WebElement> rows_table = webtable.findElements(By.tagName("tr"));
//To calculate no of rows In table.
int rows_count = rows_table.size();
//Loop will execute till the last row of table.
for (int row=0; row<rows_count; row++){
//To locate columns(cells) of that specific row.
List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));

//To calculate no of columns(cells) In that specific row.
int columns_count = Columns_row.size();
System.out.println("Number of cells In Row "+row+" are "+columns_count);
//ArrayList<String> rowData = new ArrayList<String>();
ArrayList<String> rowData = new ArrayList<String>();

	//Loop will execute till the last cell of that specific row.
	for (int column=0; column<columns_count; column++){
	//To retrieve text from that specific cell.
		scrollToElement(Columns_row.get(column),driver);
		String celtext = Columns_row.get(column).getText();
		
	//System.out.println("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
		rowData.add(celtext);
	}
rowsData.addAll(rowData);

}
return rowsData;	

}

public static void  scrollToElementYAxis(WebDriver driver) {

		((JavascriptExecutor)driver).executeScript("window.scrollBy(0,1000)");
}

public static void  scrollToElement(WebElement elt,WebDriver driver) {
	
	((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", elt);
}

public static void  clickOnElement(WebElement elt,WebDriver driver) {
	
	((JavascriptExecutor)driver).executeScript("arguments[0].click()", elt);
}


public static boolean isFileDownloaded( String fileName) {
		boolean flag = false;

		File dir = new File(Constants.DownloadLocation);
		
		File[] files = dir.listFiles();
		
		if (files == null || files.length == 0) {
			flag = false;

		}

		for (int i = 0; i < files.length; i++) {
			
			if(files[i].getName().contains(fileName)) {

				flag=true;

			}
		}
		return flag;
	}
	public static void deleteFileIfExists(String FileName) {
		//New Code
		try {
			File dir = new File(Constants.DownloadLocation);
			File destination =  new File(Constants.DeleteLocation+System.getProperty("file.separator")+"Report"+Utility.setTimestamp()+".xls");

			if (dir.isDirectory()) {
				File[] files = dir.listFiles();
				if (files != null && files.length > 0) {
					for (File aFile : files) {
						if(aFile.getName().contains(FileName)){
							System.gc();
							Thread.sleep(5000);

							aFile.renameTo(destination);

							Thread.sleep(7000);
							if (aFile.exists()) {

								ExtentTestManager.getTest().log(Status.FAIL, " File not deleted at the location ==>"+Constants.DownloadLocation+"File Name is ==>"+aFile.getName());

							}
							else{
								ExtentTestManager.getTest().log(Status.PASS, " File deleted at the location ==>"+Constants.DownloadLocation+"File Name is ==>"+aFile.getName());

							}


						}

					}
				}

			}
			else{
				dir.delete();
				if (dir.exists()) {

					ExtentTestManager.getTest().log(Status.FAIL, " File not deleted at the location ==>"+Constants.DownloadLocation+"File Name is ==>"+dir.getName());

				}
				else{
					ExtentTestManager.getTest().log(Status.PASS, " File deleted at the location ==>"+Constants.DownloadLocation+"File Name is ==>"+dir.getName());

				}

			}
		} catch (Exception e) {
			ExtentTestManager.getTest().log(Status.FAIL, " Error occurred while deleting the file ==>"+e.getMessage());

		}

	}


	public static void download_template(){


		StringSelection stringSelection = new StringSelection(Constants.DownloadLocation);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);

		Robot r;
		try {
			Thread.sleep(4000);
			 r = new Robot();

			// Open the Pop Up Alt N,
			r.keyPress(KeyEvent.VK_ALT);
			r.keyPress(KeyEvent.VK_N);

			r.keyRelease(KeyEvent.VK_N);
			r.keyRelease(KeyEvent.VK_ALT);
			Thread.sleep(4000);

			r.keyPress(KeyEvent.VK_TAB);
			r.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(4000);

			r.keyPress(KeyEvent.VK_DOWN);
			r.keyRelease(KeyEvent.VK_DOWN);
			Thread.sleep(4000);

			r.keyPress(KeyEvent.VK_DOWN);
			r.keyRelease(KeyEvent.VK_DOWN);
			Thread.sleep(4000);

			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);

			Thread.sleep(7000);
			// Reach to the URL bar
			for (int i = 0; i < 6; i++) {
				r.keyPress(KeyEvent.VK_TAB);
				r.keyRelease(KeyEvent.VK_TAB);
				Thread.sleep(2000);
			}
			// Paste the copied default ULR
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(4000);
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_CONTROL);
			// Save the file
			for (int i = 0; i < 5; i++) {
				Thread.sleep(2000);
				r.keyPress(KeyEvent.VK_ENTER);
			}

		} catch (AWTException e) {
			ExtentTestManager.getTest().log(Status.FAIL, " Error occured ==>" +e.getMessage());

		} catch (InterruptedException e) {
			ExtentTestManager.getTest().log(Status.FAIL, " Error occured ==>" +e.getMessage());

		}
	}

	public static void download_Firstbutton(){

		Random rand = new Random();
		int  randomNumber = rand.nextInt(9000000) + 1000000;
		String FileName = "Downloaded_" + randomNumber;
		StringSelection stringSelection = new StringSelection(FileName);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);

		Robot r;
		try {
			Thread.sleep(4000);
			r = new Robot();

			// Open the Pop Up Alt N,
			r.keyPress(KeyEvent.VK_ALT);
			r.keyPress(KeyEvent.VK_N);

			r.keyRelease(KeyEvent.VK_N);
			r.keyRelease(KeyEvent.VK_ALT);
			Thread.sleep(4000);

			r.keyPress(KeyEvent.VK_TAB);
			r.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(4000);

			r.keyPress(KeyEvent.VK_DOWN);
			r.keyRelease(KeyEvent.VK_DOWN);
			Thread.sleep(4000);

			r.keyPress(KeyEvent.VK_DOWN);
			r.keyRelease(KeyEvent.VK_DOWN);
			Thread.sleep(4000);

			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);

			Thread.sleep(7000);
			// Reach to the URL bar
//			for (int i = 0; i < 6; i++) {
//				r.keyPress(KeyEvent.VK_TAB);
//				r.keyRelease(KeyEvent.VK_TAB);
//				Thread.sleep(2000);
//			}
			// Paste the copied default ULR
//			r.keyPress(KeyEvent.VK_ENTER);
//			r.keyRelease(KeyEvent.VK_ENTER);
//			Thread.sleep(4000);
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_CONTROL);
//			r.keyPress(KeyEvent);
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
//			r.keyPress(KeyEvent.VK_ENTER);
//			r.keyRelease(KeyEvent.VK_ENTER);
//			// Save the file
//			for (int i = 0; i < 5; i++) {
//				Thread.sleep(2000);
//				r.keyPress(KeyEvent.VK_ENTER);
//			}

		} catch (AWTException e) {
			ExtentTestManager.getTest().log(Status.FAIL, " Error occured ==>" +e.getMessage());

		} catch (InterruptedException e) {
			ExtentTestManager.getTest().log(Status.FAIL, " Error occured ==>" +e.getMessage());

		}
	}

//	public static void download_ViewTemplate(){
//
//
////		StringSelection stringSelection = new StringSelection(Constants.DownloadLocation);
////		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
////		clipboard.setContents(stringSelection, stringSelection);
//
//		Robot r;
//		try {
////			Thread.sleep(4000);
//			r = new Robot();
//
//			r.keyPress(KeyEvent.VK_ALT);
//			Thread.sleep(2000);
//			r.keyPress(KeyEvent.VK_N);
//
//			r.keyRelease(KeyEvent.VK_N);
//			r.keyRelease(KeyEvent.VK_ALT);
//			Thread.sleep(10000);
//
//			r.keyPress(KeyEvent.VK_TAB);
//			r.keyRelease(KeyEvent.VK_TAB);
//
////			r.keyPress(KeyEvent.VK_ALT);
////			Thread.sleep(2000);
////			r.keyPress(KeyEvent.VK_N);
//
////			r.keyRelease(KeyEvent.VK_N);
////			r.keyRelease(KeyEvent.VK_ALT);
////			r.keyPress(KeyEvent.VK_TAB);
////			r.keyRelease(KeyEvent.VK_TAB);
////			r.keyPress(KeyEvent.VK_TAB);
////			r.keyRelease(KeyEvent.VK_TAB);
////			r.keyPress(KeyEvent.VK_TAB);
////			r.keyRelease(KeyEvent.VK_TAB);
////
////
//			Thread.sleep(4000);
////
////			r.keyPress(KeyEvent.VK_ENTER);
////			r.keyRelease(KeyEvent.VK_ENTER);
//
////			Thread.sleep(7000);
////			// Reach to the URL bar
////			for (int i = 0; i < 6; i++) {
////				r.keyPress(KeyEvent.VK_TAB);
////				r.keyRelease(KeyEvent.VK_TAB);
////				Thread.sleep(2000);
////			}
//			// Paste the copied default ULR
//			r.keyPress(KeyEvent.VK_ENTER);
//			r.keyRelease(KeyEvent.VK_ENTER);
//			Thread.sleep(10000);
//
////			r.keyPress(KeyEvent.VK_CONTROL);
////			r.keyPress(KeyEvent.VK_V);
////			r.keyRelease(KeyEvent.VK_V);
////			r.keyRelease(KeyEvent.VK_CONTROL);
////			// Save the file
////			for (int i = 0; i < 5; i++) {
////				Thread.sleep(2000);
////				r.keyPress(KeyEvent.VK_ENTER);
////			}
//
//		} catch (AWTException e) {
//			ExtentTestManager.getTest().log(Status.FAIL, " Error occured ==>" +e.getMessage());
//
//		} catch (InterruptedException e) {
//			ExtentTestManager.getTest().log(Status.FAIL, " Error occured ==>" +e.getMessage());
//
//		}
//	}
//Browse
public static void uploadFile(String Filepath) throws Exception{

	Robot r ;

	StringSelection stringSelection = new StringSelection(Filepath);
	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	clipboard.setContents(stringSelection, stringSelection);

	try {
		Thread.sleep(4000);
		r = new Robot();
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);

		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(5000);

	} catch (AWTException e) {
		ExtentTestManager.getTest().log(Status.FAIL, " Error occured ==>" +e.getMessage());

	} catch (InterruptedException e) {
		ExtentTestManager.getTest().log(Status.FAIL, " Error occured ==>" +e.getMessage());

	}
}
	public static void deleteFilesIfExist(String path) {
		File filepath = new File(path);
		File[] files = filepath.listFiles();
		for(File file : files){
			ExtentTestManager.getTest().log(Status.INFO, " Deleted File Name ==>" +file.getName());
			file.delete();
		}

	}

	@DataProvider(name="SearchProvider")
	public static Object[][] getDataFromDataprovider(){
		return new Object[][] {

				{ "AD Based", PropertiesOperations.getPropertyValueByKey("ADBasedAppName") },
				{"File Based", PropertiesOperations.getPropertyValueByKey("FileBasedAppName") },
				{ "Hybrid", PropertiesOperations.getPropertyValueByKey("HybridBasedAppName") }


		};
	}

//To take snapshots at the steps level
	public  static void takeSnapshot(String testClassName ,String testMethodName,WebDriver driver ){
		String targetLocation = null;
		String timeStamp = Utility.setTimestamp().toString();
		String screenShotName = testMethodName + timeStamp + ".png";
		//String screenShotName = testMethodName+".png";

		String fileSeperator = System.getProperty("file.separator");
		String reportsPath = System.getProperty("user.dir") + fileSeperator + "TestReport" + fileSeperator
				+ "StepLevelScreenshots";

		ExtentTestManager.getTest().log(Status.INFO, "Screen shots reports path - " + reportsPath);
		try {
			File file = new File(reportsPath + fileSeperator + testClassName); // Set
			// screenshots
			// folder
			if (!file.exists()) {
				if (file.mkdirs()) {

					ExtentTestManager.getTest().log(Status.INFO, "Directory: " + file.getAbsolutePath() + " is created!");
				} else {

					ExtentTestManager.getTest().log(Status.INFO, "Failed to create directory: " + file.getAbsolutePath());
				}

			}
		targetLocation = reportsPath + fileSeperator + testClassName + fileSeperator + screenShotName;// define

// New code
			File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

			// location
			File targetFile = new File(targetLocation);


			ExtentTestManager.getTest().log(Status.INFO, "Screenshot Default file location - " + screenshotFile.getAbsolutePath());

			ExtentTestManager.getTest().log(Status.INFO, "File saved at location - " + targetFile.getAbsolutePath());

			FileHandler.copy(screenshotFile, targetFile);

		ExtentTestManager.getTest().pass("Screenshot is:",
				MediaEntityBuilder.createScreenCaptureFromPath(targetLocation).build());
		} catch (FileNotFoundException e) {

			ExtentTestManager.getTest().log(Status.FAIL, "File not found exception occurred while taking screenshot " + e.getMessage());

		} catch (Exception e) {

			ExtentTestManager.getTest().log(Status.FAIL, "An exception occurred while taking screenshot " + e.getCause());
		}

	}
}
