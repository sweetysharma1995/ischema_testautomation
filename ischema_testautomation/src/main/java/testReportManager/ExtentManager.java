package testReportManager;



import java.io.File;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import reusableComponents.Utility;


public class ExtentManager  {
    private static String timeStamp = Utility.setTimestamp().toString();
   private static ExtentReports extent;
   private static String reportFileName = "Test-Automaton-Report"+timeStamp+".html";
   private static String fileSeperator = System.getProperty("file.separator");
   private static String reportFilepath = System.getProperty("user.dir") +fileSeperator+ "TestReport";
   private static String reportFileLocation =  reportFilepath +fileSeperator+ reportFileName;
   
   public static ExtentReports getInstance() {
       if (extent == null)
           createInstance();
       return extent;
   }

   //Create an extent report instance
   public static ExtentReports createInstance() {
       String fileName = getReportPath(reportFilepath);
       ExtentSparkReporter spark = new ExtentSparkReporter(fileName);
       spark.config().setTheme(Theme.STANDARD);
       spark.config().setDocumentTitle(reportFileName);
       spark.config().setEncoding("utf-8");
       spark.config().setReportName(reportFileName);
       spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
       
      

       extent = new ExtentReports();
       extent.attachReporter(spark);
       //Set environment details
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("AUT", "QA");
		extent.setSystemInfo("Author", "QA Team");
		extent.setSystemInfo("Browser", "Chrome");
		extent.setSystemInfo("Java Version", "JDK 1.8");
		
		

       return extent;
   }
    
   //Create the report path
   private static String getReportPath (String path) {
   	File testDirectory = new File(path);
       if (!testDirectory.exists()) {
       	if (testDirectory.mkdir()) {
              System.out.println("Directory: " + path + " is created!" );
               return reportFileLocation;
           } else {
               System.out.println("Failed to create directory: " + path);
               return System.getProperty("user.dir");
           }
       } else {
           System.out.println("Directory already exists: " + path);
       }
		return reportFileLocation;
   }

	

}