package com.test.weather.simulator.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class CommonUtility {

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
			System.out.println(path);
			path = path + "/application.properties";

			prop.load(new FileInputStream(path));

			number_of_location = Integer.parseInt(prop.getProperty("NUMBER_OF_LOCATION_TO_POPULATE"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
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

	public String getDateTime(String location) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		//sdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
		Date date = new Date();
		return sdf.format(date);
	}

	public String getCurrentMonth() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		Long millis = 0L;
		//sdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
		Date date = new Date();
		try {
			millis = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(sdf.format(date)).getTime();
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		return getMonth(millis);
	}

	public float convertFahToCenti(float temp){
		
		float tempInCenti = 0.0f;
		tempInCenti = ((temp - 32)*5)/9;
		return tempInCenti;
		
	}
}
