/*
Author-Sweety,Date :19 Jan 2021
Scenerio 9 - Add new User
Click Admin -> User Management -> Add User
Type <the User Name>
Type <the Lan ID>
Type <the Mail ID>
Select  <the user type> from the drop down list
Click Active checkbox
Click Save Button
***************************************************
Scenerio 10 - Search User
Click Admin -> User Management -> Add User
Type the <User Name> to be searched for
Click Search Button

Scenerio 11 - Assign Role Group
Click Admin -> User Management -> Assign Role Group
Select <the User Name> from the drop down list
Select <the Module Name> from the drop down list
Select  <the Role Name> from the drop down list
Click Active checkbox
Click Save Button

Scenerio 12 - Search User
Click Admin -> User Management -> Assign Role Group
Type the <User Name> to be searched for
Click Search Button



 */
package Admin_Module;
//Prerequisite: Create Menu URL using DB and update the Menu Name in the config file then run This test cases
//Dependent on TC_001_Module_ManagementTest and TC_002_Role_Group_ManagementTest
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

public class TC_003_User_ManagementTest extends TestBase {
    private HomePage homepage;
    private SchemaOnBoardingPage onboard;
    private AddUserPage addUserPage;
    private ActionEngine actionEngine;
    private AssignRoleGroupPage assignRoleGroupPage;
    public static String userName;


    @BeforeClass
    public void initialize(ITestContext context) throws Exception
    {
        setup();
        context.setAttribute("WebDriver", driver);
        homepage =new HomePage(driver);
        onboard = new SchemaOnBoardingPage(driver);
        addUserPage = new AddUserPage(driver);
        actionEngine = new ActionEngine(driver);
        assignRoleGroupPage =  new AssignRoleGroupPage(driver);
        ExtentTestManager.startClass(this.getClass().getName(),context.getName());
        ExtentTestManager.getTest().log(Status.INFO, " Login to application ! ");
        homepage.getLogin();
        homepage.navigateToUserManagementPage();

    }
    @Test(priority =9,description="Verify if User is able to add new User in User Management")
    public void add_new_user() {
        Random rand = new Random();
        int  randomNumber = rand.nextInt(9000000) + 1000000;
        userName = "ANZ" + randomNumber;
        actionEngine.assertEqualsString_custom("Home Page",addUserPage.getAddUserPageTitle(),"User Management Page Title");

        addUserPage.clickOnAddUserTab();
        ExtentTestManager.getTest().log(Status.INFO, "Enter New User Details! ");
        addUserPage.addNewUser(userName, PropertiesOperations.getPropertyValueByKey("LanID"),PropertiesOperations.getPropertyValueByKey("MailID"),PropertiesOperations.getPropertyValueByKey("UserType"));
        if(actionEngine.isAlertPresent() == true){
            actionEngine.assertContainsString_custom("User "+userName+" created successfully.",actionEngine.getAlertText_custom(),"Alert Text for Add User");
       //Verify if new user data is added in the Modify table
            int rownum =addUserPage.getRowNumByCellValue(userName);
            if(rownum>0){
                actionEngine.assertContainsString_custom(userName,addUserPage.getcellValueByColumnName(rownum,"User Name"),"User Name");
                actionEngine.assertContainsString_custom(PropertiesOperations.getPropertyValueByKey("LanID"),addUserPage.getcellValueByColumnName(rownum,"LAN ID"),"LAN ID");
                actionEngine.assertContainsString_custom(PropertiesOperations.getPropertyValueByKey("MailID"),addUserPage.getcellValueByColumnName(rownum,"Mail ID"),"Mail ID");
                actionEngine.assertContainsString_custom(PropertiesOperations.getPropertyValueByKey("UserType"),addUserPage.getcellValueByColumnName(rownum,"User Type"),"User Type");
                actionEngine.assertContainsString_custom("USR",addUserPage.getcellValueByColumnName(rownum,"User ID"),"User ID");

            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "New User Details are not added in the row num==>"+rownum);
            };

        }
       else{
            ExtentTestManager.getTest().log(Status.FAIL,"Alert is not found.New User is not added");

        }




    }

    @Test(priority =10,description="Verify if User is able to search User in User Management")
    public void search_user() {
        try {
            addUserPage.searchUser(userName);
            Thread.sleep(5000);
            int rownum = addUserPage.getRowNumByCellValue(userName);
            if (rownum == 2) {
                ExtentTestManager.getTest().log(Status.PASS, userName + " is identified in rownum " + rownum);

            } else {
                ExtentTestManager.getTest().log(Status.FAIL, userName + " is not identified in rownum " + rownum);

            }
        }
        catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL,"Error occurred ==>"+e.getMessage());
        }
    }

    @Test(priority =11,description="Verify if User is able to assign role group in User Management")
    public void assign_Role_Group() {
        assignRoleGroupPage.clickOnAssignRoleGroupTab();
        String user_name = userName+", "+PropertiesOperations.getPropertyValueByKey("LanID");
        assignRoleGroupPage.assign_role_group(user_name, TC_001_Module_ManagementTest.moduleName, TC_002_Role_Group_ManagementTest.roleName);
//USR0070 User details created successfully....
        if(actionEngine.isAlertPresent() == true){
            //Role Role12 created successfully.
            actionEngine.assertContainsString_custom("User details created successfully",actionEngine.getAlertText_custom(),"Alert Text for Add Role Group");

            //Verify if new user data is added in the Modify table
            int rownum =assignRoleGroupPage.getRowNumByCellValue(userName);
            if(rownum>0){
                actionEngine.assertEqualsString_custom(user_name,assignRoleGroupPage.getcellValueByColumnName(rownum,"User Name"),"User Name");
                actionEngine.assertEqualsString_custom(TC_001_Module_ManagementTest.moduleName,assignRoleGroupPage.getcellValueByColumnName(rownum,"Module Name"),"Module Name");

                actionEngine.assertEqualsString_custom(TC_002_Role_Group_ManagementTest.roleName,assignRoleGroupPage.getcellValueByColumnName(rownum,"RoleGroup Name"),"RoleGroup Name");
                actionEngine.assertEqualsString_custom("True",assignRoleGroupPage.getcellValueByColumnName(rownum,"Active Status"),"Active Status");

            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Assign Role Group Details are not added in the row num==>"+rownum);
            };

        }
        else{
            if(assignRoleGroupPage.isErrorMessageDisplayed())

                ExtentTestManager.getTest().log(Status.FAIL,"Error ==>"+assignRoleGroupPage.getErrorMessage());
        }

    }
    @Test(priority =12,description="Verify if User is able to search user/module name")
    public void search_user_moduleName() {
        try {
            assignRoleGroupPage.serachUserName(userName);
            Thread.sleep(5000);
            int rownum = assignRoleGroupPage.getRowNumByCellValue(userName);
            if (rownum == 2) {
                ExtentTestManager.getTest().log(Status.PASS, assignRoleGroupPage + " is identified in rownum " + rownum);

            } else {
                ExtentTestManager.getTest().log(Status.FAIL, assignRoleGroupPage + " is not identified in rownum " + rownum);

            }

        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "Error occurred ==>" + e.getMessage());
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
