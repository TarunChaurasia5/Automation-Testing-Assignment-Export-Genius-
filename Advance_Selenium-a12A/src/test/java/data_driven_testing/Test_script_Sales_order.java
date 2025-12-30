package data_driven_testing;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * ----------------------------------------------------------------------------
 * CLASS NAME : Sales_order
 * AUTHOR     : Tarun
 *
 * DESCRIPTION:
 * This Selenium Automation script is designed to automate the creation of a
 * Sales Order record inside Vtiger CRM. All configuration details such as
 * browser type, username, password, and CRM URL are fetched dynamically from
 * an external properties file to ensure reusability and environment flexibility.
 *
 * EXECUTION STEPS:
 *
 *  1. Load configuration from properties file
 *  2. Launch the specified browser (Chrome / Edge / Firefox)
 *  3. Navigate to CRM login page
 *  4. Authenticate using admin credentials
 *  5. Navigate to Sales Order module using "More" dropdown
 *  6. Click on “Create Sales Order”
 *  7. Populate the form fields with below test data:
 *
 *        ➤ Subject             : Order Echo
 *        ➤ Customer No         : CUST005
 *        ➤ Quote Name          : Quote E (Commented - Field optional)
 *        ➤ Contact Name        : Emma (Commented - Field optional)
 *        ➤ Carrier             : DHL
 *        ➤ Status              : Created
 *        ➤ Excise Duty         : 7%
 *        ➤ Assigned To         : User (Radio selected)
 *        ➤ Opportunity Name    : Opp E
 *        ➤ Sales Order No      : SO-005
 *        ➤ Purchase Order      : PO-005
 *        ➤ Due Date            : 2025-12-09
 *        ➤ Pending             : No
 *        ➤ Sales Commission    : 5%
 *        ➤ Organization Name   : Org E
 *
 *  8. Save the Sales Order
 *  9. Close browser
 *
 * PRE-REQUISITES:
 *  ✔ Drivers are configured in system PATH
 *  ✔ Vtiger CRM is up and accessible via URL from properties
 *  ✔ correct locators are mapped for UI fields
 *
 * NOTES:
 *  - No changes made to application logic, locators, or execution flow
 *  - Comments retained where fields are optional or future-use
 *
 * SCRIPT STATUS:
 *  ✔ Successfully automates Sales Order creation
 *  ✔ Configurable with multi-browser support
 * ----------------------------------------------------------------------------
 */
public class Test_script_Sales_order {

    public static void main(String[] args) throws IOException {
    	
    	FileInputStream fis = new FileInputStream("./src/test/resources/commondata.properties");
		Properties pObj = new Properties();
		pObj.load(fis);

		String USERNAME = pObj.getProperty("un");
		String PASSWORD = pObj.getProperty("pwd");
		String BROWSER = pObj.getProperty("browser");
		String URL = pObj.getProperty("url");
		
		FileInputStream fis1 = new FileInputStream("./src/test/resources/TEST_DATA.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		Sheet sh = wb.getSheet("Sales_Order");
		
		String SU = sh.getRow(0).getCell(0).getStringCellValue();
		String CU = sh.getRow(0).getCell(0).getStringCellValue();
//		String PU = sh.getRow(0).getCell(0).getStringCellValue();
//		String CO = sh.getRow(0).getCell(0).getStringCellValue();
//		String ON = sh.getRow(0).getCell(0).getStringCellValue();
		String CA = sh.getRow(0).getCell(0).getStringCellValue();
		String ST = sh.getRow(0).getCell(0).getStringCellValue();
		//double SC = sh.getRow(0).getCell(0).getNumericCellValue();
//		String PR = sh.getRow(0).getCell(0).getStringCellValue();

    	String browser = BROWSER;
		WebDriver driver;

		if (browser.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (browser.equals("edge")) {
			driver = new EdgeDriver();
		} else if (browser.equals("firefox")) {
			driver = new FirefoxDriver();
		} else {
			driver = new ChromeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));


        driver.get(URL);

        // LOGIN
        driver.findElement(By.name("user_name")).sendKeys(USERNAME);
        driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
        driver.findElement(By.id("submitButton")).click();

        // NAVIGATE TO SALES ORDER (via More dropdown)
        Actions action = new Actions(driver);
        WebElement more = driver.findElement(By.linkText("More"));
        action.moveToElement(more).build().perform();

        driver.findElement(By.name("Sales Order")).click();
        driver.findElement(By.cssSelector("[title='Create Sales Order...']")).click();

        // FILL SALES ORDER FORM
        driver.findElement(By.name("subject")).sendKeys(SU);
        driver.findElement(By.id("customerno")).sendKeys(CU);
        // driver.findElement(By.name("quotename")).sendKeys("Quote E");
        // driver.findElement(By.id("contact_name")).sendKeys("Emma");
        driver.findElement(By.name("carrier")).sendKeys(CA);
        driver.findElement(By.name("sostatus")).sendKeys(ST);
        driver.findElement(By.id("exciseduty")).sendKeys("7%");
        driver.findElement(By.xpath("//input[@value='U']")).click();
        driver.findElement(By.name("potential_name")).sendKeys("Opp E");
        driver.findElement(By.name("salesorder_no")).sendKeys("SO-005");
        driver.findElement(By.id("vtiger_purchaseorder")).sendKeys("PO-005");
        driver.findElement(By.name("duedate")).sendKeys("2025-12-09");
        driver.findElement(By.id("pending")).sendKeys("No");
        driver.findElement(By.id("salescommission")).sendKeys("5%");
        driver.findElement(By.name("account_name")).sendKeys("Org E");

        // SAVE ORDER
        driver.findElement(By.cssSelector("[title='Save [Alt+S]']")).click();

        // CLOSE BROWSER
        wb.close();
        fis1.close();
        driver.quit();
    }
}
