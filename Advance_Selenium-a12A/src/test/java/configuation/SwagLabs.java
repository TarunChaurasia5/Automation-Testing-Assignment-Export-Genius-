package configuation;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
//import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import basic_utility.Basic;

public class SwagLabs extends Basic {
	
	
	
	@Test
	public void popups() throws InterruptedException {
		Thread.sleep(3000);
		Alert art = driver.switchTo().alert();
		art.accept();
		
		
	}
	
	
	
	@Test
	public void addthecart() throws InterruptedException {
	
		driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
		Thread.sleep(2000);
	}
	
	@Test
	public void removethecart() throws InterruptedException {
		
		driver.findElement(By.id("remove-sauce-labs-backpack")).click();
		Thread.sleep(2000);
	}

}


