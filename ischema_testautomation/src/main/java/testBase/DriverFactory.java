package testBase;

import org.openqa.selenium.WebDriver;

public class DriverFactory {


    private DriverFactory(){
        //private constructor
    }


    private static DriverFactory instance = new DriverFactory();
    public static DriverFactory getInstance(){
        return instance;
    }

    //Define separate factory methods for creating objects
    ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    public  WebDriver getDriver(){
        return driver.get();
    }

    public  void setDriver(WebDriver driverParam){
        driver.set(driverParam);

    }

    public  void closeDriver(){

        driver.get().close();
        driver.get().quit();
        driver.remove();


    }

}
