package object_repository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OpportuntiesPage {
	
	public OpportuntiesPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(name="potentialname")
	private WebElement oppName;
	
	public WebElement getopportuntiesName() {
		return oppName;
	}

	@FindBy(xpath = "//input[@id='related_to']/../img")
	private WebElement Relatedbtn;
	
	public WebElement getRelatedbtn() {
		return Relatedbtn;
	}
	
	@FindBy(id="1")
	private WebElement Org;
	
	public WebElement getOrganizationSelection() {
		return Org;
	}
	
	@FindBy(xpath = "//img[@id='jscal_trigger_closingdate']/../input")
	private WebElement Date;
	
	public WebElement getExpectDate() {
		return Date;
	}
	
	@FindBy(xpath = "//input[@value='  Save  ']")
	private WebElement Save;
	
	public WebElement getSavebtn() {
		return Save;
	}
	
	@FindBy(id="dtlview_Opportunity Name")
	private WebElement oppoNameverification;
	
	public WebElement getoppoNameverification() {
		return oppoNameverification;
	}
	
	
	
	
	
}
