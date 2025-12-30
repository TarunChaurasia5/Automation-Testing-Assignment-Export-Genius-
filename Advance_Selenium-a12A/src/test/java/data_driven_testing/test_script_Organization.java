//package data_driven_testing;
//
//public class test_script_contact {
//	
//	
//
//}


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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * ----------------------------------------------------------------------------
 * CLASS NAME : Organization 
 * AUTHOR     : Tarun
 *
 * DESCRIPTION:
 * This Selenium script automates Organization creation inside 
 * Vtiger CRM (URL obtained from properties file).
 *
 * The browser, username, password and CRM URL are dynamically fetched 
 * from 'commondata.properties' file, making this script configurable 
 * for different environments and browsers.
 *
 * WORKFLOW:
 *     1. Read browser & login configuration from properties file
 *     2. Launch the selected browser (Chrome / Edge / Firefox)
 *     3. Navigate to CRM login page and authenticate as admin
 *     4. Navigate to Organizations module
 *     5. Click the "Create Organization" button
 *     6. Enter the following Organization test data:
 *
 *         ➤ Organization Name : Org Test 5
 *         ➤ Website           : www.org5.com
 *         ➤ Ticker Symbol     : TK5
 *         ➤ Member Of         : Parent 5
 *         ➤ Employees         : 55
 *         ➤ Other Email       : other5@example.com
 *         ➤ Industry          : Technology
 *         ➤ Type              : Customer
 *         ➤ Email Opt-out     : Default (No)
 *         ➤ Assigned To       : User (User5)
 *         ➤ Organization No   : AUTO5
 *         ➤ Phone             : 555-000-1205
 *         ➤ Fax               : 555-100-1205
 *         ➤ Other Phone       : 555-200-1205
 *         ➤ Email             : contact5@example.com
 *         ➤ Ownership         : Private
 *         ➤ Rating            : Hot
 *         ➤ SIC Code          : 1235
 *         ➤ Annual Revenue    : (Commented — not used)
 *         ➤ Notify Owner      : Default (No)
 *
 *     7. Save the newly created organization
 *     8. Close browser after short delay
 *
 * PRE-REQUISITES:
 *     ✔ Selenium WebDriver installed
 *     ✔ ChromeDriver / EdgeDriver / GeckoDriver available in PATH
 *     ✔ Vtiger CRM server running at http://localhost:8888
 *     ✔ commondata.properties file available under:
 *          src/test/resources/commondata.properties
 *
 * SCRIPT STATUS:
 *     - Successfully creates a new organization using hard-coded test data
 *     - No functionality modified — only documentation improved
 * ----------------------------------------------------------------------------
 */

public class test_script_Organization {

    public static void main(String[] args) throws InterruptedException, IOException {
    	
    	FileInputStream fis = new FileInputStream("./src/test/resources/commondata.properties");
		Properties pObj = new Properties();
		pObj.load(fis);

		String USERNAME = pObj.getProperty("un");
		String PASSWORD = pObj.getProperty("pwd");
		String BROWSER = pObj.getProperty("browser");
		String URL = pObj.getProperty("url");
		
		FileInputStream fis1 = new FileInputStream("./src/test/resources/TEST_DATA.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		Sheet sh = wb.getSheet("Organization");
		
		String ON = sh.getRow(1).getCell(0).getStringCellValue();
		String WE = sh.getRow(1).getCell(2).getStringCellValue();
		String EM = sh.getRow(1).getCell(3).getStringCellValue();
		String IN = sh.getRow(1).getCell(4).getStringCellValue();
//		String BI = sh.getRow(1).getCell(5).getStringCellValue();
//		String SH = sh.getRow(1).getCell(6).getStringCellValue();

        // STEP 1: Launch Browser
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

        // STEP 2: Open CRM Login Page
        driver.get(URL);

        // STEP 3: Login into CRM
        driver.findElement(By.name("user_name")).sendKeys(USERNAME);
        driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
        driver.findElement(By.id("submitButton")).click();

        // STEP 4: Navigate to Organizations Module
        driver.findElement(By.partialLinkText("Organizations")).click();

        // STEP 5: Click on "Create Organization"
        driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();

        // STEP 6: Fill the Organization Form
        driver.findElement(By.name("accountname")).sendKeys(ON);
        driver.findElement(By.className("detailedViewTextBox")).sendKeys(WE);
        driver.findElement(By.id("tickersymbol")).sendKeys("TK5");
        driver.findElement(By.name("account_name")).sendKeys("Parent 5");
        driver.findElement(By.id("employees")).sendKeys("55");
        driver.findElement(By.id("email2")).sendKeys("other5@example.com");

        driver.findElement(By.name("industry")).sendKeys(IN);
        driver.findElement(By.name("accounttype")).sendKeys("Customer");

        driver.findElement(By.xpath("//input[@value='U']")).click();
        driver.findElement(By.name("assigned_user_id")).sendKeys("User5");

        driver.findElement(By.id("account_no")).sendKeys("AUTO5");
        driver.findElement(By.id("phone")).sendKeys("555-000-1205");
        driver.findElement(By.id("fax")).sendKeys("555-100-1205");
        driver.findElement(By.id("otherphone")).sendKeys("555-200-1205");
        driver.findElement(By.id("email1")).sendKeys(EM);

        driver.findElement(By.id("ownership")).sendKeys("Private");
        driver.findElement(By.name("rating")).sendKeys("Hot");
        driver.findElement(By.id("siccode")).sendKeys("1235");

        // STEP 7: Save Organization
        driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

        // STEP 8: Close Browser
        Thread.sleep(2000);
        wb.close();
        fis1.close();
        driver.quit();
    }
}
