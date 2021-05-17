package pageObjects;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import reusableComponents.ActionEngine;
import testReportManager.ExtentTestManager;

import java.util.List;

public class AddRoleGroupPage {
    private WebDriver driver;
    private ActionEngine actionEngine;
    //Object repository by sweety Date:21 Jan 2021
    private By addRoleGroupTab = By.id("MainContent_btnAddRoleGroup");
    private By dropdownModuleName = By.id("MainContent_ddlModuleName");
    private By txtRoleID = By.id("MainContent_txtRoleID");
    private By txtRoleName = By.id("MainContent_txtRoleName");
    private  By chkboxActive  = By.id("MainContent_chkActive");
    private By btnSave = By.id("MainContent_btnSave");
    private By serachBox = By.id("MainContent_txtSearch");
    private By btnSearch = By.id("MainContent_btnSearch");
    private By tableModify = By.id("MainContent_gvRole");
    private By txtErrorMessage = By.id("error");





    public AddRoleGroupPage(WebDriver driver) {
        this.driver = driver;
        actionEngine = new ActionEngine(this.driver);
    }

public Boolean istxtRolIDDisabled(){
        return driver.findElement(txtRoleID).isEnabled();
}


public void addRoleGroup(String moduleName,String roleName){
    actionEngine.selectDropDownByVisibleText_custom(driver.findElement(dropdownModuleName),"Module Name", moduleName);
    actionEngine.sendKeys_custom(driver.findElement(txtRoleName),"Role Name",roleName);
    actionEngine.click_custom(driver.findElement(chkboxActive),"Active Check Box");
    actionEngine.click_custom(driver.findElement(btnSave),"Save Button");
        }

public void serachRoleGroup(String searchText){
          actionEngine.sendKeys_custom(driver.findElement(serachBox),"Search Box",searchText);
         actionEngine.click_custom(driver.findElement(btnSearch),"Search Button");
    }

    public int getRowNumByCellValue(String value) {
        WebElement table = driver.findElement(tableModify);
        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        int rowNo = 0;
        for (int i = 0; i < rows.size(); i++) {
            WebElement row = tbody.findElement(By.xpath("//table[@id='MainContent_gvRole']/tbody/tr[" + (i + 1) + "]"));
            if (row.getText().trim().contains(value)) {
                rowNo = i + 1;
                ExtentTestManager.getTest().log(Status.INFO, value + "  found in row num: " + rowNo);
                break;
            }

        }
        return rowNo;
    }
    public String getcellValueByColumnName(int rownum ,String key){
        String dxpath ="//table[@id='MainContent_gvRole']/tbody/tr["+rownum+"]/td[count(//table[@id='MainContent_gvRole']/tbody/tr/th[text()='"+key+"']/preceding-sibling::th)+1]";
        return driver.findElement(By.xpath(dxpath)).getText();
    }

public Boolean isErrorMessageDisplayed(){
      return driver.findElement(txtErrorMessage).isDisplayed();
}
    public String getErrorMessage(){
        return driver.findElement(txtErrorMessage).getText();
    }
}
