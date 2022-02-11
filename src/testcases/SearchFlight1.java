/*
 * Action - Flight Search
 * Parameters - Origin, Destination, Date of Departure, Count of Adults
 * 
 */
package testcases;
import java.io.IOException;

import org.testng.annotations.Test;

import utilities.BaseClass;

public class SearchFlight1 {
@Test
	public SearchFlight1() throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		BaseClass ob=new BaseClass();

		ob.launchApplication();
		//navigate to flights section
		ob.clickElement("flights");
		ob.userWaitsFor(6);
		//Select Origin and Destination city
		ob.enterText("from","BLR");	
		ob.userWaitsFor(2);
		ob.selectValueFromList("fromList", "Bangalore");
		ob.userWaitsFor(2);
		ob.enterText("to","MAA");
		ob.userWaitsFor(2);
		ob.selectValueFromList("toList", "Chennai");
		ob.userWaitsFor(1);
		//select Departure date
	    
		ob.clickElement("datepicker");
    	
    	ob.selectDate("22-06-2022","travelSafeIcon","datepicker");
    	ob.userWaitsFor(3);
		//select number of adults
    	ob.selectAdult("adultsDropdown", "4");
		ob.clickElement("searchFlightButton");
		ob.userWaitsFor(6);
		//select flight for the specific price and duration
		ob.selectFlight("₹6,412","1h 0m","bookFlight");
		ob.userWaitsFor(6);
		ob.switchToWindow();
		//verify itinerary
		ob.verifyText("review","Review your itinerary");
		ob.takesScreenshot();
		ob.teardown();
	}

}
