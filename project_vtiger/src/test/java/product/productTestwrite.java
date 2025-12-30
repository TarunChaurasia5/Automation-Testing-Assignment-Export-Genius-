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

public class productTestwrite extends Basic {
	
	
	@Test
	public void ProductTest() throws EncryptedDocumentException, IOException {
		
		HomePage hp = new HomePage(driver);
		hp.getProductslink().click();
		
		ProductPage pp = new ProductPage(driver);
		pp.getCreate_Product().click();
		
		String ProductName = new FileUtility().getDataFromExcelFile("Sheet1", 4, 1);
		pp.getProductname().sendKeys(ProductName);
		
		driver.findElement(By.xpath("//input[@value='  Save  ']")).click();

		
		//validation
		String pName = driver.findElement(By.id("dtlview_Product Name")).getText();
		Assert.assertEquals(pName, ProductName);
		
		
	}

}
