package com.philippinestradedata.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class HomePage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Page Elements
    @FindBy(tagName = "h1")
    private WebElement mainHeading;
    
    @FindBy(xpath = "//button[contains(text(), 'Free Trial')]")
    private WebElement freeTrialButton;
    
    @FindBy(tagName = "nav")
    private WebElement navigationMenu;
    
    @FindBy(tagName = "footer")
    private WebElement footer;
    
    @FindBy(xpath = "//div[contains(@class, 'statistic')]")
    private List<WebElement> statistics;
    
    @FindBy(xpath = "//div[contains(@class, 'chart') or contains(@class, 'graph')]")
    private List<WebElement> charts;
    
    @FindBy(tagName = "img")
    private List<WebElement> images;
    
    @FindBy(xpath = "//a[@href]")
    private List<WebElement> allLinks;
    
    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }
    
    // Page Actions
    public void navigateToHome() {
        driver.get("https://staging.philippinestradedata.com");
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    public boolean isMainHeadingDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(mainHeading)).isDisplayed();
    }
    
    public String getMainHeadingText() {
        return mainHeading.getText();
    }
    
    public boolean isFreeTrialButtonClickable() {
        return wait.until(ExpectedConditions.elementToBeClickable(freeTrialButton)).isEnabled();
    }
    
    public void clickFreeTrialButton() {
        freeTrialButton.click();
    }
    
    public boolean isNavigationMenuDisplayed() {
        try {
            return navigationMenu.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isFooterDisplayed() {
        return footer.isDisplayed();
    }
    
    public int getStatisticsCount() {
        return statistics.size();
    }
    
    public int getChartsCount() {
        return charts.size();
    }
    
    public int getImagesCount() {
        return images.size();
    }
    
    public List<WebElement> getAllImages() {
        return images;
    }
    
    public List<WebElement> getAllLinks() {
        return allLinks;
    }
    
    public boolean isElementVisible(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isElementClickable(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getCurrentViewportSize() {
        return driver.manage().window().getSize().toString();
    }
}