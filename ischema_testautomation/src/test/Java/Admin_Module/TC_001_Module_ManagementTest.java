/*
@Author:Sweety
Scenerio 1 - Add new Module
Click Admin -> Module Management -> Add Module
Type <the Module Name>
Click Active checkbox
Click Save Button
Scenerio 2 - Search Module
Click Admin -> Module Management -> Add Module
Type the <Module Name> to be searched for in Module Name column
Click Search Button
Scenerio 3 - Assign Menus to Module
Click Admin -> Module Management -> Assign Menus to Module
Select <the Module Name> from the drop down list
Select <the Menu Name> from the drop down list
Click Active checkbox
Click Save Button
Scenerio 4 - Search Module/Menu
Click Admin -> Module Management -> Assign Menus to Module
Type the <Module Name> to be searched for or <menu name> to be searched for in Module Name/Menu Name column
Click Search Button

 */

package Admin_Module;

import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.*;
import reusableComponents.ActionEngine;
import reusableComponents.PropertiesOperations;
import testBase.TestBase;
import testReportManager.ExtentTestManager;



import java.util.Random;
//Prerequisite: Create Menu URL using DB and update the Menu Name in the config file then run This test cases
public class TC_001_Module_ManagementTest extends TestBase {


    private HomePage homepage;
    private SchemaOnBoardingPage onboard;
    private AddModulePage addModulePage;
    private AssignMenusToModulePage assignMenusToModulePage;
    private ActionEngine actionEngine;
    public static String moduleName;


    @BeforeClass
    public void initialize(ITestContext context) throws Exception
    {
        setup();
        context.setAttribute("WebDriver", driver);
        homepage =new HomePage(driver);
        onboard = new SchemaOnBoardingPage(driver);
        addModulePage = new AddModulePage(driver);
        assignMenusToModulePage = new AssignMenusToModulePage(driver);
        actionEngine = new ActionEngine(driver);
        ExtentTestManager.startClass(this.getClass().getName(),context.getName());
        ExtentTestManager.getTest().log(Status.INFO, " Login to application ! ");
        homepage.getLogin();
        homepage.moduleManagementPage();

    }
    @Test(priority =1,description="Verify if User is able to add new module in Module Management")
    public void add_new_module() {
        Random rand = new Random();
        int  randomNumber = rand.nextInt(9000000) + 1000000;
        moduleName = "Module" + randomNumber;
        actionEngine.assertEqualsString_custom("Home Page",addModulePage.getPageTitle(),"Module Management Page Title");
       if(addModulePage.isModuleIDTextBoxDisabled() == false){
           ExtentTestManager.getTest().log(Status.PASS, "Module ID Text Box is disabled ");
       }
       else{
           ExtentTestManager.getTest().log(Status.FAIL, "Module ID Text Box is not disabled ");
       }
        ExtentTestManager.getTest().log(Status.INFO, "Enter New Module Details! ");
        addModulePage.addModule(moduleName);
        if(actionEngine.isAlertPresent() == true){
            actionEngine.assertContainsString_custom("New module "+moduleName+" created successfully.",actionEngine.getAlertText_custom(),"Alert Text for Add Module");
            //Verify if new user data is added in the Modify table
            int rownum =addModulePage.getRowNumByCellValue(moduleName);
            if(rownum>0){
                actionEngine.assertEqualsString_custom(moduleName,addModulePage.getcellValueByColumnName(rownum,"Module Name"),"Module Name");
                actionEngine.assertEqualsString_custom("True",addModulePage.getcellValueByColumnName(rownum,"Active Status"),"Active Status");

            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "New Module Details are not added in the row num==>"+rownum);
            };

        }
        else{
            String actaulErrorMessage = addModulePage.getErrorMessage();
            actionEngine.assertContainsString_custom(" Module Name: Already exists, please use another. ",actaulErrorMessage,"Error Message");
            ExtentTestManager.getTest().log(Status.FAIL,"Module Name: Already exists, please use another.");
        }




    }

    @Test(priority =2,description="Verify if User is able to search Module in Module Management")
    public void search_module() {
        try {
            addModulePage.searchModule(moduleName);
            int rownum = addModulePage.getRowNumByCellValue(moduleName);
            if (rownum == 2) {
                ExtentTestManager.getTest().log(Status.PASS, moduleName + " is identified in rownum " + rownum);

            } else {
                ExtentTestManager.getTest().log(Status.FAIL, moduleName + " is not identified in rownum " + rownum);

            }
        }
        catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL,"Error occurred ==>"+e.getMessage());
        }
    }

    @Test(priority =3,description="Verify if User is able to assign menus to module")
    public void assign_Menus_To_Module() {
        assignMenusToModulePage.clickOnAssignToMenuTab();

        assignMenusToModulePage.assignMenusToModule(moduleName,PropertiesOperations.getPropertyValueByKey("MenuName"));
        if(actionEngine.isAlertPresent() == true) {

            actionEngine.getAlertText_custom();
            int rownum = assignMenusToModulePage.getRowNumByCellValue(moduleName);
            if(rownum>0){
                actionEngine.assertEqualsString_custom(PropertiesOperations.getPropertyValueByKey("MenuName"),assignMenusToModulePage.getcellValueByColumnName(rownum,"Menu Name"),"Menu Name");
                actionEngine.assertEqualsString_custom(moduleName,assignMenusToModulePage.getcellValueByColumnName(rownum,"Module Name"),"Module Name");
                actionEngine.assertEqualsString_custom("True",assignMenusToModulePage.getcellValueByColumnName(rownum,"Active Status"),"Active Status");

            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL,"Menu and Module name not added in the table in the row num==> "+rownum);
            }

        }
        else{
            if(assignMenusToModulePage.getErrorMessage().contains("Menu Url :Already exists, please use another.")){
                ExtentTestManager.getTest().log(Status.FAIL,"Menu Url :Already exists, please use another.");
            }

        }
        }


    @Test(priority =4,description="Verify if User is able to search menu/module")
    public void search_menu_module() {
        try {
            assignMenusToModulePage.searchModuleName(moduleName);
            int rownum = assignMenusToModulePage.getRowNumByCellValue(moduleName);
            if (rownum == 2) {
                ExtentTestManager.getTest().log(Status.PASS, moduleName + " is identified in rownum " + rownum);

            } else {
                ExtentTestManager.getTest().log(Status.FAIL, moduleName + " is not identified in rownum " + rownum);

            }

        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL,"Error occurred ==>"+e.getMessage());
        }

    }

    @AfterClass
    public void quitDriver()
    {
        if(driver != null) {
            tearDown();
        }
    }
}
