/*
 * Action - Flight Search
 * Parameters - Origin, Destination, Date of Departure, Count of Adults
 * 
 */
package testcases;
import org.testng.annotations.Test;

import utilities.BaseClass;

public class SearchFlight extends BaseClass{
	
@Test
	public void search_flight() throws Throwable {
		// TODO Auto-generated method stub
		

		
		//navigate to flights section
		clickElement("flights");
		userWaitsFor(6);
		//Select Origin and Destination city
		enterText("from","BLR");	
		userWaitsFor(2);
		selectValueFromList("fromList", "Bangalore");
		userWaitsFor(2);
		enterText("to","MAA");
		userWaitsFor(2);
		selectValueFromList("toList", "Chennai");
		userWaitsFor(1);
		//select Departure date
	    
		clickElement("datepicker");
		//userWaitsFor(3);
    	selectDate("22-06-2022","travelSafeIcon","datepicker","nextButton");
    	userWaitsFor(3);
		//select number of adults
    	selectAdult("adultsDropdown", "4");
		clickElement("searchFlightButton");
		waitForElementtobeClickable("currency");
		//select flight for the specific price and duration
		clickElement("currency");
		selectValueFromList("currenciesList", "India");
		userWaitsFor(3);
		clickElement("bookFlight");
		userWaitsFor(6);
		switchToWindow();
		//verify itinerary
		verifyText("review","Review your itinerary");
		takesScreenshot();
		
	}

}
