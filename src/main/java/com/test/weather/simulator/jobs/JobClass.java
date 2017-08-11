package com.test.weather.simulator.jobs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.test.weather.simulator.beans.LocationAttributes;
import com.test.weather.simulator.beans.LocationPrintAttr;
import com.test.weather.simulator.beans.MonthlyClimateVariation;
import com.test.weather.simulator.utility.CommonUtility;

public class JobClass {

	static LocationAttributes locAttr = new LocationAttributes();
	MonthlyClimateVariation monthlyClimateObj = new MonthlyClimateVariation();
	DecimalFormat df = new DecimalFormat("###.##");
	CommonUtility objUtil = new CommonUtility();

	public void populateData(Map<String, LocationAttributes> locationMap, ArrayList<String> locationList) {


		populateDataFor(locationMap, locationList);

	}

	private void populateDataFor(Map<String, LocationAttributes> locationMap,ArrayList<String> locationList) {

		LocationPrintAttr locaPrintAttr = new LocationPrintAttr();
		ArrayList<String> locList = new ArrayList<String>(); 
		int noOfIteration = 2;

		for(int i=0; i<noOfIteration ; i++) {

			Iterator<String> locItr = locationList.iterator();
			String monthToCheck = objUtil.getCurrentMonth();

			while(locItr.hasNext()){

				String location = locItr.next();
				locAttr = locationMap.get(location);
				monthlyClimateObj = locAttr.getLocClimMap().get(monthToCheck);
				locaPrintAttr.setLocation(location);
				locaPrintAttr.setLattitude(Float.valueOf(df.format(locAttr.getLattitude())));
				locaPrintAttr.setLongitude(Float.valueOf(df.format(locAttr.getLongitude())));
				locaPrintAttr.setElevation(Float.valueOf(df.format(locAttr.getElev())));
				locaPrintAttr.setDateTime(objUtil.getDateTime(location));

				locaPrintAttr.setPressure(getPressure(monthlyClimateObj));
				locaPrintAttr.setHumidity(Math.round(getHumidity(monthlyClimateObj)*100));
				locaPrintAttr.setTemp(Float.valueOf(df.format(objUtil.convertFahToCenti(getTemp(monthlyClimateObj)))));
				locaPrintAttr.setWeatherCondition(getWeatherCondition(locaPrintAttr.getTemp(), locaPrintAttr.getHumidity()));

				locList.add(locaPrintAttr.toString());
				//System.out.println(locaPrintAttr.toString());

			}

		}
		
		printLocationDetails(locList);

	}

	public void printLocationDetails(ArrayList<String> locList) {


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
			e.printStackTrace();
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
		if((temp < 10 && humidity > 45)|| temp <= -10){
			weatherCond = "Snow";
		}else if(temp >= 10 && humidity > 80){
			weatherCond = "Rain";
		}else{
			weatherCond = "Sunny";
		}
		return weatherCond;
	}

	private float getHumidity(MonthlyClimateVariation monClim) {


		float minHumd = monClim.getMinMonthlyHumidity();
		float maxHumd = monClim.getMaxMonthlyHumidity();
		float humditiy = 0.0f;
		try{
			humditiy = Float.valueOf(df.format(objUtil.getRandomFloat(maxHumd, minHumd))); 
		}catch(Exception e){

		}

		return humditiy;

	}

	private float getPressure(MonthlyClimateVariation monClim) {

		float minPress = monClim.getMinMonthlyPressure();
		float maxPress = monClim.getMaxMonthlyPressure();
		float press = 0.0f;
		try{
			press = Float.valueOf(df.format(objUtil.getRandomFloat(maxPress, minPress))); 
		}catch(Exception e){

		}

		return press;

	}

	private float getTemp(MonthlyClimateVariation monClim) {

		float minTemp = monClim.getMinMonthlyTemp();
		float maxTemp = monClim.getMaxMonthlyTemp();
		float temp = 0.0f;
		try{
			temp = Float.valueOf(df.format(objUtil.getRandomFloat(maxTemp, minTemp))); 
		}catch(Exception e){

		}

		return temp;
	}



}
