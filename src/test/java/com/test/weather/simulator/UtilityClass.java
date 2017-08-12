package com.test.weather.simulator;

import com.test.weather.simulator.utility.CommonUtility;

import junit.framework.TestCase;

public class UtilityClass extends TestCase{

	CommonUtility utilObj = new CommonUtility();

	public void testMonthName () {

		long epochTimeVal = 1432209600L;
		try{
			assertEquals("May", utilObj.getMonth(epochTimeVal));
		}catch(Exception e){

		}
		
	}
	
	public void testGetDateTime (){
		
		String monthName = "January";
		int year = 2018;
		
		try {
			assertEquals("2018-01-01T00:00:00Z", utilObj.getDateTime( monthName, year));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}