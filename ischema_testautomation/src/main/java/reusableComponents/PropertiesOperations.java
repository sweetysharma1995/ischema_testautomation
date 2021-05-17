package reusableComponents;



import com.aventstack.extentreports.Status;
import testReportManager.ExtentTestManager;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesOperations {

    static Properties prop = new Properties();

    public static String getPropertyValueByKey(String key){

        FileInputStream fis;

       try {
           fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\Config File\\config.properties");
           prop.load(fis);
       }
       catch(Exception ex){
           ExtentTestManager.getTest().log(Status.FAIL, " Unable to read data from config properties file.Error Occurred==> "+ex.getMessage());

       }
       // read data
        String value = prop.getProperty(key);
return  value;
    }

}
