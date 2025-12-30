package object_repository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage {
	
	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(linkText = "Contacts")
	private WebElement contactLink;
	
	

	@FindBy(linkText = "Organizations")
	private WebElement orgLink;
	
	
	@FindBy(linkText = "Opportunity")
	private WebElement oppLink;
	
	
	@FindBy(linkText = "Leads")
	private WebElement leadLink;

	@FindBy(xpath = "//img[@src='themes/softed/images/user.PNG']")
	private WebElement profileIcon;
	
	@FindBy(linkText = "More")
	private WebElement more;
	
//	@FindBy(linkText = "More")
//	private WebElement more;
	
	@FindBy(name = "Sales Order")
	private WebElement sales_OrderLink;

	@FindBy(linkText = "Sign Out")
	private WebElement signOutLink;
	
	@FindBy(linkText = "Products")
	private WebElement Productslink;

	public WebElement getContactLink() {
		return contactLink;
	}

	public WebElement getProfileIcon() {
		return profileIcon;
	}
	
	public WebElement getmore() {
		return more;
	}
	
	public WebElement getsales_OrderLink() {
		return sales_OrderLink;
	}

	public WebElement getSignOutLink() {
		return signOutLink;
	}
	
	public WebElement getProductslink() {
		return Productslink;
	}
	
	
//	WebElement profile = new HomePage(driver).getProfileIcon();
//	new WebDriverUtility(driver).hover(profile);
//	new HomePage(driver).getSignOutLink().click();
	
	public WebElement getOrgLink() {
		return orgLink;
	}

	public WebElement getOppLink() {
		return oppLink;
	}

	public WebElement getLeadLink() {
		return leadLink;
	}

	
	
	
}