/*
Scenerio 5 - Add Role Group
Click Admin -> Role Group Management -> Add Role Group
Select <the Module Name> from the drop down list
Tyoe <the Role Name>
Click Active checkbox
Click Save Button

Scenerio 6 - Search Role Group
Click Admin -> Role Group Management -> Add Role Group
Type the <Module Name> to be searched for or <menu name> to be searched for in Module Name/Menu Name column
Click Search Button
Scenerio 7 - Assign Menus to Roles
Click Admin -> Role Group Management -> Assign Menus to Roles
Select  <the Role Name> from the drop down list
Select  <the Module Name> from the drop down list
Select  <the Menu Name> from the drop down list
Select  <the Access Name> from the drop down list
Click Active checkbox
Click Save Button
Scenerio 8 - Search Module/Role Name
Click Admin -> Role Group Management -> Assign Menus to Roles
Type the <Module Name> to be searched for or <role name> to be searched for in Module Name/Role Name column
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
//Dependent on TC_001_Module_ManagementTest
public class TC_002_Role_Group_ManagementTest extends TestBase {


    private HomePage homepage;
    private SchemaOnBoardingPage onboard;
    private AddRoleGroupPage addRoleGroupPage;
    private MenuToRolePage menuToRolePage;
    private ActionEngine actionEngine;
    public static String roleName;


    @BeforeClass
    public void initialize(ITestContext context) throws Exception
    {
        setup();
        context.setAttribute("WebDriver", driver);
        homepage =new HomePage(driver);
        onboard = new SchemaOnBoardingPage(driver);
        addRoleGroupPage = new AddRoleGroupPage(driver);
        menuToRolePage = new MenuToRolePage(driver);
        actionEngine = new ActionEngine(driver);
        ExtentTestManager.startClass(this.getClass().getName(),context.getName());
        ExtentTestManager.getTest().log(Status.INFO, " Login to application ! ");
        homepage.getLogin();
        homepage.roleGroupManagementPage();

    }
    @Test(priority =5,description="Verify if User is able to add new Role Group in Role Group Management")
    public void add_new_RoleGroup() {
        Random rand = new Random();
        int  randomNumber = rand.nextInt(9000000) + 1000000;
        roleName = "Role" + randomNumber;

         if(addRoleGroupPage.istxtRolIDDisabled() == false){
            ExtentTestManager.getTest().log(Status.PASS, "Role ID Text Box is disabled ");
        }
        else{
            ExtentTestManager.getTest().log(Status.FAIL, "Role ID Text Box is not disabled ");
        }
        ExtentTestManager.getTest().log(Status.INFO, "Add new role group !!");
        addRoleGroupPage.addRoleGroup(TC_001_Module_ManagementTest.moduleName,roleName);
        if(actionEngine.isAlertPresent() == true){
            //Role Role12 created successfully.
            actionEngine.assertContainsString_custom("Role "+roleName+" created successfully.",actionEngine.getAlertText_custom(),"Alert Text for Add Role Group");

            //Verify if new user data is added in the Modify table
            int rownum =addRoleGroupPage.getRowNumByCellValue(roleName);
            if(rownum>0){
                actionEngine.assertEqualsString_custom(roleName,addRoleGroupPage.getcellValueByColumnName(rownum,"Role Name"),"Role Name");

                actionEngine.assertEqualsString_custom(TC_001_Module_ManagementTest.moduleName,addRoleGroupPage.getcellValueByColumnName(rownum,"Module Name"),"Module Name");
                actionEngine.assertEqualsString_custom("True",addRoleGroupPage.getcellValueByColumnName(rownum,"Active Status"),"Active Status");

            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "New Role Group Details are not added in the row num==>"+rownum);
            };

        }
        else{
            if(addRoleGroupPage.isErrorMessageDisplayed())

            ExtentTestManager.getTest().log(Status.FAIL,"Error ==>"+addRoleGroupPage.getErrorMessage());
        }




    }

    @Test(priority =6,description="Verify if User is able to search Role Group in Role Group Management")
    public void search_roleGroup() {
        try {
            addRoleGroupPage.serachRoleGroup(roleName);
            int rownum = addRoleGroupPage.getRowNumByCellValue(roleName);
            if (rownum == 2) {
                ExtentTestManager.getTest().log(Status.PASS, roleName + " is identified in rownum " + rownum);

            } else {
                ExtentTestManager.getTest().log(Status.FAIL, roleName + " is not identified in rownum " + rownum);

            }
        }
        catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL,"Error occurred ==>"+e.getMessage());
        }
    }

    @Test(priority =7,description="Verify if User is able to assign menus to role")
    public void assign_Menu_To_role() {
        menuToRolePage.clickOnMenuToRoleTab();
      menuToRolePage.add_menu_to_role(roleName, TC_001_Module_ManagementTest.moduleName, PropertiesOperations.getPropertyValueByKey("MenuName"),"Read");
        if(actionEngine.isAlertPresent() == true) {

            actionEngine.getAlertText_custom();
            int rownum = menuToRolePage.getRowNumByCellValue(roleName);
            if(rownum>0){
                actionEngine.assertEqualsString_custom(roleName,menuToRolePage.getcellValueByColumnName(rownum,"Role Name"),"Role Name");
                actionEngine.assertEqualsString_custom(TC_001_Module_ManagementTest.moduleName,menuToRolePage.getcellValueByColumnName(rownum,"Module Name"),"Module Name");
                actionEngine.assertEqualsString_custom(PropertiesOperations.getPropertyValueByKey("MenuName"),menuToRolePage.getcellValueByColumnName(rownum,"Module Name"),"Module Name");
                actionEngine.assertEqualsString_custom("Read",menuToRolePage.getcellValueByColumnName(rownum,"Menu Name"),"Menu Name");
                actionEngine.assertEqualsString_custom("True",menuToRolePage.getcellValueByColumnName(rownum,"Active Status"),"Active Status");

            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL,"Menu and Module name not added in the table in the row num==> "+rownum);
            }

        }
        else{
            if(menuToRolePage.isErrorMessageDisplayed()){
                ExtentTestManager.getTest().log(Status.FAIL,"Error ==>"+menuToRolePage.getErrorMessage());
            }

        }
    }


    @Test(priority =8,description="Verify if User is able to search role/module")
    public void search_role_module() {
        try {
            menuToRolePage.serachRoleName(roleName);
            int rownum = menuToRolePage.getRowNumByCellValue(roleName);
            if (rownum == 2) {
                ExtentTestManager.getTest().log(Status.PASS, menuToRolePage + " is identified in rownum " + rownum);

            } else {
                ExtentTestManager.getTest().log(Status.FAIL, menuToRolePage + " is not identified in rownum " + rownum);

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
