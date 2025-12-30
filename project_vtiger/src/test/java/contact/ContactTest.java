package contact;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import basic_utility.Basic;
import generic_utility.FileUtility;
import object_repository.HomePage;

/**
 * Test Class: ContactTest
 * Description:
 * This test case automates the creation of a Contact
 * in the Vtiger CRM application using Selenium and TestNG.
 */
public class ContactTest extends Basic {

    /**
     * Test Method: createContactTest
     * Description:
     * Creates a new Contact using data from an Excel file
     * and validates successful creation using assertions.
     */
    @Test
    public void createContactTest() throws IOException, InterruptedException {

        // Initialize Home Page object
        HomePage hp = new HomePage(driver);

        // Navigate to Contacts module
        hp.getContactLink().click();

        // Click on Create Contact icon
        driver.findElement(By.cssSelector("img[alt='Create Contact...']")).click();

        // Fetch Last Name from Excel file
        String lastName = new FileUtility()
                .getDataFromExcelFile("Contacts", 5, 1);

        // Locate Last Name text field
        WebElement lastName_field = driver.findElement(By.name("lastname"));

        // Enter Last Name
        lastName_field.sendKeys(lastName);

        // Click on Save button
        driver.findElement(By.xpath("//input[@value='  Save  ']")).click();

        // Verification: Validate Last Name after saving Contact
        String actLastName = driver
                .findElement(By.id("dtlview_Last Name"))
                .getText();

        Assert.assertEquals(actLastName, lastName);
    }
}
