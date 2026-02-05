package com.philippinestradedata.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import java.util.HashMap;
import java.util.Map;

public class PerformanceMetrics {
    
    private final JavascriptExecutor js;
    
    // Constructor - store only JavascriptExecutor
    public PerformanceMetrics(WebDriver driver) {
        this.js = (JavascriptExecutor) driver;
    }
    
    public Map<String, Object> getPerformanceMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        try {
            // Navigation Timing API metrics
            Object loadEventEnd = js.executeScript(
                "if (window.performance && window.performance.timing) {" +
                "   return window.performance.timing.loadEventEnd - window.performance.timing.navigationStart;" +
                "} else {" +
                "   return 'Performance API not available';" +
                "}"
            );
            metrics.put("loadEventEnd", loadEventEnd);
            
            Object domContentLoaded = js.executeScript(
                "if (window.performance && window.performance.timing) {" +
                "   return window.performance.timing.domContentLoadedEventEnd - window.performance.timing.navigationStart;" +
                "} else {" +
                "   return 'Performance API not available';" +
                "}"
            );
            metrics.put("domContentLoadedEventEnd", domContentLoaded);
            
            Object responseEnd = js.executeScript(
                "if (window.performance && window.performance.timing) {" +
                "   return window.performance.timing.responseEnd - window.performance.timing.navigationStart;" +
                "} else {" +
                "   return 'Performance API not available';" +
                "}"
            );
            metrics.put("responseEnd", responseEnd);
            
            // Get all performance entries count
            Object totalEntries = js.executeScript(
                "return performance && performance.getEntries ? performance.getEntries().length : 0;"
            );
            metrics.put("totalEntries", totalEntries);
            
        } catch (Exception e) {
            System.err.println("Error getting performance metrics: " + e.getMessage());
            metrics.put("error", e.getMessage());
        }
        
        return metrics;
    }
    
    public Map<String, Long> getResourceLoadTimes() {
        Map<String, Long> resourceTimes = new HashMap<>();
        
        try {
            String[] resourceTypes = {"script", "css", "image", "font", "xmlhttprequest"};
            
            for (String type : resourceTypes) {
                Object result = js.executeScript(
                    "if (!performance || !performance.getEntriesByType) return 0;" +
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
            System.err.println("Error getting resource timing: " + e.getMessage());
        }
        
        return resourceTimes;
    }
    
    public int getImageCount() {
        try {
            Object result = js.executeScript(
                "return document.getElementsByTagName('img').length;"
            );
            return result instanceof Number ? ((Number) result).intValue() : 0;
        } catch (Exception e) {
            System.err.println("Error counting images: " + e.getMessage());
            return 0;
        }
    }
    
    public long getTotalImageSize() {
        try {
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
            System.err.println("Error calculating image size: " + e.getMessage());
            return 0;
        }
    }
    
    public int getTotalRequests() {
        try {
            Object result = js.executeScript(
                "return performance && performance.getEntriesByType ? performance.getEntriesByType('resource').length : 0;"
            );
            return result instanceof Number ? ((Number) result).intValue() : 0;
        } catch (Exception e) {
            System.err.println("Error counting requests: " + e.getMessage());
            return 0;
        }
    }
    
    public double getPageLoadTime() {
        try {
            Object result = js.executeScript(
                "if (performance && performance.timing && performance.timing.loadEventEnd && performance.timing.navigationStart) {" +
                "   return (performance.timing.loadEventEnd - performance.timing.navigationStart) / 1000;" +
                "} else {" +
                "   return 0;" +
                "}"
            );
            return result instanceof Number ? ((Number) result).doubleValue() : 0;
        } catch (Exception e) {
            System.err.println("Error getting page load time: " + e.getMessage());
            return 0;
        }
    }
    
    public boolean hasLargeImages() {
        try {
            Object result = js.executeScript(
                "var images = document.getElementsByTagName('img');" +
                "for (var i = 0; i < images.length; i++) {" +
                "   var img = images[i];" +
                "   if (img.naturalWidth > 1920 || img.naturalHeight > 1080) {" +
                "       return true;" +
                "   }" +
                "}" +
                "return false;"
            );
            return result instanceof Boolean ? (Boolean) result : false;
        } catch (Exception e) {
            System.err.println("Error checking image sizes: " + e.getMessage());
            return false;
        }
    }
    
    public boolean hasRenderBlockingResources() {
        try {
            Object result = js.executeScript(
                "var links = document.getElementsByTagName('link');" +
                "for (var i = 0; i < links.length; i++) {" +
                "   if (links[i].rel === 'stylesheet' && !links[i].media) {" +
                "       return true;" +
                "   }" +
                "}" +
                "return false;"
            );
            return result instanceof Boolean ? (Boolean) result : false;
        } catch (Exception e) {
            System.err.println("Error checking render-blocking resources: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get current page title
     */
    public String getPageTitle() {
        try {
            Object result = js.executeScript("return document.title;");
            return result instanceof String ? (String) result : "";
        } catch (Exception e) {
            System.err.println("Error getting page title: " + e.getMessage());
            return "";
        }
    }
    
    /**
     * Get current URL
     */
    public String getCurrentUrl() {
        try {
            Object result = js.executeScript("return window.location.href;");
            return result instanceof String ? (String) result : "";
        } catch (Exception e) {
            System.err.println("Error getting current URL: " + e.getMessage());
            return "";
        }
    }
    
    /**
     * Get DOM element count
     */
    public int getDomElementCount() {
        try {
            Object result = js.executeScript("return document.getElementsByTagName('*').length;");
            return result instanceof Number ? ((Number) result).intValue() : 0;
        } catch (Exception e) {
            System.err.println("Error counting DOM elements: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Check if page is fully loaded
     */
    public boolean isPageFullyLoaded() {
        try {
            Object result = js.executeScript(
                "return document.readyState === 'complete';"
            );
            return result instanceof Boolean ? (Boolean) result : false;
        } catch (Exception e) {
            System.err.println("Error checking page load state: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get memory usage (Chrome only)
     */
    public Map<String, Object> getMemoryUsage() {
        Map<String, Object> memoryInfo = new HashMap<>();
        
        try {
            Object usedJSHeapSize = js.executeScript(
                "return window.performance && performance.memory ? performance.memory.usedJSHeapSize : 'Not available';"
            );
            memoryInfo.put("usedJSHeapSize", usedJSHeapSize);
            
            Object totalJSHeapSize = js.executeScript(
                "return window.performance && performance.memory ? performance.memory.totalJSHeapSize : 'Not available';"
            );
            memoryInfo.put("totalJSHeapSize", totalJSHeapSize);
            
            Object jsHeapSizeLimit = js.executeScript(
                "return window.performance && performance.memory ? performance.memory.jsHeapSizeLimit : 'Not available';"
            );
            memoryInfo.put("jsHeapSizeLimit", jsHeapSizeLimit);
            
        } catch (Exception e) {
            memoryInfo.put("error", "Memory API not available: " + e.getMessage());
        }
        
        return memoryInfo;
    }
    
    /**
     * Execute custom JavaScript and return result
     */
    public Object executeCustomScript(String script) {
        try {
            return js.executeScript(script);
        } catch (Exception e) {
            System.err.println("Error executing custom script: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all performance entries as JSON string
     */
    public String getAllPerformanceEntries() {
        try {
            Object result = js.executeScript(
                "if (!performance || !performance.getEntries) return '[]';" +
                "var entries = performance.getEntries();" +
                "var simplified = [];" +
                "for (var i = 0; i < entries.length; i++) {" +
                "   var entry = entries[i];" +
                "   simplified.push({" +
                "       name: entry.name," +
                "       type: entry.initiatorType || entry.entryType," +
                "       duration: entry.duration," +
                "       startTime: entry.startTime" +
                "   });" +
                "}" +
                "return JSON.stringify(simplified);"
            );
            return result instanceof String ? (String) result : "[]";
        } catch (Exception e) {
            System.err.println("Error getting performance entries: " + e.getMessage());
            return "[]";
        }
    }
}