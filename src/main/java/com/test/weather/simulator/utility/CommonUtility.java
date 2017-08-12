package com.test.weather.simulator.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;


public class CommonUtility {


	final static Logger logger = Logger.getLogger(CommonUtility.class);
	Random rand = new Random();


	public String getMonth(long epochTimeVal){


		long val = epochTimeVal * 1000;
		Date date = new Date(val);
		DateFormat format = new SimpleDateFormat("MM");
		String formatted = format.format(date);
		String monthString = null;

		switch(Integer.parseInt(formatted)){
		case 1:  monthString = "January";
		break;
		case 2:  monthString = "February";
		break;
		case 3:  monthString = "March";
		break;
		case 4:  monthString = "April";
		break;
		case 5:  monthString = "May";
		break;
		case 6:  monthString = "June";
		break;
		case 7:  monthString = "July";
		break;
		case 8:  monthString = "August";
		break;
		case 9:  monthString = "September";
		break;
		case 10: monthString = "October";
		break;
		case 11: monthString = "November";
		break;
		case 12: monthString = "December";
		break; 
		}

		return monthString;

	}

	public int loadSettings() throws Exception{

		Properties prop = new Properties();
		InputStream input = null;
		int number_of_location = 0;
		try {

			String path = new File("config").getCanonicalPath();
			//System.out.println(path);
			path = path + "/application.properties";

			prop.load(new FileInputStream(path));

			number_of_location = Integer.parseInt(prop.getProperty("NUMBER_OF_LOCATION_TO_POPULATE"));

		} catch (IOException ex) {
			logger.error("Error in reading the file " + ex);
		} finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					logger.error("Unable to close the input stream " + e);
				}
			}
		}

		return number_of_location;

	}

	public float getRandomFloat(float maxFloatValue, float minFloatValue) throws Exception{
		try{

			return  rand.nextFloat() * (maxFloatValue - minFloatValue) + minFloatValue;

		}catch(Exception ex){
			throw new Exception("Could not generate value : ",ex);	
		}

	}

	public String getDateTime(String monthToCheck, int yearToCheck) throws Exception {

		try{

			if(monthToCheck.equalsIgnoreCase(getCurrentMonth())){

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
				//sdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
				Date date = new Date();
				return sdf.format(date);

			}else {
				int month_no = getMonthInInt(monthToCheck);
				GregorianCalendar gc = new GregorianCalendar(yearToCheck, month_no-1, 1);
				java.util.Date monthEndDate = new java.util.Date(gc.getTime().getTime());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
				return format.format(monthEndDate);
			}

		}catch(Exception ex){
			throw new Exception("Could not generate date time  : ",ex);
		}

		//System.out.println(getCurrentMonth());

	}

	private int getMonthInInt(String monthToCheck) {

		int month_in_int= 0;

		if(monthToCheck.equalsIgnoreCase("January")){
			month_in_int = 1;
		}else if(monthToCheck.equalsIgnoreCase("February")){
			month_in_int = 2;
		}else if(monthToCheck.equalsIgnoreCase("March")){
			month_in_int = 3;
		}else if(monthToCheck.equalsIgnoreCase("April")){
			month_in_int = 4;
		}else if(monthToCheck.equalsIgnoreCase("May")){
			month_in_int = 5;
		}else if(monthToCheck.equalsIgnoreCase("June")){
			month_in_int = 6;
		}else if(monthToCheck.equalsIgnoreCase("July")){
			month_in_int = 7;
		}else if(monthToCheck.equalsIgnoreCase("August")){
			month_in_int = 8;
		}else if(monthToCheck.equalsIgnoreCase("September")){
			month_in_int = 9;
		}else if(monthToCheck.equalsIgnoreCase("October")){
			month_in_int = 10;
		}else if(monthToCheck.equalsIgnoreCase("November")){
			month_in_int = 11;
		}else if(monthToCheck.equalsIgnoreCase("December")){
			month_in_int = 12;
		}

		return month_in_int;
	}

	public String getCurrentMonth() throws Exception{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		Long millis = 0L;
		//sdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
		Date date = new Date();
		try {
			millis = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(sdf.format(date)).getTime();
			//System.out.println(millis);
		} catch (ParseException e) {

			throw new Exception("Could not current month  : ",e);

		}

		return getMonth(millis/1000);
	}

	public float convertFahToCenti(float temp){

		float tempInCenti = 0.0f;
		tempInCenti = ((temp - 32)*5)/9;
		return tempInCenti;

	}

	public Map<String, Object> getYearAndMonth() {

		Map<String, Object> monthMap = new HashMap<String, Object>();
		Properties prop = new Properties();
		InputStream input = null;
		int number_of_iteration = 0;
		String month_to_be_observed = null;
		int year_to_be_observed = 0000;
		try {

			String path = new File("config").getCanonicalPath();
			System.out.println(path);
			path = path + "/application.properties";

			prop.load(new FileInputStream(path));

			if(!prop.getProperty("NO_OF_ITERATION").isEmpty()){
				number_of_iteration = Integer.parseInt(prop.getProperty("NO_OF_ITERATION"));
			}

			if(!prop.getProperty("MONTH_TO_BE_OBSERVED").isEmpty()){
				month_to_be_observed = prop.getProperty("MONTH_TO_BE_OBSERVED");
			}

			if(!prop.getProperty("YEAR_TO_BE_OBSERVED").isEmpty()){
				year_to_be_observed = Integer.parseInt(prop.getProperty("YEAR_TO_BE_OBSERVED"));
			}

			logger.info("number of iteration to be done is :- " +  number_of_iteration);
			logger.info("month to be observed is  :- " +  month_to_be_observed);
			logger.info("year to be observed :- " +  year_to_be_observed);

			monthMap.put("iteration", number_of_iteration);
			monthMap.put("month", month_to_be_observed);
			monthMap.put("year", year_to_be_observed);

		} catch (IOException ex) {

			logger.error("Error reading the property file " + ex);

		} finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {

					logger.error("Error closing the input stream " + e);
				}
			}

		}

		return monthMap;


	}

	public int getCurrentYear() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		//int year = 0;
		Date date = new Date();
		return Integer.parseInt(sdf.format(date));

	}
}
