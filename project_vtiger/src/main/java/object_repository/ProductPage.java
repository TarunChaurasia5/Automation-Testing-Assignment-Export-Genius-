package object_repository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {
	
	//img[@alt='Create Product...']
	
	public ProductPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//img[@alt='Create Product...']")
	private WebElement Create_Product;
	
	@FindBy(name = "productname")
	private WebElement Productname;
	
	public WebElement getProductname() {
		return Productname;
	}
	
	public WebElement getCreate_Product() {
		return Create_Product;
	}

}
