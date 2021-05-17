package pageObjects;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import reusableComponents.ActionEngine;
import testReportManager.ExtentTestManager;

import java.util.List;

public class AddModulePage  {
    private WebDriver driver;
    private ActionEngine actionEngine;
    //Object repository by sweety Date:21 Jan 2021
    private By addModuleTab = By.id("MainContent_btnAddUser");
    private By txtMduleID = By.id("MainContent_txtModuleID");
    private By txtModuleName = By.id("MainContent_txtModuleName");
    private By chkboxActive  = By.id("MainContent_chkActive");
    private By btnSave = By.id("MainContent_btnSave");
    private By serachBoxModuleName = By.id("MainContent_txtSearch");
    private By btnSearch = By.id("MainContent_btnSearch");
    private By tableModify = By.id("MainContent_gvModule");
    private By txtErrorMessage = By.id("error");

//POM Hybrid framework


    public AddModulePage(WebDriver driver) {
        this.driver = driver;
        actionEngine = new ActionEngine(this.driver);
    }
    public String getPageTitle(){
       return  driver.getTitle();
    }

public boolean isModuleIDTextBoxDisabled(){
        return driver.findElement(txtMduleID).isEnabled();
}
public void addModule(String moduleName){
    actionEngine.sendKeys_custom(driver.findElement(txtModuleName),"Module Name",moduleName);
    actionEngine.click_custom(driver.findElement(chkboxActive),"Active Check Box");
    actionEngine.click_custom(driver.findElement(btnSave),"Search Button");
}
public String getErrorMessage(){
        return driver.findElement(txtErrorMessage).getText();
}

public void searchModule(String moduleName){
    actionEngine.sendKeys_custom(driver.findElement(serachBoxModuleName),"Search Box",moduleName);
    actionEngine.click_custom(driver.findElement(btnSearch),"Search Button");
}
    public int getRowNumByCellValue(String value) {
        WebElement table = driver.findElement(tableModify);
        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        int rowNo = 0;
        for (int i = 0; i < rows.size(); i++) {
            WebElement row = tbody.findElement(By.xpath("//table[@id='MainContent_gvModule']/tbody/tr[" + (i + 1) + "]"));
            if (row.getText().trim().contains(value)) {
                rowNo = i + 1;
                ExtentTestManager.getTest().log(Status.INFO, value + "  found in row num: " + rowNo);
                break;
            }

        }
        return rowNo;
    }
    public String getcellValueByColumnName(int rownum ,String key){
        String dxpath ="//table[@id='MainContent_gvModule']/tbody/tr["+rownum+"]/td[count(//table[@id='MainContent_gvModule']/tbody/tr/th[text()='"+key+"']/preceding-sibling::th)+1]";
        return driver.findElement(By.xpath(dxpath)).getText();
    }
}
