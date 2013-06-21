package org.openmrs.reference.page;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * A superclass for "real" pages. Has lots of handy methods for accessing
 * elements, clicking, filling fields. etc.
 */
public abstract class AbstractBasePage implements Page {
    protected TestProperties properties = new TestProperties();
    protected WebDriver driver;
    private String serverURL;

    public AbstractBasePage(WebDriver driver) {
        this.driver = driver;
        serverURL = properties.getWebAppUrl();
    }

    @Override
    public void gotoPage(String address) {
        driver.get(serverURL + address);
    }
    
    // Convenience method. TODO Should this be named findElement instead?
    @Override
    public WebElement getElement(By by) {
    	return driver.findElement(by);
    }

    // Convenience method. TODO Should this be named findElementById instead?
    @Override
    public WebElement getElementById(String id) {
    	return getElement(By.id(id));
    }
    
    @Override
    public String getText(By by) {
        return getElement(by).getText();
    }

    @Override
    public void setTextToField(String textFieldId, String text) {
        setText(getElement(By.id(textFieldId)), text);
    }

    @Override
    public void setTextToFieldInsideSpan(String spanId, String text) {
        setText(findTextFieldInsideSpan(spanId), text);
    }

    private void setText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
        element.sendKeys(Keys.RETURN);
    }

    @Override
    public void clickOn(By by) {
        getElement(by).click();
    }

    @Override
    public void hoverOn(By by) {
        Actions builder = new Actions(driver);
        Actions hover = builder.moveToElement(driver.findElement(by));
        hover.perform();
    }

    private WebElement findTextFieldInsideSpan(String spanId) {
        return getElementById(spanId).findElement(By.tagName("input"));
    }

	@Override
    public String title() {
	    return getText(By.tagName("title"));
    }
	
	@Override
	public String urlPath() {
	    try {
	        return new URL(driver.getCurrentUrl()).getPath();
        }
        catch (MalformedURLException e) {
	        return null;
        }
    }
	
	@Override
    public abstract String expectedUrlPath();
}
