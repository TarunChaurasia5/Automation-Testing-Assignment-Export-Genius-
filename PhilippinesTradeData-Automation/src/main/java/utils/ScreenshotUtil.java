package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    
    private static ConfigReader config = ConfigReader.getInstance();
    
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            String screenshotDir = config.getProperty("screenshot.path", "test-output/screenshots/");
            File directory = new File(screenshotDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = screenshotDir + fileName;
            
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(filePath));
            
            System.out.println("Screenshot saved: " + filePath);
            return filePath;
            
        } catch (IOException e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }
    
    public static String takeScreenshotForDevice(WebDriver driver, String testName, String device) {
        String fileName = testName + "_" + device + "_" + 
                         new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png";
        return takeScreenshotWithName(driver, fileName);
    }
    
    private static String takeScreenshotWithName(WebDriver driver, String fileName) {
        try {
            String screenshotDir = config.getProperty("screenshot.path", "test-output/screenshots/");
            File directory = new File(screenshotDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            String filePath = screenshotDir + fileName;
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(filePath));
            
            return filePath;
            
        } catch (IOException e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }
    
    public static String captureBase64Screenshot(WebDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            System.err.println("Failed to capture base64 screenshot: " + e.getMessage());
            return null;
        }
    }
}