import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;

import java.util.*;

/**
 * 
 */

/**
 * @author westmount
 *
 */
public class MyImmigrationStatus {
	static String linkOnlineTool = "https://services3.cic.gc.ca/ecas/security.do?lang=en&_ga=2.36017702.783948421.1515458672-1945924788.1512770655";
	
	static String strAppNumber = "";
	static String strSurname = "";
	static String strBirthMonth = "";
	static String strBirthDay = "";
	static String strBirthYear = "";
	static String strBirthCountry = "China";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	    FirefoxOptions options = new FirefoxOptions();
	    options.setLogLevel(FirefoxDriverLogLevel.FATAL);
	    //options.setHeadless(true);
	    
		WebDriver driver = new FirefoxDriver(options);
		//WebDriver driver = new HtmlUnitDriver();

        // And now use this to visit Google
        driver.get(linkOnlineTool);
        
        try {
        	WebElement termAndCondition = driver.findElement(By.xpath("//input[@id='agree']"));
            System.out.println(termAndCondition.getText());
            termAndCondition.click();
            
            WebElement continueButton = driver.findElement(By.xpath("//input[@value='Continue']"));
            continueButton.click();
        }catch(NoSuchElementException e) {
        	System.out.println("Can't find the Term and Condition checkbox.");
        }

        //come into the next page, need to fill in some data
        //1. fill the identification type
        Select identificationType = new Select(driver.findElement(By.id("idTypeLabel")));
        identificationType.selectByVisibleText("Application Number / Case Number");
        
        //2. input the identification number
        WebElement identificationNumber = driver.findElement(By.id("idNumberLabel"));
        identificationNumber.sendKeys(strAppNumber);
        
        //3. input surname
        WebElement surname = driver.findElement(By.id("surnameLabel"));
        surname.sendKeys(strSurname);
        
        //4.input birthday
        //WebElement dateBox = driver.findElement(By.xpath("//input[@name='dateOfBirth']"));
        //dateBox.click();
        
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.TAB).perform();
        actions.sendKeys(Keys.TAB).perform();
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        actions.sendKeys(strBirthYear).perform();
        actions.sendKeys(Keys.TAB).perform();

        try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        actions.sendKeys(strBirthMonth).perform();
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        actions.sendKeys(strBirthDay).perform();
        
        
        //5.input place of birth
        Select country = new Select(driver.findElement(By.id("cobLabel")));
        country.selectByVisibleText(strBirthCountry);        
        
        try {
            WebElement continueButton = driver.findElement(By.xpath("//input[@value='Continue']"));
            continueButton.click();
        }catch(NoSuchElementException e) {
        	System.out.println("Can't find the Continue Button in Online service page.");
        }        
        
        //come into the status page
        WebElement statusForm = driver.findElement(By.className("align-center"));
        String strStatus = new String(statusForm.getText());
        
        //click the status link
        //<a href="viewcasehistory.do?id=5266343&amp;type=prCases&amp;source=db&amp;app=&amp;lang=en" class="ui-link">In Process</a>
        WebElement linkStatus = statusForm.findElement(By.className("ui-link"));
        linkStatus.click();
        
		//get the status details
        /* <ol>	
		<li class="margin-bottom-medium">We received your application for permanent residence  on March 1, 2017.</li>	
		<li class="margin-bottom-medium">We sent you correspondence acknowledging receipt of your application(s) on March 28, 2017.</li>
		<li class="margin-bottom-medium">We started processing your application on March 29, 2017.</li>
		<li class="margin-bottom-medium">Medical results have been received.</li>
		</ol>*/
        //WebElement details = driver.findElement(By.className("margin-bottom-large"));

        List<WebElement> histories = driver.findElements(By.xpath("//li[@class='margin-bottom-medium']"));
        String strDetails = "";
        for(WebElement item:histories) {
        	strDetails += "* "+item.getText()+"\n";
        }
        
        driver.quit();

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(strStatus);
        System.out.println("-------------------");
        System.out.println(strDetails);
        
	}

}
