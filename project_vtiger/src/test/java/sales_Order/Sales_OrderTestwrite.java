package sales_Order;

import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
//import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import basic_utility.Basic;
import generic_utility.FileUtility;
import generic_utility.WebDriverUtility;
import object_repository.HomePage;
import object_repository.Sale_orderPage;

public class Sales_OrderTestwrite extends Basic {

//	public static void main(String[] args) throws InterruptedException, IOException {

	@Test
	public void createContactTest() throws IOException, InterruptedException {

		//HomePage hp = new HomePage(driver);

		WebElement morebtn = new HomePage(driver).getmore();
		new WebDriverUtility(driver).hover(morebtn);
		new HomePage(driver).getsales_OrderLink().click();

		driver.findElement(By.cssSelector("img[alt='Create Sales Order...']")).click();

		String Subject = new FileUtility().getDataFromExcelFile("Sales_Order", 4, 0);
	//	String Status = new FileUtility().getDataFromExcelFile("Sales_Order", 4, 7);
		String billing_address = new FileUtility().getDataFromExcelFile("Sales_Order", 4, 10);
		String shipping_address = new FileUtility().getDataFromExcelFile("Sales_Order", 5, 10);

//		String Subject = new FileUtility().getDataFromExcelFile("Sales_Order", 4, 0);

		Sale_orderPage sp = new Sale_orderPage(driver);
		sp.getOrg_name().click();

		String PID = driver.getWindowHandle();
		System.out.println(PID);
		Set<String> IDs = driver.getWindowHandles();
		System.out.println(IDs);

		for (String i : IDs) {
			driver.switchTo().window(i);
			String title = driver.getTitle();
			// if(title.equals("flipkart")) {
			if (title.contains("Organizations")) {
				break;
			}
		}

		driver.manage().window().maximize();

		driver.findElement(By.id("1")).click();

		Alert art = driver.switchTo().alert();
		String text = art.getText();
		System.out.println(text);
		art.accept();

		// driver.close();

		driver.switchTo().window(PID);
		// System.out.print(PID);

		// xpathsurrounding = //input[@name='account_id']/../img
		WebElement Subject_field = driver.findElement(By.xpath("//input[@name='subject']"));

		Subject_field.sendKeys(Subject);

		WebElement Status1 = driver.findElement(By.xpath("//select[@name='sostatus']"));
		String visibleText1 = "Approved";

		WebDriverUtility Status_field = new WebDriverUtility(driver);
		Status_field.select(Status1, visibleText1);

		WebElement billingaddress_tesxtfield = sp.getbillingaddress_tesxtfield();
		WebDriverUtility Billing = new WebDriverUtility(driver);
		Billing.scrollIntoView(billingaddress_tesxtfield, false);

		billingaddress_tesxtfield.sendKeys(billing_address);

		WebElement shippingaddress_textfield = sp.getshippingaddress_tesxtfield();
		shippingaddress_textfield.sendKeys(shipping_address);

		WebElement item = sp.getItemlink();
		WebDriverUtility itemname = new WebDriverUtility(driver);
		itemname.scrollIntoView(item, false);

		item.click();

		String PID1 = driver.getWindowHandle();
		System.out.println(PID1);
		Set<String> IDss = driver.getWindowHandles();
		System.out.println(IDss);

		for (String i : IDss) {
			driver.switchTo().window(i);
			String title = driver.getTitle();
			// if(title.equals("flipkart")) {
			if (title.contains("Products")) {
				break;
			}
		}

		driver.manage().window().maximize();

		driver.findElement(By.id("popup_product_21")).click();

		driver.switchTo().window(PID1);

		driver.findElement(By.id("qty1")).sendKeys("5");

		WebElement save = driver.findElement(By.xpath("//input[@value='  Save  ']"));
		WebDriverUtility savebtn = new WebDriverUtility(driver);
		savebtn.scrollIntoView(save, false);
		save.click();

//		verification
		String actProductName = driver.findElement(By.id("dtlview_Subject")).getText();
		Assert.assertEquals(actProductName, Subject);
	}
}