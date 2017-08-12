package com.test.weather.simulator.jobs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.test.weather.simulator.beans.LocationAttributes;
import com.test.weather.simulator.beans.LocationPrintAttr;
import com.test.weather.simulator.beans.MonthlyClimateVariation;
import com.test.weather.simulator.utility.CommonUtility;

public class JobClass {

	final static Logger logger = Logger.getLogger(JobClass.class);
	
	static LocationAttributes locAttr = new LocationAttributes();
	MonthlyClimateVariation monthlyClimateObj = new MonthlyClimateVariation();
	DecimalFormat df = new DecimalFormat("###.##");
	CommonUtility objUtil = new CommonUtility();

	public void populateData(Map<String, LocationAttributes> locationMap, ArrayList<String> locationList,
							Map<String, Object> propertyMap) throws Exception{

		try{
			
			populateDataFor(locationMap, locationList, propertyMap);
			
		}catch(Exception ex){
			throw new Exception("Could not populate value : ",ex);
		}
		

	}

	private void populateDataFor(Map<String, LocationAttributes> locationMap,ArrayList<String> locationList,
									Map<String, Object> propertyMap) throws Exception {

		LocationPrintAttr locaPrintAttr = new LocationPrintAttr();
		ArrayList<String> locList = new ArrayList<String>(); 
		int noOfIteration = 0;
		int yearToCheck = 0;
		String monthToCheck = null;
		
		
		try{
			
			if(propertyMap.containsKey("iteration")){
				if((Integer)propertyMap.get("iteration") != 0){
					noOfIteration = (Integer)propertyMap.get("iteration");
				}
				
			}
			
			if(propertyMap.containsKey("month")){
				if((String)propertyMap.get("month") != null){
					monthToCheck = (String)propertyMap.get("month");
				}
			}
			
			if(monthToCheck == null){
				monthToCheck =  objUtil.getCurrentMonth();
			}
			
			if(propertyMap.containsKey("year")){
				if((Integer)propertyMap.get("year") != 0){
					yearToCheck = (Integer)propertyMap.get("year");
				}
			}
			
			if( yearToCheck == 0){
				yearToCheck = objUtil.getCurrentYear();
			}
			
			
			for(int i=0; i < noOfIteration ; i++) {

				Iterator<String> locItr = locationList.iterator();
				//String monthToCheck = objUtil.getCurrentMonth() ;

				while(locItr.hasNext()){

					String location = locItr.next();
					locAttr = locationMap.get(location);
					monthlyClimateObj = locAttr.getLocClimMap().get(monthToCheck);
					locaPrintAttr.setLocation(location);
					locaPrintAttr.setLattitude(Float.valueOf(df.format(locAttr.getLattitude())));
					locaPrintAttr.setLongitude(Float.valueOf(df.format(locAttr.getLongitude())));
					locaPrintAttr.setElevation(Float.valueOf(df.format(locAttr.getElev())));
					locaPrintAttr.setDateTime(objUtil.getDateTime(monthToCheck, yearToCheck));

					locaPrintAttr.setPressure(getPressure(monthlyClimateObj));
					locaPrintAttr.setHumidity(Math.round(getHumidity(monthlyClimateObj)*100));
					locaPrintAttr.setTemp(Float.valueOf(df.format(objUtil.convertFahToCenti(getTemp(monthlyClimateObj)))));
					locaPrintAttr.setWeatherCondition(getWeatherCondition(locaPrintAttr.getTemp(), locaPrintAttr.getHumidity()));

					locList.add(locaPrintAttr.toString());
					//System.out.println(locaPrintAttr.toString());

				}

			}
			
		}
		catch(Exception ex){
			throw new Exception("Could not populate value : ",ex);
		}
		
		printLocationDetails(locList);

	}

	public void printLocationDetails(ArrayList<String> locList) throws Exception{


		BufferedWriter bufferedWriter = null;
		try {

			String path= "output.txt";

			File myFile = new File(path);
			if (!myFile.exists()) {
				myFile.createNewFile();
			}
			Writer writer = new FileWriter(myFile);
			bufferedWriter = new BufferedWriter(writer);
			@SuppressWarnings("rawtypes")
			Iterator listIterator = locList.iterator();
			while(listIterator.hasNext()){

				String stmnt = listIterator.next().toString();
				bufferedWriter.write(stmnt.toString());
				bufferedWriter.write("\n");
			}

		} catch (Exception e) {
			throw new Exception("Could not read the value : ",e);
		} finally{
			try{
				if(bufferedWriter != null) bufferedWriter.close();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

	private String getWeatherCondition(float temp, float humidity) {

		String weatherCond = null;
		if((temp < 10 && humidity > 45)|| temp <= -5){
			weatherCond = "Snow";
		}else if(temp >= 10 && humidity > 80){
			weatherCond = "Rain";
		}else{
			weatherCond = "Sunny";
		}
		return weatherCond;
	}

	private float getHumidity(MonthlyClimateVariation monClim) throws Exception {


		float minHumd = monClim.getMinMonthlyHumidity();
		float maxHumd = monClim.getMaxMonthlyHumidity();
		float humditiy = 0.0f;
		try{
			humditiy = Float.valueOf(df.format(objUtil.getRandomFloat(maxHumd, minHumd))); 
		}catch(Exception ex){
			throw new Exception("Could not calculate value : ",ex);
		}

		return humditiy;

	}

	private float getPressure(MonthlyClimateVariation monClim) throws Exception {

		float minPress = monClim.getMinMonthlyPressure();
		float maxPress = monClim.getMaxMonthlyPressure();
		float press = 0.0f;
		try{
			press = Float.valueOf(df.format(objUtil.getRandomFloat(maxPress, minPress))); 
		}catch(Exception e){
			throw new Exception("Could not calculate value : ",e);
		}

		return press;

	}

	private float getTemp(MonthlyClimateVariation monClim) throws Exception {

		float minTemp = monClim.getMinMonthlyTemp();
		float maxTemp = monClim.getMaxMonthlyTemp();
		float temp = 0.0f;
		try{
			temp = Float.valueOf(df.format(objUtil.getRandomFloat(maxTemp, minTemp))); 
		}catch(Exception e){
			throw new Exception("Could not calculate value : ",e);
		}

		return temp;
	}


}
