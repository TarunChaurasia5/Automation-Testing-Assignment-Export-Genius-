package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    
    private Properties properties;
    private static ConfigReader instance;
    
    // Constructor
    public ConfigReader() {
        properties = new Properties();
        loadProperties();
    }
    
    // Singleton pattern to ensure single instance
    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }
    
    // Load properties from config file
    private void loadProperties() {
        try {
            String configPath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
            FileInputStream fis = new FileInputStream(configPath);
            properties.load(fis);
            fis.close();
            System.out.println("Properties loaded successfully from: " + configPath);
        } catch (IOException e) {
            System.err.println("Error loading config.properties file: " + e.getMessage());
            // Set default properties if file not found
            setDefaultProperties();
        }
    }
    
    // Set default properties
    private void setDefaultProperties() {
        properties.setProperty("base.url", "https://staging.philippinestradedata.com");
        properties.setProperty("browser", "chrome");
        properties.setProperty("headless", "false");
        properties.setProperty("timeout", "30");
        properties.setProperty("mobile.width", "375");
        properties.setProperty("mobile.height", "812");
        properties.setProperty("tablet.width", "768");
        properties.setProperty("tablet.height", "1024");
        properties.setProperty("desktop.width", "1920");
        properties.setProperty("desktop.height", "1080");
        properties.setProperty("mobile.load.threshold", "2");
        properties.setProperty("desktop.load.threshold", "5");
        System.out.println("Using default properties");
    }
    
    // Get property value
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            System.err.println("Property '" + key + "' not found in config file");
            return "";
        }
        return value.trim();
    }
    
    // Get property with default value
    public String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key);
        return (value != null) ? value.trim() : defaultValue;
    }
    
    // Get integer property
    public int getIntProperty(String key) {
        String value = getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing integer for key '" + key + "': " + value);
            return 0;
        }
    }
    
    // Get integer property with default
    public int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        if (value.isEmpty()) return defaultValue;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    // Get boolean property
    public boolean getBooleanProperty(String key) {
        String value = getProperty(key);
        return Boolean.parseBoolean(value);
    }
    
    // Get boolean property with default
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value.isEmpty()) return defaultValue;
        return Boolean.parseBoolean(value);
    }
    
    // Get mobile load threshold
    public int getMobileLoadThreshold() {
        return getIntProperty("mobile.load.threshold", 2);
    }
    
    // Get desktop load threshold
    public int getDesktopLoadThreshold() {
        return getIntProperty("desktop.load.threshold", 5);
    }
    
    // Get mobile width
    public int getMobileWidth() {
        return getIntProperty("mobile.width", 375);
    }
    
    // Get mobile height
    public int getMobileHeight() {
        return getIntProperty("mobile.height", 812);
    }
    
    // Get tablet width
    public int getTabletWidth() {
        return getIntProperty("tablet.width", 768);
    }
    
    // Get tablet height
    public int getTabletHeight() {
        return getIntProperty("tablet.height", 1024);
    }
    
    // Get desktop width
    public int getDesktopWidth() {
        return getIntProperty("desktop.width", 1920);
    }
    
    // Get desktop height
    public int getDesktopHeight() {
        return getIntProperty("desktop.height", 1080);
    }
    
    // Reload properties
    public void reloadProperties() {
        loadProperties();
    }
    
    // Get all properties
    public Properties getAllProperties() {
        return new Properties(properties);
    }
}