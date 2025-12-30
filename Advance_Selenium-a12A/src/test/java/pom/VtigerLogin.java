package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import object.LoginPage;

public class VtigerLogin {


//	to initialize
	public VtigerLogin(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	
	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();

		driver.get("http://localhost:8888/");

		//VtigerLogin vt = new VtigerLogin(driver);
		
		LoginPage lo = new LoginPage(driver);

		WebElement un = lo.getUsername();
		WebElement pwd = lo.getPassword();
		WebElement login = lo.getLoginButton();

		//driver.navigate().refresh();
		
		un.sendKeys("admin");
		pwd.sendKeys("password");
		login.click();

		driver.quit();
	}
	

}