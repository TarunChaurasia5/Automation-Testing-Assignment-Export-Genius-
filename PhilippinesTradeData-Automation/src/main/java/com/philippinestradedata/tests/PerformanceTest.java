package com.philippinestradedata.tests;

import com.philippinestradedata.base.BaseTest;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.HashMap;

public class PerformanceTest extends BaseTest {
    
    @Test(priority = 1, description = "Test mobile page load time")
    public void testMobilePageLoadTime() {
        logInfo("Starting mobile page load time test");
        
        setViewport("mobile");
        
        // Measure load time
        long startTime = System.currentTimeMillis();
        navigateToHomePage();
        long endTime = System.currentTimeMillis();
        
        long loadTime = endTime - startTime;
        double loadTimeSeconds = loadTime / 1000.0;
        
        logInfo("Mobile page load time: " + loadTimeSeconds + " seconds");
        
        // Get performance metrics using our utility method
        Map<String, Object> perfMetrics = getPerformanceMetrics();
        logInfo("Performance Metrics: " + perfMetrics);
        
        // Check against requirement (≤ 2 seconds)
        int mobileThreshold = getMobileLoadThreshold();
        logInfo("Mobile load threshold: " + mobileThreshold + " seconds");
        
        if (loadTimeSeconds <= mobileThreshold) {
            logInfo("✓ PASS: Mobile load time within threshold");
        } else {
            logWarning("✗ FAIL: Mobile load time exceeds threshold");
        }
        
        Assert.assertTrue(loadTimeSeconds <= mobileThreshold, 
            "Mobile load time should be ≤ " + mobileThreshold + 
            " seconds. Actual: " + loadTimeSeconds + " seconds");
        
        // Check for performance issues
        checkPerformanceIssues();
        
        logInfo("Mobile page load time test completed");
    }
    
    @Test(priority = 2, description = "Test desktop page load time")
    public void testDesktopPageLoadTime() {
        logInfo("Starting desktop page load time test");
        
        setViewport("desktop");
        
        // Measure load time
        long startTime = System.currentTimeMillis();
        navigateToHomePage();
        long endTime = System.currentTimeMillis();
        
        long loadTime = endTime - startTime;
        double loadTimeSeconds = loadTime / 1000.0;
        
        logInfo("Desktop page load time: " + loadTimeSeconds + " seconds");
        
        // Check against requirement (≤ 5 seconds)
        int desktopThreshold = getDesktopLoadThreshold();
        logInfo("Desktop load threshold: " + desktopThreshold + " seconds");
        
        if (loadTimeSeconds <= desktopThreshold) {
            logInfo("✓ PASS: Desktop load time within threshold");
        } else {
            logWarning("✗ FAIL: Desktop load time exceeds threshold");
        }
        
        Assert.assertTrue(loadTimeSeconds <= desktopThreshold, 
            "Desktop load time should be ≤ " + desktopThreshold + 
            " seconds. Actual: " + loadTimeSeconds + " seconds");
        
        logInfo("Desktop page load time test completed");
    }
    
    @Test(priority = 3, description = "Identify slow pages and performance issues")
    public void identifyPerformanceIssues() {
        logInfo("Starting performance issues identification");
        
        navigateToHomePage();
        
        // Get resource timing
        Map<String, Long> resourceTiming = getResourceLoadTimes();
        
        logInfo("=== Resource Load Times ===");
        resourceTiming.forEach((resource, time) -> {
            if (time > 1000) { // More than 1 second
                logWarning("SLOW RESOURCE: " + resource + " - " + time + "ms");
            } else {
                logInfo("Resource: " + resource + " - " + time + "ms");
            }
        });
        
        // Check for heavy images
        int imageCount = getImageCount();
        long totalImageSize = getTotalImageSize();
        
        logInfo("Total Images: " + imageCount);
        logInfo("Total Image Size: " + totalImageSize + " bytes (" + 
                (totalImageSize / 1024) + " KB)");
        
        if (totalImageSize > 5000000) { // More than 5MB
            logWarning("HEAVY IMAGES: Total image size exceeds 5MB!");
        } else if (totalImageSize > 2000000) { // More than 2MB
            logWarning("LARGE IMAGES: Total image size exceeds 2MB");
        } else {
            logInfo("✓ Image size within acceptable range");
        }
        
        // Check number of requests
        int totalRequests = getTotalRequests();
        logInfo("Total Requests: " + totalRequests);
        
        if (totalRequests > 100) {
            logWarning("HIGH REQUESTS: More than 100 requests detected!");
        } else if (totalRequests > 50) {
            logWarning("MODERATE REQUESTS: More than 50 requests detected");
        } else {
            logInfo("✓ Request count within acceptable range");
        }
        
        // Check page load time using Navigation Timing API
        double pageLoadTime = getPageLoadTime();
        logInfo("Navigation Timing API - Page Load Time: " + pageLoadTime + " seconds");
        
        // Take screenshot
        utils.ScreenshotUtil.takeScreenshot(driver, "performance_analysis");
        
        logInfo("Performance issues identification completed");
    }
    
    @Test(priority = 4, description = "Test performance across multiple devices")
    public void testPerformanceAcrossDevices() {
        logInfo("Starting performance test across all devices");
        
        String[] devices = {"mobile", "tablet", "desktop"};
        
        for (String device : devices) {
            logInfo("Testing performance on " + device.toUpperCase() + "...");
            
            setViewport(device);
            
            // Measure load time 3 times for average
            double totalLoadTime = 0;
            int iterations = 3;
            
            for (int i = 1; i <= iterations; i++) {
                logInfo("Iteration " + i + " of " + iterations);
                
                long startTime = System.currentTimeMillis();
                navigateToHomePage();
                long endTime = System.currentTimeMillis();
                
                double loadTimeSeconds = (endTime - startTime) / 1000.0;
                totalLoadTime += loadTimeSeconds;
                
                logInfo("Load time: " + loadTimeSeconds + " seconds");
                
                // Wait between iterations
                if (i < iterations) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            
            double averageLoadTime = totalLoadTime / iterations;
            logInfo("Average load time on " + device + ": " + averageLoadTime + " seconds");
            
            // Check against threshold
            int threshold = device.equals("mobile") ? getMobileLoadThreshold() : getDesktopLoadThreshold();
            
            if (averageLoadTime <= threshold) {
                logInfo("✓ " + device.toUpperCase() + ": PASS - Within threshold of " + threshold + " seconds");
            } else {
                logWarning("✗ " + device.toUpperCase() + ": FAIL - Exceeds threshold of " + threshold + " seconds");
            }
            
            // Take screenshot
            utils.ScreenshotUtil.takeScreenshot(driver, device + "_performance");
        }
        
        logInfo("Performance test across devices completed");
    }
    
    private void checkPerformanceIssues() {
        logInfo("Checking for common performance issues...");
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        try {
            // Check for unoptimized images
            boolean hasLargeImages = (Boolean) js.executeScript(
                "var images = document.getElementsByTagName('img');" +
                "var largeImages = 0;" +
                "for (var i = 0; i < images.length; i++) {" +
                "   var img = images[i];" +
                "   if (img.naturalWidth > 0 && img.naturalHeight > 0) {" +
                "       if (img.naturalWidth > 1920 || img.naturalHeight > 1080) {" +
                "           largeImages++;" +
                "           console.log('Large image: ' + img.src + ' - ' + img.naturalWidth + 'x' + img.naturalHeight);" +
                "       }" +
                "   }" +
                "}" +
                "return largeImages > 0;"
            );
            
            if (hasLargeImages) {
                logWarning("PERFORMANCE ISSUE: Large unoptimized images detected");
            } else {
                logInfo("✓ Images are properly sized");
            }
            
        } catch (Exception e) {
            logWarning("Could not check image sizes: " + e.getMessage());
        }
        
        try {
            // Check for render-blocking resources
            boolean hasRenderBlocking = (Boolean) js.executeScript(
                "var blockingResources = 0;" +
                "var links = document.getElementsByTagName('link');" +
                "for (var i = 0; i < links.length; i++) {" +
                "   if (links[i].rel === 'stylesheet' && !links[i].media) {" +
                "       blockingResources++;" +
                "   }" +
                "}" +
                "return blockingResources > 0;"
            );
            
            if (hasRenderBlocking) {
                logWarning("PERFORMANCE ISSUE: Render-blocking resources detected");
            } else {
                logInfo("✓ No render-blocking resources found");
            }
            
        } catch (Exception e) {
            logWarning("Could not check render-blocking resources: " + e.getMessage());
        }
        
        try {
            // Check for excessive DOM elements
            Long domElementCount = (Long) js.executeScript(
                "return document.getElementsByTagName('*').length;"
            );
            
            logInfo("DOM Element Count: " + domElementCount);
            
            if (domElementCount > 1500) {
                logWarning("PERFORMANCE ISSUE: Large DOM size detected (" + domElementCount + " elements)");
            } else {
                logInfo("✓ DOM size within acceptable range");
            }
            
        } catch (Exception e) {
            logWarning("Could not count DOM elements: " + e.getMessage());
        }
        
        try {
            // Check for console errors
            Object consoleErrors = js.executeScript(
                "return window.console && console.error ? console.error.length : 0;"
            );
            
            if (consoleErrors instanceof Long && (Long) consoleErrors > 0) {
                logWarning("PERFORMANCE ISSUE: " + consoleErrors + " console errors detected");
            }
            
        } catch (Exception e) {
            // Ignore - console might not be accessible
        }
    }
    
    /**
     * Get performance metrics using Navigation Timing API
     */
    private Map<String, Object> getPerformanceMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        try {
            // Basic timing metrics
            metrics.put("navigationStart", js.executeScript(
                "return window.performance.timing.navigationStart;"
            ));
            
            metrics.put("loadEventEnd", js.executeScript(
                "return window.performance.timing.loadEventEnd - window.performance.timing.navigationStart;"
            ));
            
            metrics.put("domContentLoadedEventEnd", js.executeScript(
                "return window.performance.timing.domContentLoadedEventEnd - window.performance.timing.navigationStart;"
            ));
            
            // Get memory usage if available (Chrome only)
            try {
                Object memory = js.executeScript(
                    "return window.performance.memory ? window.performance.memory.usedJSHeapSize : 'Not available';"
                );
                metrics.put("memoryUsed", memory);
            } catch (Exception e) {
                metrics.put("memoryUsed", "Not available");
            }
            
        } catch (Exception e) {
            logWarning("Could not get performance metrics: " + e.getMessage());
            metrics.put("error", e.getMessage());
        }
        
        return metrics;
    }
    
    /**
     * Get resource load times
     */
    private Map<String, Long> getResourceLoadTimes() {
        Map<String, Long> resourceTimes = new HashMap<>();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        try {
            // Get resource timing for different types
            String[] resourceTypes = {"script", "css", "image", "font"};
            
            for (String type : resourceTypes) {
                Object result = js.executeScript(
                    "var resources = performance.getEntriesByType('resource');" +
                    "var totalTime = 0;" +
                    "var count = 0;" +
                    "for (var i = 0; i < resources.length; i++) {" +
                    "   if (resources[i].initiatorType === '" + type + "') {" +
                    "       totalTime += resources[i].duration;" +
                    "       count++;" +
                    "   }" +
                    "}" +
                    "return count > 0 ? Math.round(totalTime/count) : 0;"
                );
                
                if (result instanceof Number) {
                    resourceTimes.put(type, ((Number) result).longValue());
                }
            }
            
        } catch (Exception e) {
            logWarning("Could not get resource timing: " + e.getMessage());
        }
        
        return resourceTimes;
    }
    
    /**
     * Get image count
     */
    private int getImageCount() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Object result = js.executeScript(
                "return document.getElementsByTagName('img').length;"
            );
            return result instanceof Number ? ((Number) result).intValue() : 0;
        } catch (Exception e) {
            logWarning("Could not count images: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Get total image size estimate
     */
    private long getTotalImageSize() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Object result = js.executeScript(
                "var images = document.getElementsByTagName('img');" +
                "var totalSize = 0;" +
                "for (var i = 0; i < images.length; i++) {" +
                "   var img = images[i];" +
                "   if (img.naturalWidth > 0 && img.naturalHeight > 0) {" +
                "       totalSize += img.naturalWidth * img.naturalHeight;" +
                "   }" +
                "}" +
                "return totalSize;"
            );
            return result instanceof Number ? ((Number) result).longValue() : 0;
        } catch (Exception e) {
            logWarning("Could not calculate image size: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Get total requests count
     */
    private int getTotalRequests() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Object result = js.executeScript(
                "return performance.getEntriesByType('resource').length;"
            );
            return result instanceof Number ? ((Number) result).intValue() : 0;
        } catch (Exception e) {
            logWarning("Could not count requests: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Get page load time using Navigation Timing API
     */
    private double getPageLoadTime() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Object result = js.executeScript(
                "if (performance.timing.loadEventEnd && performance.timing.navigationStart) {" +
                "   return (performance.timing.loadEventEnd - performance.timing.navigationStart) / 1000;" +
                "} else {" +
                "   return 0;" +
                "}"
            );
            return result instanceof Number ? ((Number) result).doubleValue() : 0;
        } catch (Exception e) {
            logWarning("Could not get page load time from Navigation Timing API: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Get page size in bytes (estimate)
     */
    @SuppressWarnings("unused")
	private long getPageSize() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Object result = js.executeScript(
                "return document.documentElement.innerHTML.length;"
            );
            return result instanceof Number ? ((Number) result).longValue() : 0;
        } catch (Exception e) {
            logWarning("Could not estimate page size: " + e.getMessage());
            return 0;
        }
    }
}