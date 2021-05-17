package reusableComponents;

import java.io.File;

public class Constants {
    //@Author :Sweety
    public static final String MultiInsertDataLocation = System.getProperty("user.dir")+"\\src\\main\\resources\\Test Data\\Bulk_Insert_Test_Data";

   // public static final String DeleteLocation = System.getProperty("user.dir")+"\\Delete";
   // public static final String DownloadLocation = System.getProperty("user.dir")+"\\Downloads";

    //New Code

    public static final String DeleteLocation = getDeletePath();
    public static final String DownloadLocation = getDownloadPath();
    public static final String TestDataLocation = System.getProperty("user.dir")+"\\src\\main\\resources\\Test Data\\Duplicate_Bulk_Insert_Test_Data";
    public static final String DataValidationTestData = System.getProperty("user.dir")+"\\src\\main\\resources\\Test Data\\DataValidation_Bulk_Insert_Test_Data";

    public static final String BulkModifyTestData = System.getProperty("user.dir")+"\\src\\main\\resources\\Test Data\\Bulk_Modify_Test_Data";
    public static final String BulkDeleteTestData = System.getProperty("user.dir")+"\\src\\main\\resources\\Test Data\\Bulk_Delete_Test_Data";

    public static final String PCCTestData = System.getProperty("user.dir")+"\\src\\main\\resources\\Test Data\\PCCTestData.xls";

    public static final String Attr_Mapping_FileName = "Bulk_AttriBute_Generated.xls";
    public static final String Entitlement_FileName = "Bulk_Entitlement_Generated.xls";
    public static final String ITRolemaster_FileName = "BulkUpload_RoleMaster.xls";
    public static final String Exclusion_FileName = "Bulk_Exclusion_Generated.xls";
    public static final String Population_FileName = "Bulk_Population_Generated.xls";
    public static final String CertDef_FileName = "Bulk_CertDef_Generated.xls";
    public static final String ADGroup_FileName = "Bulk_AdGroup_Generated.xls";
    public static final String DataValidationFileName = "Reports.xls";
    public static final String PCCExportFileName = "PCCScopeList";
    public static final String PCC_Change_Request_Summary_FileName = "PCC_ChangeRequest_Summary";


    //Create the Download path
    private static String getDownloadPath () {
        String path = System.getProperty("user.dir")+"\\Downloads";
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                System.out.println("Directory: " + path + " is created!" );
                return path;
            } else {
                System.out.println("Failed to create directory: " + path);
                return System.getProperty("user.dir");
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }
        return path;
    }


    //Create the Delete path
    private static String getDeletePath () {
        String path =System.getProperty("user.dir")+"\\Delete";
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                System.out.println("Directory: " + path + " is created!" );
                return path;
            } else {
                System.out.println("Failed to create directory: " + path);
                return System.getProperty("user.dir");
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }
        return path;
    }

    //@aashi

    public static final String ViewProdFileName = "BookList";

    public static final String ViewWIPFileName = "BookList";
}
