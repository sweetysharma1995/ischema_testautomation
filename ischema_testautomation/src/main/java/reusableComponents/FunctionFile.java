package reusableComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.aventstack.extentreports.Status;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import testReportManager.ExtentTestManager;


public class FunctionFile {

	public static String GetAllTestCaseData(String ColumnName,String ExpectedTestcase) throws IOException {
		System.out.println("User directory is"+System.getProperty("user.dir"));
		System.out.println("File location is  "+System.getProperty("user.dir") + "\\src\\main\\resources\\Test Data\\TestData.xls");
		System.out.println("ColumnName is: "+ColumnName);
		System.out.println("ExpectedTestcase is: "+ExpectedTestcase);
		File TestDataFile = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\Test Data\\TestData.xls");
		//		//C:\Users\sweetys\eclipse-workspace\ISchemaProject\src\main\resources\Test Data\TestData.xls
		// Load the file.

		FileInputStream finput1 = new FileInputStream(TestDataFile);
		HSSFWorkbook wb1 = new HSSFWorkbook(finput1);
		HSSFSheet sheet2 = wb1.getSheet("TestCaseSheet");
		String KeyValue = null;
		HSSFRow row = sheet2.getRow(0);
	     
		for (int j = 1; j <= sheet2.getLastRowNum(); j++) {			
			//String TestcaseName = sheet2.getRow(j).getCell(0).getStringCellValue();
			String TestcaseName =sheet2.getRow(j).getCell(0).toString();
			System.out.println("TestcaseName is: "+TestcaseName);
			if (TestcaseName.equalsIgnoreCase(ExpectedTestcase)) {	
			     for(int i=1; i<=row.getLastCellNum(); i++)
			     {

			    	 String FieldName = sheet2.getRow(0).getCell(i).getStringCellValue();
					 System.out.println("Fieldname is: "+FieldName);
			         if (FieldName.equalsIgnoreCase(ColumnName)) {
			        	// KeyValue=sheet2.getRow(j).getCell(i).getStringCellValue();
						 KeyValue = sheet2.getRow(j).getCell(i).toString();
						 System.out.println("KeyValue is"+KeyValue);
			        	 break;
			         }  
			       }
								


				break;
			}

		}
		System.out.println("returned KeyValue is"+KeyValue);
		return KeyValue;	
	}


//Write cell data in the excel file
	public static void WriteCellData (String FileName,String sheetName, String colName,  String value)
	{
		try
		{
			File file =    new File(Constants.DownloadLocation+"\\"+FileName);

			FileInputStream inputStream = new FileInputStream(file);

			HSSFWorkbook workbook = null;
			HSSFCell cell = null;



			int col_Num = -1;
			workbook = new HSSFWorkbook(inputStream);
			HSSFSheet sheet = workbook.getSheet(sheetName);


			HSSFRow row = sheet.getRow(0);

			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
				{

					col_Num = i;

					break;
				}
			}


			sheet.autoSizeColumn(col_Num);
			row = sheet.getRow(1);
			if(row==null)
				row = sheet.createRow(1);
			cell = row.getCell(col_Num);
			if(cell == null)
				cell = row.createCell(col_Num);

			cell.setCellValue(value);

			inputStream.close();

			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			fos.close();
		}
		catch (Exception ex)
		{

			ExtentTestManager.getTest().log(Status.FAIL, "Error occurred while writing test data in the excel==> "+ex.getMessage());


		}


	}
	//Get Total row count from Excel
	public static int getRowCount_excel(String FilePath,String sheetName ) throws Exception {
		File file =    new File(FilePath);
		FileInputStream inputStream = new FileInputStream(file);
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		HSSFSheet sheet = workbook.getSheet(sheetName);
		int row = sheet.getLastRowNum();


		return row+1;
	}


	public static String readTestDataUsingColumnName(String ColumnName,String ExpectedTestcase) throws IOException {
		File TestDataFile = new File(Constants.PCCTestData);
		//C:\Users\sweetys\eclipse-workspace\ISchemaProject\src\main\resources\Test Data\TestData.xls
		// Load the file.

		FileInputStream finput1 = new FileInputStream(TestDataFile);
		HSSFWorkbook wb1 = new HSSFWorkbook(finput1);
		HSSFSheet sheet2 = wb1.getSheet("TestCaseSheet");
		String KeyValue = null;
		HSSFRow row = sheet2.getRow(0);
		DataFormatter formatter = new DataFormatter();

		for (int j = 1; j <= sheet2.getLastRowNum(); j++) {
			String TestcaseName = sheet2.getRow(j).getCell(0).getStringCellValue();
			if (TestcaseName.equalsIgnoreCase(ExpectedTestcase)) {
				for(int i=1; i<=row.getLastCellNum(); i++)
				{
					String FieldName = sheet2.getRow(0).getCell(i).getStringCellValue();
					if (FieldName.equalsIgnoreCase(ColumnName)) {
						Cell cell=sheet2.getRow(j).getCell(i);
						KeyValue = formatter.formatCellValue(cell);
						break;
					}
				}


				break;
			}

		}
		return KeyValue;
	}


}
