package opportunities;

import java.io.IOException;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import basic_utility.Basic;
import generic_utility.FileUtility;
import object_repository.HomePage;
import object_repository.OpportuntiesPage;

/**
 * Test Class: OpportuntiesTest
 * -----------------------------------
 * This class contains TestNG test cases related to
 * the Opportunities module.
 * 
 * It extends the Basic class to reuse browser setup,
 * driver initialization, and teardown methods.
 */
public class OpportunitieswriteTest extends Basic {

	/**
	 * Test Method: createOpportunties
	 * -----------------------------------
	 * This test case validates the creation of a new Opportunity.
	 * 
	 * Steps performed:
	 * 1. Navigate to Opportunities page
	 * 2. Click on Create Opportunity
	 * 3. Fetch Opportunity name from Excel
	 * 4. Enter Opportunity details
	 * 5. Select related Organization from popup window
	 * 6. Enter expected close date
	 * 7. Save the Opportunity
	 * 8. Verify the Opportunity name
	 */
	@Test
	public void createOpportunties() throws EncryptedDocumentException, IOException {

		// Create HomePage object using Page Object Model
		HomePage hp = new HomePage(driver);

		// Click on Opportunities link from Home Page
		hp.getOpportuntieslink().click();

		// Click on Create Opportunity button using CSS selector
		driver.findElement(By.cssSelector("[alt='Create Opportunity...']")).click();

		// Fetch Opportunity name from Excel file
		String fu = new FileUtility().getDataFromExcelFile("opportunties", 1, 0);

		// Create Opportunities Page object
		OpportuntiesPage op = new OpportuntiesPage(driver);

		// Locate Opportunity name text field and enter value
		WebElement oppoName = op.getopportuntiesName();
		oppoName.sendKeys(fu);

		// Click on Related Organization button
		op.getRelatedbtn().click();

		// Store parent window ID
		String PID = driver.getWindowHandle();

		// Get all window IDs
		Set<String> IDs = driver.getWindowHandles();

		// Switch control to Organization popup window
		for (String i : IDs) {
			driver.switchTo().window(i);
			String title = driver.getPageSource();
			if (title.equals("Organizations")) {
				break;
			}
		}

		// Maximize the popup window
		driver.manage().window().maximize();

		// Select an Organization
		op.getOrganizationSelection().click();

		// Switch back to parent window
		driver.switchTo().window(PID);

		// Fetch Expected Close Date from Excel file
		String fu2 = new FileUtility().getDataFromExcelFile(1, 6, "opportunties");

		// Enter Expected Close Date
		op.getExpectDate().sendKeys(fu2);

		// Click on Save button
		op.getSavebtn().click();

		// Fetch Opportunity name for verification
		String value = op.getoppoNameverification().getText();

		// Validate Opportunity name using TestNG assertion
		Assert.assertEquals(fu, value);

	}

}
