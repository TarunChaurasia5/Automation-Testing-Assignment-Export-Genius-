/*
 * Project     : Vtiger CRM Automation
 * Author      : AutomationWithPiyush
 * Version     : 0.0.1
 * Description : This class automates the creation of a new Contact in Vtiger CRM.
 *
 * Workflow Summary:
 * 1. Load configuration from properties file
 * 2. Launch browser based on config
 * 3. Log into Vtiger CRM
 * 4. Navigate to Contacts module
 * 5. Create a new contact by entering last name
 * 6. Validate creation
 * 7. Logout and close browser
 */

package orgnization;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
//import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import basic_utility.Basic;
import generic_utility.FileUtility;
import object_repository.HomePage;

public class OrgnizationTestwrite extends Basic {

//	public static void main(String[] args) throws InterruptedException, IOException {

	@Test
	public void createContactTest() throws IOException, InterruptedException {
		
		
		HomePage hp = new HomePage(driver);

		hp.getOrgLink().click();

		driver.findElement(By.cssSelector("img[alt='Create Organization...']")).click();
		
		String OrgName = new FileUtility().getDataFromExcelFile("Organization", 4, 0);

		WebElement OrgName_field = driver.findElement(By.name("accountname"));

		OrgName_field.sendKeys(OrgName);
		driver.findElement(By.xpath("//input[@value='  Save  ']")).click();

//		verification
		String actOrgName = driver.findElement(By.id("dtlview_Organization Name")).getText();
		Assert.assertEquals(actOrgName, OrgName);
	}
}