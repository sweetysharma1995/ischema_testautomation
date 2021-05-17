package testReportManager;

import java.io.File;
import java.io.FileNotFoundException;



import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;


import reusableComponents.Utility;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;

//import testBase.TestBase;
public class TestListener  implements ITestListener  {
	 	
	public void onStart(ITestContext context) {
		System.out.println("*** Test Suite " + context.getName() + " started ***");
		//ExtentTestManager.startClass(context.getCurrentXmlTest().getClasses().stream().findFirst().get().getName(),context.getName());
			
	}

	public void onFinish(ITestContext context) {
		System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}

	public void onTestStart(ITestResult result) {
		System.out.println(("*** Running test method " + result.getMethod().getMethodName() + "..."));
		//String feature = result.getMethod().getRealClass().getName() + ":" + result.getMethod().getMethodName();
        //ExtentTest extentTest = extent.createTest(feature, result.getMethod().getDescription());
		ExtentTestManager.startTest(result.getMethod().getMethodName());
		ExtentTestManager.getTest().log(Status.INFO, "Execution begin for Test Method in Test Class   "+result.getMethod().getRealClass().getName());
		ExtentTestManager.getTest().log(Status.INFO, "Test Execution Started for Test Method  "+result.getMethod().getMethodName());
		ExtentTestManager.getTest().log(Status.INFO, "Test Method Description is  "+result.getMethod().getDescription());
	}

	public void onTestSuccess(ITestResult result) {

		System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
		ExtentTestManager.getTest().log(Status.PASS, "Test passed"+result.getMethod().getMethodName());

		ITestContext context = result.getTestContext();
		WebDriver  driver = (WebDriver)context.getAttribute("WebDriver");

		String targetLocation = null;

		String testClassName = (result.getInstanceName()).trim();
		String timeStamp = Utility.setTimestamp().toString();
		String testMethodName = result.getMethod().getMethodName().toString().trim();
		System.out.println(testMethodName);
		String screenShotName = testMethodName + timeStamp + ".png";
		//String screenShotName = testMethodName+".png";
		System.out.println(screenShotName);
		String fileSeperator = System.getProperty("file.separator");
		String reportsPath = System.getProperty("user.dir") + fileSeperator + "TestReport" + fileSeperator
				+ "PassedScreenshots";

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
			//new code
			// location
			targetLocation = reportsPath + fileSeperator + testClassName + fileSeperator + screenShotName;// define

			Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);

			ImageIO.write(screenshot.getImage(), "PNG", new File(targetLocation));


			ExtentTestManager.getTest().pass("Screenshot is:",
					MediaEntityBuilder.createScreenCaptureFromPath(targetLocation).build());
			//old code

			//File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			//System.out.println("screenshotFile is"+screenshotFile);

			//File targetFile = new File(targetLocation);


			//ExtentTestManager.getTest().log(Status.INFO, "Screen shot file location - " + screenshotFile.getAbsolutePath());

			//ExtentTestManager.getTest().log(Status.INFO, "Target File location - " + targetFile.getAbsolutePath());
			//FileHandler.copy(screenshotFile, targetFile);
			//FileUtils.copyFile(screenshotFile, targetFile);
			//FileHandler.copy(screenshotFile, targetFile);

		} catch (FileNotFoundException e) {

			ExtentTestManager.getTest().log(Status.FAIL, "File not found exception occurred while taking screenshot " + e.getMessage());

		} catch (Exception e) {

			ExtentTestManager.getTest().log(Status.FAIL, "An exception occurred while taking screenshot " + e.getCause());
		}

	//	ExtentTestManager.getTest().pass("Screenshot is:",
	//			MediaEntityBuilder.createScreenCaptureFromPath(targetLocation).build());
	//	ExtentTestManager.getTest().log(Status.PASS, "Test Passed");
		//ExtentTestManager.getTest().addScreenCaptureFromPath(targetLocation,"Screenshot:");
		ExtentTestManager.getTest().log(Status.PASS, "Test Passed");





	}

	public void onTestFailure(ITestResult result) {
		
		ExtentTestManager.getTest().log(Status.FAIL, "*** Test execution " + result.getMethod().getMethodName() + " failed...");
		
		ExtentTestManager.getTest().log(Status.FAIL, "Error Occurred: " +result.getThrowable());
			
          ITestContext context = result.getTestContext();
          WebDriver  driver = (WebDriver)context.getAttribute("WebDriver");

		String targetLocation = null;

		String testClassName = (result.getInstanceName()).trim();
		String timeStamp = Utility.setTimestamp().toString();		
		String testMethodName = result.getMethod().getMethodName().toString().trim();
		System.out.println(testMethodName);
		String screenShotName = testMethodName + timeStamp + ".png";
		System.out.println(screenShotName);
		String fileSeperator = System.getProperty("file.separator");
		String reportsPath = System.getProperty("user.dir") + fileSeperator + "TestReport" + fileSeperator
				+ "FailedScreenshots";
		
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

			File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			System.out.println("screenshotFile is"+screenshotFile);
			targetLocation = reportsPath + fileSeperator + testClassName + fileSeperator + screenShotName;// define
																											// location
			File targetFile = new File(targetLocation);
			
			
			ExtentTestManager.getTest().log(Status.INFO, "Screen shot file location - " + screenshotFile.getAbsolutePath());
			
			ExtentTestManager.getTest().log(Status.INFO, "Target File location - " + targetFile.getAbsolutePath());
			//FileHandler.copy(screenshotFile, targetFile);
			//FileUtils.copyFile(screenshotFile, targetFile);
			FileHandler.copy(screenshotFile, targetFile);

		} catch (FileNotFoundException e) {
			
			ExtentTestManager.getTest().log(Status.FAIL, "File not found exception occurred while taking screenshot " + e.getMessage());
			
		} catch (Exception e) {
			
			ExtentTestManager.getTest().log(Status.FAIL, "An exception occurred while taking screenshot " + e.getCause());
		}

		ExtentTestManager.getTest().fail("Screenshot",
				MediaEntityBuilder.createScreenCaptureFromPath(targetLocation).build());
		ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}

	
	
	

}
