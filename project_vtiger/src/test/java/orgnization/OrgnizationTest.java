package orgnization;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import basic_utility.Basic;
import generic_utility.FileUtility;
import object_repository.HomePage;

/**
 * Test Class: OrgnizationTest
 * Description:
 * This test case automates the creation of an Organization
 * in the Vtiger CRM application using Selenium and TestNG.
 */
public class OrgnizationTest extends Basic {

    /**
     * Test Method: Orgnization
     * Description:
     * Creates a new Organization using data fetched from
     * an Excel file and validates successful creation.
     */
    @Test
    public void Orgnization() throws IOException, InterruptedException {

        // Initialize Home Page object
        HomePage hp = new HomePage(driver);

        // Navigate to Organization module
        hp.getOrgLink().click();

        // Click on Create Organization icon
        driver.findElement(By.cssSelector("img[alt='Create Organization...']")).click();

        // Fetch Organization Name from Excel file
        String OrgName = new FileUtility()
                .getDataFromExcelFile("Organization", 4, 0);

        // Locate Organization Name text field
        WebElement OrgName_field = driver.findElement(By.name("accountname"));

        // Enter Organization Name
        OrgName_field.sendKeys(OrgName);

        // Click on Save button
        driver.findElement(By.xpath("//input[@value='  Save  ']")).click();

        // Verification: Validate Organization Name after saving
        String actOrgName = driver
                .findElement(By.id("dtlview_Organization Name"))
                .getText();

        Assert.assertEquals(actOrgName, OrgName);
    }
}
