package product;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import basic_utility.Basic;
import generic_utility.FileUtility;
import object_repository.HomePage;
import object_repository.ProductPage;

/**
 * Test Class: productTest
 * Description:
 * This test case automates the creation of a Product.
 * It performs the following actions:
 * 1. Navigates to the Products page
 * 2. Creates a new Product
 * 3. Enters Product name using Excel data
 * 4. Saves the Product
 * 5. Verifies the created Product name
 * 
 * Author: Tarun Chaurasia
 */
public class productTest extends Basic {

    /**
     * Test Method: ProductTest
     * Description:
     * Creates a product using data driven testing
     * and validates the product name after saving.
     */
    @Test
    public void ProductTest() throws EncryptedDocumentException, IOException {

        // Initialize Home Page Object
        HomePage hp = new HomePage(driver);

        // Navigate to Products page
        hp.getProductslink().click();

        // Initialize Product Page Object
        ProductPage pp = new ProductPage(driver);

        // Click on Create Product button
        pp.getCreate_Product().click();

        // Fetch Product Name from Excel file
        String ProductName = new FileUtility()
                .getDataFromExcelFile("Sheet1", 4, 1);

        // Enter Product Name
        pp.getProductname().sendKeys(ProductName);

        // Click on Save button
        driver.findElement(By.xpath("//input[@value='  Save  ']")).click();

        // Verification: Validate Product Name after saving
        String pName = driver.findElement(By.id("dtlview_Product Name")).getText();
        Assert.assertEquals(pName, ProductName);
    }
}
