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

public class OpportuntiesTest extends Basic {
	@Test
	public void createOpportunties() throws EncryptedDocumentException, IOException {

		HomePage hp = new HomePage(driver);
		hp.getOpportuntieslink().click();

		driver.findElement(By.cssSelector("[alt='Create Opportunity...']")).click();

		String fu = new FileUtility().getDataFromExcelFile("opportunties", 1, 0);
		OpportuntiesPage op = new OpportuntiesPage(driver);
		WebElement oppoName = op.getopportuntiesName();
		oppoName.sendKeys(fu);

		op.getRelatedbtn().click();

		String PID = driver.getWindowHandle();
		Set<String> IDs = driver.getWindowHandles();

		for (String i : IDs) {
			driver.switchTo().window(i);
			String title = driver.getPageSource();
			if (title.equals("Organizations")) {
				break;
			}
		}

		driver.manage().window().maximize();
		op.getOrganizationSelection().click();

		driver.switchTo().window(PID);

		String fu2 = new FileUtility().getDataFromExcelFile(1, 6, "opportunties");
		op.getExpectDate().sendKeys(fu2);

		op.getSavebtn().click();

		String value = op.getoppoNameverification().getText();
		Assert.assertEquals(fu, value);

	}

}
