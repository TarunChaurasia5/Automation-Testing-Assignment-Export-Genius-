package contacts;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * ----------------------------------------------------------------------------
 * CLASS NAME : Contacts 
 * AUTHOR     : Tarun
 *
 * DESCRIPTION:
 *  This Selenium script automates the creation of a new Contact record in the
 *  Vtiger CRM (http://localhost:8888). It performs browser selection from
 *  properties file, CRM login, contact creation, data input and form submission.
 *
 * WORKFLOW:
 *  1. Loads configuration details (Browser, URL, Username, Password) from
 *     commondata.properties file
 *  2. Launches specified browser (Chrome/Edge/Firefox)
 *  3. Logs into CRM using admin credentials
 *  4. Navigates to Contacts module
 *  5. Opens "Create Contact" form
 *  6. Enters predefined test data:
 *
 *     ➤ First Name   : Emily
 *     ➤ Last Name    : Brown
 *     ➤ Organization : Umbrella
 *     ➤ Lead Source  : Partner
 *     ➤ Title        : Engineer
 *     ➤ Department   : Marketing
 *     ➤ Email        : user3@example.com
 *     ➤ Assistant    : Assistant 3
 *     ➤ Assistant Ph : 555-132-1045
 *     ➤ Phone        : 555-385-5910
 *     ➤ Mobile       : 555-453-1834
 *     ➤ Home Phone   : 555-598-2435
 *     ➤ Other Phone  : 555-306-3317
 *     ➤ Fax          : 555-159-1298
 *     ➤ Birthdate    : 1993-01-15
 *     ➤ Reports To   : Manager 3
 *     ➤ Secondary Email : secondary3@example.com
 *     ➤ Assigned To     : Group
 *
 *  7. Saves the contact & validates successful creation (implicitly)
 *  8. Closes the browser
 *
 * PRE-REQUISITES:
 *  - Selenium WebDriver installed
 *  - ChromeDriver / EdgeDriver / GeckoDriver configured in PATH
 *  - Vtiger CRM running on http://localhost:8888/
 *  - commondata.properties file present in project directory
 *
 * VERSION     : 1.0
 * ----------------------------------------------------------------------------
 */

public class Contacts {

	public static void main(String[] args) throws InterruptedException, IOException {

		FileInputStream fis = new FileInputStream("./src/test/resources/commondata.properties");
		Properties pObj = new Properties();
		pObj.load(fis);

		String USERNAME = pObj.getProperty("un");
		String PASSWORD = pObj.getProperty("pwd");
		String BROWSER = pObj.getProperty("browser");
		String URL = pObj.getProperty("url");

		// STEP 1: Launch the browser
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

		// STEP 2: Open CRM login page
		driver.get(URL);

		// STEP 3: Log in to CRM
		driver.findElement(By.name("user_name")).sendKeys(USERNAME);
		driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
		driver.findElement(By.id("submitButton")).click();

		// STEP 4: Navigate to Contacts section
		driver.findElement(By.partialLinkText("Contacts")).click();

		// STEP 5: Open Create Contact form
		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();

		// STEP 6: Fill the contact form fields
		driver.findElement(By.name("firstname")).sendKeys("Emily");
		driver.findElement(By.name("lastname")).sendKeys("Brown");

		driver.findElement(By.id("email")).sendKeys("user3@example.com");
		driver.findElement(By.id("mobile")).sendKeys("555-453-1834");

		driver.findElement(By.id("assistant")).sendKeys("Assistant 3");
		driver.findElement(By.id("assistantphone")).sendKeys("555-132-1045");

		driver.findElement(By.id("homephone")).sendKeys("555-598-2435");
		driver.findElement(By.id("otherphone")).sendKeys("555-306-3317");
		driver.findElement(By.id("fax")).sendKeys("555-159-1298");

		driver.findElement(By.id("phone")).sendKeys("555-385-5910");
		driver.findElement(By.id("secondaryemail")).sendKeys("secondary3@example.com");

		driver.findElement(By.name("birthday")).sendKeys("1993-01-15");
		driver.findElement(By.name("contact_name")).sendKeys("Manager 3");

		// Assigned To → Group
		driver.findElement(By.xpath("//input[@value='T']")).click();

		// STEP 7: Save contact
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		// STEP 8: Close browser
		Thread.sleep(2000);
		driver.quit();
	}
}
