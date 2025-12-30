package object_repository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	public LoginPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}

//	declaration
	@FindBy(name = "user_name")
	private WebElement username;

	@FindBy(name = "user_password")
	private WebElement password;

	@FindBy(id = "submitButton")
	private WebElement loginBtn;
	

//	getter => public services
	public WebElement getUsername() {
		return username;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getLoginButton() {
		return loginBtn;
	}
	
	

}


//package obejct_respository;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//
//public class VtigerLogin {
//	
//	WebDriver driver;
////	 decalaration
//	
//	@FindBy(name="user_name")
//	private WebElement username;
//	
//	@FindBy(name="user_password")
//	private WebElement password;
//	
//	@FindBy(id="submitButton")
//	private WebElement loginbtn;
//	
////	    Encapusalation
////	    getter method-> public services
//	    public WebElement getUsername() {
//	    	return username;
//	    }
//	    public WebElement getPassword() {
//	    	return password;
//	    }
//	    public WebElement getLoginButton() {
//	    	return loginbtn;
//	    }
//	    
//	    
////	    inilization
//	    public VtigerLogin(WebDriver driver){
//	    	 
//
//	    	 
//	    	 PageFactory.initElements(driver,this);
//	     }
//	     public static void main(String[] args) {
//	 		WebDriver driver=new ChromeDriver();
//	 		
//	 		driver.get("http://localhost:8888/");
//	 		VtigerLogin vt=new VtigerLogin(driver);
//	 		WebElement un=vt.getUsername();
//	 		WebElement pwd=vt.getPassword();
//	 		WebElement login=vt.getLoginButton();
//	 		
//	 		un.sendKeys("admin");
//	 		pwd.sendKeys("manager");
//	 		login.click();
//	 	}
//     
//		
//	
//  }