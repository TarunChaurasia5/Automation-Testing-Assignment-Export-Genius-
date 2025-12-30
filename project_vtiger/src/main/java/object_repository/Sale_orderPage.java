package object_repository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Sale_orderPage {
	
	public Sale_orderPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//input[@name='account_id']/../img")
	private WebElement Organization_Name;

	
	public WebElement getOrg_name() {
		return Organization_Name;
	}
	
	@FindBy(xpath = "//textarea[@name='bill_street']")
	private WebElement billingaddress_tesxtfield;

	
	public WebElement getbillingaddress_tesxtfield() {
		return billingaddress_tesxtfield;
	}
	
	@FindBy(xpath = "//textarea[@name='ship_street']")
	private WebElement shippingaddress_textfield;

	
	public WebElement getshippingaddress_tesxtfield() {
		return shippingaddress_textfield;
	}
	
	@FindBy(id = "searchIcon1")
	private WebElement Itemlink;

	
	public WebElement getItemlink() {
		return Itemlink;
	}
	
	
	
	
	
	
	
	
	
	
}

