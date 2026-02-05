package com.philippinestradedata.tests;

import com.philippinestradedata.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ResponsivenessTest extends BaseTest {
    
    @Test(priority = 1, description = "Test homepage on mobile device")
    public void testMobileResponsiveness() {
        logInfo("Starting mobile responsiveness test");
        
        // Set mobile viewport
        setViewport("mobile");
        navigateToHomePage();
        
        // Verify page title
        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("Philippines Trade Data"), 
            "Page title should contain 'Philippines Trade Data'. Actual: " + pageTitle);
        logInfo("Page title verified: " + pageTitle);
        
        // Check if main heading is visible
        try {
            WebElement heading = driver.findElement(By.tagName("h1"));
            Assert.assertTrue(heading.isDisplayed(), "Main heading should be displayed");
            logInfo("Main heading is displayed: " + heading.getText());
        } catch (Exception e) {
            logWarning("No h1 heading found on the page");
        }
        
        // Check for mobile-specific elements
        boolean isMobileView = driver.manage().window().getSize().width <= 768;
        Assert.assertTrue(isMobileView, "Should be in mobile viewport");
        logInfo("Mobile viewport verified: " + driver.manage().window().getSize());
        
        logInfo("Mobile responsiveness test completed");
    }
    
    @Test(priority = 2, description = "Test page load time on mobile")
    public void testMobilePageLoadTime() {
        logInfo("Starting mobile page load time test");
        
        setViewport("mobile");
        
        // Measure load time
        long startTime = System.currentTimeMillis();
        navigateToHomePage();
        long endTime = System.currentTimeMillis();
        
        long loadTimeMs = endTime - startTime;
        double loadTimeSeconds = loadTimeMs / 1000.0;
        
        logInfo("Mobile page load time: " + loadTimeSeconds + " seconds");
        
        // Check against threshold
        int threshold = getMobileLoadThreshold();
        Assert.assertTrue(loadTimeSeconds <= threshold, 
            "Mobile load time should be ≤ " + threshold + 
            " seconds. Actual: " + loadTimeSeconds + " seconds");
        
        logInfo("Mobile page load time test passed");
    }
    
    @Test(priority = 3, description = "Test navigation links")
    public void testNavigationLinks() {
        logInfo("Starting navigation links test");
        
        navigateToHomePage();
        
        // Find all links on the page
        List<WebElement> links = driver.findElements(By.tagName("a"));
        logInfo("Found " + links.size() + " links on the page");
        
        // Test first 3 links (to avoid too many requests)
        int linksTested = 0;
        int linksWorking = 0;
        
        for (int i = 0; i < Math.min(3, links.size()); i++) {
            WebElement link = links.get(i);
            String href = link.getAttribute("href");
            
            if (href != null && !href.isEmpty() && href.startsWith("http")) {
                linksTested++;
                
                try {
                    String originalWindow = driver.getWindowHandle();
                    
                    // Open link in new tab using JavascriptExecutor
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("window.open(arguments[0]);", href);
                    
                    // Switch to new tab
                    for (String windowHandle : driver.getWindowHandles()) {
                        if (!originalWindow.equals(windowHandle)) {
                            driver.switchTo().window(windowHandle);
                            break;
                        }
                    }
                    
                    // Wait for page to load
                    try {
                        Thread.sleep(2000); // Wait 2 seconds for page load
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    
                    // Verify page loaded
                    String pageTitle = driver.getTitle();
                    Assert.assertNotNull(pageTitle, "Page title should not be null");
                    
                    logInfo("Link " + (i + 1) + " works: " + href + " - Title: " + pageTitle);
                    linksWorking++;
                    
                    // Close tab and switch back
                    driver.close();
                    driver.switchTo().window(originalWindow);
                    
                } catch (Exception e) {
                    logError("Link broken: " + href + " - Error: " + e.getMessage());
                }
            }
        }
        
        logInfo("Links tested: " + linksTested + ", Working: " + linksWorking);
        Assert.assertTrue(linksWorking > 0, "At least one link should work");
        
        logInfo("Navigation links test completed");
    }
    
    @Test(priority = 4, description = "Test responsive design using JavaScript checks")
    public void testResponsiveDesignChecks() {
        logInfo("Starting responsive design checks");
        
        String[] devices = {"mobile", "tablet", "desktop"};
        
        for (String device : devices) {
            logInfo("Testing responsive design on " + device.toUpperCase() + "...");
            
            // Set viewport for device
            setViewport(device);
            navigateToHomePage();
            
            // Perform JavaScript-based responsive checks
            checkResponsiveIssues(device);
            
            logInfo(device.toUpperCase() + " responsive checks completed");
        }
        
        logInfo("All responsive design checks completed");
    }
    
    /**
     * Check for common responsive design issues using JavaScript
     */
    private void checkResponsiveIssues(String device) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        try {
            // 1. Check for horizontal scrolling
            boolean hasHorizontalScroll = (Boolean) js.executeScript(
                "return document.documentElement.scrollWidth > document.documentElement.clientWidth;"
            );
            
            if (hasHorizontalScroll) {
                logWarning("Horizontal scrolling detected on " + device);
            } else {
                logInfo("No horizontal scrolling on " + device);
            }
            
            // 2. Check viewport width matches device
            int viewportWidth = driver.manage().window().getSize().width;
            logInfo(device.toUpperCase() + " viewport width: " + viewportWidth + "px");
            
            // 3. Check for missing viewport meta tag
            boolean hasViewportMeta = (Boolean) js.executeScript(
                "var metaTags = document.getElementsByTagName('meta');" +
                "for (var i = 0; i < metaTags.length; i++) {" +
                "   if (metaTags[i].getAttribute('name') === 'viewport') {" +
                "       return true;" +
                "   }" +
                "}" +
                "return false;"
            );
            
            if (!hasViewportMeta) {
                logWarning("Missing viewport meta tag on " + device);
            } else {
                logInfo("Viewport meta tag present on " + device);
            }
            
            // 4. Check for responsive images
            int responsiveImages = ((Long) js.executeScript(
                "var images = document.getElementsByTagName('img');" +
                "var responsiveCount = 0;" +
                "for (var i = 0; i < images.length; i++) {" +
                "   var img = images[i];" +
                "   if (img.style.maxWidth === '100%' || img.getAttribute('srcset')) {" +
                "       responsiveCount++;" +
                "   }" +
                "}" +
                "return responsiveCount;"
            )).intValue();
            
            logInfo("Responsive images found: " + responsiveImages);
            
            // 5. Check media queries (simplified check)
            boolean hasMediaQueries = (Boolean) js.executeScript(
                "var styles = document.styleSheets;" +
                "for (var i = 0; i < styles.length; i++) {" +
                "   try {" +
                "       var rules = styles[i].cssRules || styles[i].rules;" +
                "       for (var j = 0; j < rules.length; j++) {" +
                "           if (rules[j].type === 4) { // CSSRule.MEDIA_RULE" +
                "               return true;" +
                "           }" +
                "       }" +
                "   } catch (e) {" +
                "       // Cross-origin stylesheet, skip" +
                "   }" +
                "}" +
                "return false;"
            );
            
            if (hasMediaQueries) {
                logInfo("Media queries detected on " + device);
            } else {
                logWarning("No media queries detected on " + device);
            }
            
        } catch (Exception e) {
            logError("Error during responsive checks on " + device + ": " + e.getMessage());
        }
    }
    
    @Test(priority = 5, description = "Test element visibility across devices")
    public void testElementVisibility() {
        logInfo("Starting element visibility test across devices");
        
        String[] devices = {"mobile", "tablet", "desktop"};
        String[] elementsToCheck = {"h1", "h2", "nav", "footer", "button", "img"};
        
        for (String device : devices) {
            logInfo("Testing element visibility on " + device.toUpperCase() + "...");
            
            setViewport(device);
            navigateToHomePage();
            
            for (String elementTag : elementsToCheck) {
                try {
                    List<WebElement> elements = driver.findElements(By.tagName(elementTag));
                    logInfo("Found " + elements.size() + " <" + elementTag + "> elements on " + device);
                    
                    // Check if first element is visible (if exists)
                    if (!elements.isEmpty()) {
                        WebElement firstElement = elements.get(0);
                        Assert.assertTrue(firstElement.isDisplayed(), 
                            elementTag + " should be visible on " + device);
                        logInfo("<" + elementTag + "> is visible on " + device);
                    }
                } catch (Exception e) {
                    logWarning("Could not check <" + elementTag + "> on " + device + ": " + e.getMessage());
                }
            }
            
            logInfo(device.toUpperCase() + " element visibility test completed");
        }
        
        logInfo("All element visibility tests completed");
    }
    
    @Test(priority = 6, description = "Test touch-friendly elements on mobile")
    public void testTouchFriendlyElements() {
        logInfo("Starting touch-friendly elements test");
        
        // Only test on mobile
        setViewport("mobile");
        navigateToHomePage();
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        try {
            // Check clickable elements size (should be at least 44x44px for touch)
            List<WebElement> clickableElements = driver.findElements(
                By.xpath("//a | //button | //input[@type='button'] | //input[@type='submit']")
            );
            
            logInfo("Found " + clickableElements.size() + " clickable elements on mobile");
            
            int smallElements = 0;
            for (WebElement element : clickableElements) {
                try {
                    int width = element.getSize().width;
                    int height = element.getSize().height;
                    
                    if (width < 44 || height < 44) {
                        smallElements++;
                        logWarning("Small clickable element: " + element.getTagName() + 
                                  " - Size: " + width + "x" + height + "px");
                    }
                } catch (Exception e) {
                    // Skip if element is not visible or not interactable
                }
            }
            
            if (smallElements > 0) {
                logWarning("Found " + smallElements + " clickable elements smaller than 44x44px");
            } else {
                logInfo("All clickable elements are touch-friendly (≥44x44px)");
            }
            
            // Check for hover effects that might not work on touch
            boolean hasHoverEffects = (Boolean) js.executeScript(
                "var styles = document.styleSheets;" +
                "for (var i = 0; i < styles.length; i++) {" +
                "   try {" +
                "       var rules = styles[i].cssRules || styles[i].rules;" +
                "       for (var j = 0; j < rules.length; j++) {" +
                "           if (rules[j].type === 1 && rules[j].selectorText && " +
                "               rules[j].selectorText.includes(':hover')) {" +
                "               return true;" +
                "           }" +
                "       }" +
                "   } catch (e) {" +
                "       // Skip cross-origin stylesheets" +
                "   }" +
                "}" +
                "return false;"
            );
            
            if (hasHoverEffects) {
                logWarning("Hover effects detected - may not work well on touch devices");
            }
            
        } catch (Exception e) {
            logError("Error testing touch-friendly elements: " + e.getMessage());
        }
        
        logInfo("Touch-friendly elements test completed");
    }
}