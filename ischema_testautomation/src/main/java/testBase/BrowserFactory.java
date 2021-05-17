package testBase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import sun.security.krb5.internal.crypto.Des;

public class BrowserFactory {

    public WebDriver createBrowserInstance(String browser){
        WebDriver driver = null;

        if(browser.equalsIgnoreCase("chrome"))
        {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
           // ChromeOptions options = new ChromeOptions();
           // options.setExperimentalOption()
            driver= new ChromeDriver();
        }

        else if (browser.equalsIgnoreCase("IE"))
        {
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability("nativeEvents",false);
            capabilities.setCapability("unexpectedAlertBehaviour","accept");
            capabilities.setCapability("ignoreProtectedModeSettings",true);
            capabilities.setCapability("disable-popup-blocking",true);
            capabilities.setCapability("enablePersistentHover",true);
            capabilities.setCapability("ignoreZoomSetting",true);
            capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,true);
            //capabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP,true);
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
            capabilities.setCapability("requireWindowFocus",true);
            // System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\Drivers\\IEDriverServer.exe");
            System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\Drivers\\IEDriverServer.exe");

            driver = new InternetExplorerDriver(capabilities);
        }
        else {
            System.out.println("please enter valid browser name ");
        }


     return driver;

    }


}
