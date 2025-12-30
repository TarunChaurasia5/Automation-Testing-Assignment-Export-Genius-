//package basic_utility;
//
//
//import java.time.Duration;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeMethod;
//
//public class Basic {
//	
//	public WebDriver driver;
//	
//	@BeforeClass
//   public void openBrowser() {
//		
//		
//	   driver = new ChromeDriver();
//	   driver.manage().window().maximize();
//	   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
//	   driver.get("https://www.saucedemo.com/");
//   }
//	
//	
//   @BeforeMethod
//   public void login() {
//	   driver.findElement(By.id("user-name")).sendKeys("visual_user");
//	   driver.findElement(By.id("password")).sendKeys("secret_sauce");
//	   driver.findElement(By.id("login-button")).click();
//	   
//   }
//   
//   @AfterMethod
//   public void logout() {
//	   driver.findElement(By.id("react-burger-menu-btn")).click();
//	   driver.findElement(By.id("logout_sidebar_link")).click();
//   }
//   
//   
//   @AfterClass
//   public void closeBroswer() {
//	   driver.quit();
//   }
//   
//
//
//}


package basic_utility;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;


import generic_utility.WebDriverUtility;

import object_repository.HomePage;

public class Basic {
	public WebDriver driver;
	public static WebDriver sdriver;

	@BeforeClass
	public void openBro() {
//		opening browser
		
		String bro = "chrome";
		
		if (bro.equals("chrome")) {
			driver = new ChromeDriver();
		}else if (bro.equals("edge")) {
			driver = new EdgeDriver();
		}else if (bro.equals("firefox")) {
			driver = new FirefoxDriver();
		}else {
			driver = new ChromeDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.get("http://localhost:8888/");
	}

	@BeforeMethod
	public void login() {
//		login
		driver.findElement(By.name("user_name")).sendKeys("admin");
		driver.findElement(By.name("user_password")).sendKeys("manager");
		driver.findElement(By.id("submitButton")).submit();
	}
	
	
//	@BeforeMethod
//	public void login() throws IOException {
//		FileUtility fUtil = new FileUtility();
//
//		String URL = fUtil.getDataFromPropertiesFile("url");
//		String USERNAME = fUtil.getDataFromPropertiesFile("un");
//		String PASSWORD = fUtil.getDataFromPropertiesFile("pwd");
//
//		driver.get(URL);
//
//		LoginPage lp = new LoginPage(driver);
//
//		WebElement username = lp.getUsername();
//		WebElement password = lp.getPassword();
//		WebElement loginBtn = lp.getLoginButton();
//
//		username.sendKeys(USERNAME);
//		password.sendKeys(PASSWORD);
//		loginBtn.submit(); 
//	}

	@AfterMethod
	public void logOut() {
		WebElement profile = new HomePage(driver).getProfileIcon();
		new WebDriverUtility(driver).hover(profile);
		new HomePage(driver).getSignOutLink().click();
	}

	@AfterClass
	public void closeBro() throws InterruptedException {
		Thread.sleep(3000);
		driver.quit();
	}
}