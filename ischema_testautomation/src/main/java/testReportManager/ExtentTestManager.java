package testReportManager;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;



public class ExtentTestManager {
	static ExtentTest parenttest;
	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
	static ExtentReports extent = ExtentManager.getInstance();

	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public static synchronized void endTest() {
		extent.flush();
	}


	public static synchronized ExtentTest startClass(String testClassName,String category ) {
		 parenttest = extent.createTest(testClassName);
		
		 parenttest.assignCategory(category);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), parenttest);
		return parenttest;
	}
	
	public static synchronized ExtentTest startTest(String testName) {
		
		ExtentTest test = parenttest.createNode(testName);
		//test.assignCategory(category)
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
		return test;
	}
	
}

