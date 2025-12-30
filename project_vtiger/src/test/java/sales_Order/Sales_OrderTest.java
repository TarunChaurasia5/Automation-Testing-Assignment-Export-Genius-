package sales_Order;

import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import basic_utility.Basic;
import generic_utility.FileUtility;
import generic_utility.WebDriverUtility;
import object_repository.HomePage;
import object_repository.Sale_orderPage;

/**
 * Test Class: Sales_OrderTest
 * Description:
 * This test case automates the creation of a Sales Order.
 * It performs the following actions:
 * 1. Navigates to Sales Order page
 * 2. Creates a new Sales Order
 * 3. Selects Organization and Product from popup windows
 * 4. Fills mandatory fields using Excel data
 * 5. Saves the Sales Order
 * 6. Verifies the created Sales Order
 * 
 * Author: Tarun Chaurasia
 */
public class Sales_OrderTest extends Basic {

    /**
     * Test Method: createContactTest
     * Description:
     * Creates a Sales Order using data driven approach
     * and validates the subject after saving.
     */
    @Test
    public void createContactTest() throws IOException, InterruptedException {

        // Initialize Home Page Object
//        HomePage hp = new HomePage(driver);

        // Hover on "More" option and click on Sales Order
        WebElement morebtn = new HomePage(driver).getmore();
        new WebDriverUtility(driver).hover(morebtn);
        new HomePage(driver).getsales_OrderLink().click();

        // Click on Create Sales Order icon
        driver.findElement(By.cssSelector("img[alt='Create Sales Order...']")).click();

        // Fetch test data from Excel file
        String Subject = new FileUtility().getDataFromExcelFile("Sales_Order", 4, 0);
        String billing_address = new FileUtility().getDataFromExcelFile("Sales_Order", 4, 10);
        String shipping_address = new FileUtility().getDataFromExcelFile("Sales_Order", 5, 10);

        // Initialize Sales Order Page Object
        Sale_orderPage sp = new Sale_orderPage(driver);

        // Click on Organization lookup icon
        sp.getOrg_name().click();

        // Store Parent Window ID
        String PID = driver.getWindowHandle();
        Set<String> IDs = driver.getWindowHandles();

        // Switch to Organization popup window
        for (String i : IDs) {
            driver.switchTo().window(i);
            if (driver.getTitle().contains("Organizations")) {
                break;
            }
        }

        driver.manage().window().maximize();

        // Select organization
        driver.findElement(By.id("1")).click();

        // Handle alert popup
        Alert art = driver.switchTo().alert();
        System.out.println(art.getText());
        art.accept();

        // Switch back to Parent Window
        driver.switchTo().window(PID);

        // Enter Subject
        WebElement Subject_field = driver.findElement(By.xpath("//input[@name='subject']"));
        Subject_field.sendKeys(Subject);

        // Select Status from dropdown
        WebElement Status1 = driver.findElement(By.xpath("//select[@name='sostatus']"));
        WebDriverUtility Status_field = new WebDriverUtility(driver);
        Status_field.select(Status1, "Approved");

        // Enter Billing Address
        WebElement billingaddress_tesxtfield = sp.getbillingaddress_tesxtfield();
        WebDriverUtility Billing = new WebDriverUtility(driver);
        Billing.scrollIntoView(billingaddress_tesxtfield, false);
        billingaddress_tesxtfield.sendKeys(billing_address);

        // Enter Shipping Address
        WebElement shippingaddress_textfield = sp.getshippingaddress_tesxtfield();
        shippingaddress_textfield.sendKeys(shipping_address);

        // Click on Item lookup icon
        WebElement item = sp.getItemlink();
        WebDriverUtility itemname = new WebDriverUtility(driver);
        itemname.scrollIntoView(item, false);
        item.click();

        // Store Parent Window ID for Product popup
        String PID1 = driver.getWindowHandle();
        Set<String> IDss = driver.getWindowHandles();

        // Switch to Product popup window
        for (String i : IDss) {
            driver.switchTo().window(i);
            if (driver.getTitle().contains("Products")) {
                break;
            }
        }

        driver.manage().window().maximize();

        // Select Product
        driver.findElement(By.id("popup_product_21")).click();

        // Switch back to Parent Window
        driver.switchTo().window(PID1);

        // Enter Quantity
        driver.findElement(By.id("qty1")).sendKeys("5");

        // Click Save button
        WebElement save = driver.findElement(By.xpath("//input[@value='  Save  ']"));
        WebDriverUtility savebtn = new WebDriverUtility(driver);
        savebtn.scrollIntoView(save, false);
        save.click();

        // Verification: Validate Subject after saving Sales Order
        String actProductName = driver.findElement(By.id("dtlview_Subject")).getText();
        Assert.assertEquals(actProductName, Subject);
    }
}
