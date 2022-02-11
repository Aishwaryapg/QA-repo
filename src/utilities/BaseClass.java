package utilities;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Month;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

public class BaseClass {
	ReadConfig rc = new ReadConfig();
	WebDriver driver;
	WebDriverWait wait;
	static int targetDay = 0, targetMonth = 0, targetYear = 0;

	static int currentDay = 0, currentMonth = 0, currentYear = 0;

	static int jumpMonthsBy = 0;

	static boolean increment = true;
/*  
 * Launch application, ClearTrip
 *
 */
	@BeforeClass
	public void launchApplication() {
		System.setProperty("webdriver.chrome.driver", "src\\resources\\chromedriver.exe");
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setJavascriptEnabled(true);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox", "--disable-dev-shm-usage",
				"--disable-blink-features=AutomationControlled");

		//options.setExperimentalOption("useAutomationExtension", false);

		driver = new ChromeDriver(options);
		driver.manage().window().maximize();

		driver.get("https://www.cleartrip.com/");
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

	}
/*
 * Select a value from a list by filtering text
 */
	public void selectValueFromList(String locator, String text) throws InterruptedException {
		// TODO Auto-generated method stub
		String str = rc.getLocatorValue(locator);

		String locatorValue = str.split("__")[1];
		List<WebElement> elementList = driver.findElements(By.xpath(locatorValue));
		//System.out.println(elementList);

		for (int i = 0; i < elementList.size(); i++) {
			System.out.println(elementList.get(i).getText());
			System.out.println(text);
			if ((elementList.get(i).getText()).contains(text)) {
				elementList.get(i).click();
				break;
			}
		}
	}

/*
 * Select a value from DropDown
 */

	public void selectAdult( String locator,String value) {
		// TODO Auto-generated method stub
		try {
		WebElement element = getWebElement(locator);
		Select select = new Select(element);
		select.selectByValue(value);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/*
 * Click an element
 */
	public void clickElement(String locator) {
		// TODO Auto-generated method stub
		try {
			WebElement element = getWebElement(locator);
			element.click();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/*
 * Extracting WebElement from locator value stored in property file
 */
	public WebElement getWebElement(String locator) throws Exception {
		// TODO Auto-generated method stub
		String str = rc.getLocatorValue(locator);
		String locatorType = str.split("__")[0];
		String locatorValue = str.split("__")[1];
		if (locatorType.equalsIgnoreCase("xpath")) {
			return (driver.findElement(By.xpath(locatorValue)));
		} else if (locatorType.equalsIgnoreCase("css")) {
			return (driver.findElement(By.cssSelector(locatorValue)));
		} else if (locatorType.equalsIgnoreCase("id")) {
			return (driver.findElement(By.id(locatorValue)));
		} else if (locatorType.equalsIgnoreCase("name")) {
			return (driver.findElement(By.name(locatorValue)));
		} else if (locatorType.equalsIgnoreCase("class")) {
			return (driver.findElement(By.name(locatorValue)));
		} else if (locatorType.equalsIgnoreCase("linktext")) {
			return (driver.findElement(By.linkText(locatorValue)));
		} else
			throw new Exception("Unknown locator");
	}
/*
 * Enter a value in textbox/input
 */
	public void enterText(String locator, String text) {
		// TODO Auto-generated method stub
		try {
			WebElement element = getWebElement(locator);
			element.sendKeys(text);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/*
 * After test actions
 */
	@AfterTest
	public void teardown() {
		driver.quit();
		System.out.println("------Flight search Done-----");

	}
	/*
	 * To check an element present or not
	 */
	public boolean isElementPresent(String locator){
       
        	String str = rc.getLocatorValue(locator);

    		String locatorValue = str.split("__")[1];
    		List<WebElement> elements = driver.findElements(By.xpath(locatorValue));
    		if(elements.size()==0)
    		{
    			return false;
    		}
    		else 
    			{
    				return true;
    			}
                	
    }
/*
 * Select date from datepicker
 */
	public void selectDate(String dateToSet,String iconlocator,String datepickerlocator) throws InterruptedException {
		// TODO Auto-generated method stub
		try{
			
			if(isElementPresent(iconlocator))
			{
				
				scrollDown(datepickerlocator);
				getCurrentDateMonthAndYear();
				System.out.println(currentDay + "   " + currentMonth + "   " + currentYear);

				
				GetTargetDateMonthAndYear(dateToSet);
				System.out.println(targetDay + "   " + targetMonth + "   " + targetYear);

				
				CalculateHowManyMonthsToJump();
				//System.out.println(jumpMonthsBy);
				//System.out.println(increment);

				for (int i = 0; i < (jumpMonthsBy); i++) {

					if (increment) {

						
						driver.findElement(By
								.xpath("(//*[name()='svg']//*[name()='path' and @d='M5 12.875h10.675l-4.9 4.9L12 19l7-7-7-7-1.225 1.225 4.9 4.9H5z'])[1]"))
								.click();

					}

					Thread.sleep(1000);

				}
				String strMonth = (Month.of(targetMonth).name());
				String strMonthFormatted = strMonth.charAt(0) + (strMonth.substring(1, 3)).toLowerCase();
				String day = strMonthFormatted + " " + targetDay;
				System.out.println(day);
				driver.findElement(By.xpath("//div[contains(@aria-label, '" + day + "')]")).click();
				}
			
		else
		{

			
			JavascriptExecutor js = (JavascriptExecutor) driver; 
		     js.executeScript("window.scrollBy(0,250)");
		
		getCurrentDateMonthAndYear();
		System.out.println(currentDay + "   " + currentMonth + "   " + currentYear);

		
		GetTargetDateMonthAndYear(dateToSet);
		System.out.println(targetDay + "   " + targetMonth + "   " + targetYear);

		
		CalculateHowManyMonthsToJump();
		//System.out.println(jumpMonthsBy);
		//System.out.println(increment);

		for (int i = 0; i < (jumpMonthsBy); i++) {

			if (increment) {

				
				driver.findElement(By
						.xpath("(//*[name()='svg']//*[name()='path' and @d='M5 12.875h10.675l-4.9 4.9L12 19l7-7-7-7-1.225 1.225 4.9 4.9H5z'])[1]"))
						.click();

			}

			Thread.sleep(1000);

		}
		String strMonth = (Month.of(targetMonth).name());
		String strMonthFormatted = strMonth.charAt(0) + (strMonth.substring(1, 3)).toLowerCase();
		String day = strMonthFormatted + " " + targetDay;
		System.out.println(day);
		driver.findElement(By.xpath("//div[contains(@aria-label, '" + day + "')]")).click();
		}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
/*
 * To get Current Date,Month and Year
 */
	public static void getCurrentDateMonthAndYear() {

		Calendar cal = Calendar.getInstance();

		currentDay = cal.get(Calendar.DAY_OF_MONTH);
		currentMonth = cal.get(Calendar.MONTH) + 1;
		currentYear = cal.get(Calendar.YEAR);

	}
/*
 * To get the target date to be selected
 */
	public static void GetTargetDateMonthAndYear(String dateString) {
		String day = dateString.split("-")[0];
		targetDay = Integer.parseInt(day);

		String month = dateString.split("-")[1];
		targetMonth = Integer.parseInt(month);

		String year = dateString.split("-")[2];
		targetYear = Integer.parseInt(year);
	}
/*
 * Calculate difference between current date and target date
 */
	public static void CalculateHowManyMonthsToJump() {

		if ((targetMonth - currentMonth) > 0) {

			jumpMonthsBy = (targetMonth - currentMonth);
		} else {

			jumpMonthsBy = (currentMonth - targetMonth);
			increment = false;
		}

	}
/*
 * Select a radio button
 */
	public void selectRadioButton(String locator, String text) {
		// TODO Auto-generated method stub
		String str = rc.getLocatorValue(locator);

		String locatorValue = str.split("__")[1];
		List<WebElement> monthYear = driver.findElements(By.xpath(locatorValue));
		for (WebElement element : monthYear) {
			if (element.getText().equals(text))
				element.click();
		}
	}
/*
 * to wait for specific time
 */
	public void userWaitsFor(int i) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread.sleep(i * 1000);
	}
/*
 * to wait until an element to be visible
 */
	public void userWaitsForElementtobeVisisble(String locator) {
		// TODO Auto-generated method stub
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			WebElement element = getWebElement(locator);
			wait.until(ExpectedConditions.visibilityOf(element));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
/*
 * to press Enter key
 */
	public void pressEnter(String locator) {
		// TODO Auto-generated method stub
		try {
			WebElement element = getWebElement(locator);
			element.sendKeys(Keys.ENTER);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	
/*
 * Scroll down in a page
 */
	public void scrollDown(String locator) {
		// TODO Auto-generated method stub
		try {
			WebElement element = getWebElement(locator);
			//Thread.sleep(1000);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// js.executeScript("arguments[0].scrollIntoView();", element);
			element.sendKeys(Keys.PAGE_DOWN);
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	/*
	 * to check whether an element is enabled
	 */
	
	public void waitForElementtobeClickable(String locator) {
		// TODO Auto-generated method stub
		try {
			WebElement element = getWebElement(locator);
			wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
/*
 * Select a flight based on price and duration
 */
	public void selectFlight(String price, String duration, String string3) {
		// TODO Auto-generated method stub
		driver.findElement(By.xpath(
				"//div/p[text()='" + duration + "']//following::p[3][text()='" + price + "']//following::button"))
				.click();
	}
/*
 * Validate the text of an element
 */
	public void verifyText(String locator, String expectedText) {
		// TODO Auto-generated method stub
		try {
			WebElement element = getWebElement(locator);
			Assert.assertEquals(element.getText(), expectedText);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
/*
 * Switch to child window
 */

	public void switchToWindow() {
		// TODO Auto-generated method stub
		String mainWindowHandle = driver.getWindowHandle();
		for (String childWindowHandle : driver.getWindowHandles()) {
			
			if (!childWindowHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(childWindowHandle);
			}
		}

	}
	public void takesScreenshot() throws IOException {
		// TODO Auto-generated method stub
		TakesScreenshot scrShot =((TakesScreenshot)driver);		
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);		
		File DestFile=new File("screenshots/Review.png");
		
		FileUtils.copyFile(SrcFile, DestFile);
	}
}