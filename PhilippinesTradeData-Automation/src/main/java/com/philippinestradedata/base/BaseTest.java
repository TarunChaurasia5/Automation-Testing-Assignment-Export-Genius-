package com.philippinestradedata.base;

import java.lang.reflect.Method;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class BaseTest {
    
    protected WebDriver driver;
    protected ConfigReader config;
    
    @BeforeSuite
    public void beforeSuite() {
        config = ConfigReader.getInstance();
        System.out.println("Test Suite Started");
        System.out.println("Base URL: " + config.getProperty("base.url"));
        System.out.println("Browser: " + config.getProperty("browser"));
    }
    
    @BeforeMethod
    public void setUp(Method method) {
        // Get browser configuration
        String browser = config.getProperty("browser");
        boolean headless = config.getBooleanProperty("headless");
        
        // Initialize WebDriver
        WebDriverFactory.initializeDriver(browser, headless);
        driver = WebDriverFactory.getDriver();
        
        System.out.println("Test Started: " + method.getName());
        System.out.println("Browser: " + browser + (headless ? " (headless)" : ""));
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        String testName = result.getName();
        
        // Take screenshot on failure
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Test FAILED: " + testName);
            ScreenshotUtil.takeScreenshot(driver, testName);
            
            // Log failure details
            Throwable throwable = result.getThrowable();
            if (throwable != null) {
                System.err.println("Failure cause: " + throwable.getMessage());
                System.err.println("Stack trace:");
                throwable.printStackTrace();
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            System.out.println("Test PASSED: " + testName);
        } else if (result.getStatus() == ITestResult.SKIP) {
            System.out.println("Test SKIPPED: " + testName);
        }
        
        // Quit WebDriver
        WebDriverFactory.quitDriver();
    }
    
    @AfterSuite
    public void afterSuite() {
        System.out.println("Test Suite Completed");
    }
    
    protected void navigateToHomePage() {
        String url = config.getProperty("base.url");
        System.out.println("Navigating to: " + url);
        driver.get(url);
        
        // Wait for page to load
        try {
            Thread.sleep(1000); // Small wait for page load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Page Title: " + driver.getTitle());
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }
    
    protected void setViewport(String deviceType) {
        int width, height;
        
        switch (deviceType.toLowerCase()) {
            case "mobile":
                width = config.getMobileWidth();
                height = config.getMobileHeight();
                System.out.println("Setting viewport to Mobile: " + width + "x" + height);
                break;
                
            case "tablet":
                width = config.getTabletWidth();
                height = config.getTabletHeight();
                System.out.println("Setting viewport to Tablet: " + width + "x" + height);
                break;
                
            case "desktop":
                width = config.getDesktopWidth();
                height = config.getDesktopHeight();
                System.out.println("Setting viewport to Desktop: " + width + "x" + height);
                break;
                
            default:
                System.err.println("Unknown device type: " + deviceType + ". Using desktop.");
                width = config.getDesktopWidth();
                height = config.getDesktopHeight();
        }
        
        WebDriverFactory.setViewport(width, height);
    }
    
    // Helper method to get config values
    protected String getBaseUrl() {
        return config.getProperty("base.url");
    }
    
    protected int getMobileLoadThreshold() {
        return config.getMobileLoadThreshold();
    }
    
    protected int getDesktopLoadThreshold() {
        return config.getDesktopLoadThreshold();
    }
    
    // Helper method to log test information
    protected void logInfo(String message) {
        System.out.println("[INFO] " + message);
    }
    
    protected void logError(String message) {
        System.err.println("[ERROR] " + message);
    }
    
    protected void logWarning(String message) {
        System.out.println("[WARNING] " + message);
    }
    
 // Add this method to your BaseTest.java class
    protected void takeScreenshot(String screenshotName) {
        try {
            // Create screenshots directory
            java.io.File directory = new java.io.File("test-output/screenshots/");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            // Generate filename with timestamp
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = sdf.format(new java.util.Date());
            String fileName = screenshotName + "_" + timestamp + ".png";
            String filePath = "test-output/screenshots/" + fileName;
            
            // Take screenshot
            java.io.File screenshot = ((org.openqa.selenium.TakesScreenshot) driver)
                .getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            org.apache.commons.io.FileUtils.copyFile(screenshot, new java.io.File(filePath));
            
            logInfo("Screenshot saved: " + filePath);
        } catch (Exception e) {
            logError("Failed to take screenshot: " + e.getMessage());
        }
    }
}