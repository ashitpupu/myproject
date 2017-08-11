package com.test.weather.simulator.training;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.test.weather.simulator.beans.LocationAttributes;
import com.test.weather.simulator.beans.MonthlyClimateVariation;
import com.test.weather.simulator.jobs.JobClass;
import com.test.weather.simulator.utility.CommonUtility;


public class WeatherDataTrainer {

	static Map<String, LocationAttributes> locationMap = new HashMap<String, LocationAttributes>();
	static Map<String, MonthlyClimateVariation> locClimMap = new HashMap<String, MonthlyClimateVariation>();
	static CommonUtility utilObj = new CommonUtility();
	static ArrayList<String> locationList = new ArrayList<String>();
	static JobClass job = new JobClass();

	public void trainWithData() {

		BufferedReader br = null;
		FileReader fr = null;

		try {

			String path = new File("config").getCanonicalPath();
			path = path + "/trainingdata.csv";
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			br.readLine();
			String sCurrentLine = null;


			while ((sCurrentLine = br.readLine()) != null) {

				locationMap = populateDetails(sCurrentLine);
			}

			for (Entry<String, LocationAttributes> entry : locationMap.entrySet())
			{
				
				locationList.add(entry.getKey());
			}
			job.populateData(locationMap, locationList);
			
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

	private Map<String, LocationAttributes> populateDetails(String sCurrentLine) {



		LocationAttributes locAttr = new LocationAttributes();
		MonthlyClimateVariation monClimVar = new MonthlyClimateVariation();


		String[] lineSplit = sCurrentLine.split(",");
				
		if(locationMap.isEmpty()){

			String location  = lineSplit[6];
			locAttr.setLocation(location);
			locAttr.setElev(Float.parseFloat(lineSplit[2]));
			locAttr.setLattitude(Float.parseFloat(lineSplit[4]));
			locAttr.setLongitude(Float.parseFloat(lineSplit[5]));
			String month = utilObj.getMonth(Long.parseLong(lineSplit[9]));
			monClimVar.setMonth(month);
			monClimVar.setMinMonthlyTemp(Float.parseFloat(lineSplit[8]));
			monClimVar.setMaxMonthlyTemp(Float.parseFloat(lineSplit[8]));
			monClimVar.setMinMonthlyHumidity(Float.parseFloat(lineSplit[3]));
			monClimVar.setMaxMonthlyhumidity(Float.parseFloat(lineSplit[3]));
			monClimVar.setMinMonthlyPressure(Float.parseFloat(lineSplit[7]));
			monClimVar.setMaxMonthlyPressure(Float.parseFloat(lineSplit[7]));
			monClimVar.setStartEpochTime(Long.parseLong(lineSplit[9]));
			monClimVar.setEndEpochTime(Long.parseLong(lineSplit[9]));
			locClimMap.put(month, monClimVar);
			locAttr.setLocClimMap(locClimMap);
			locationMap.put(location, locAttr);

		}else {
			evaluateAndAdd(lineSplit, locationMap);
		}

		return locationMap;

	}

	private Map<String, LocationAttributes> evaluateAndAdd(String[] lineSplit, Map<String, LocationAttributes> locationMap2) {

		String identifiedLoc = lineSplit[6].toString();
		
		LocationAttributes locAttr = new LocationAttributes();
		MonthlyClimateVariation monClimVar = new MonthlyClimateVariation();
		boolean flag = false;
		
		for ( String locKey : locationMap.keySet() ) {

			if(locKey.equalsIgnoreCase(identifiedLoc)){
				flag = true;
			}
		}

		if(flag){

			locAttr = checkAttributes(lineSplit, identifiedLoc, locationMap);

		}else {

			locAttr = new LocationAttributes();
			locClimMap = new HashMap<String, MonthlyClimateVariation>();
			locAttr.setLocation(identifiedLoc);
			locAttr.setElev(Float.parseFloat(lineSplit[2]));
			locAttr.setLattitude(Float.parseFloat(lineSplit[4]));
			locAttr.setLongitude(Float.parseFloat(lineSplit[5]));
			String month = utilObj.getMonth(Long.parseLong(lineSplit[9]));
			monClimVar.setMonth(month);
			monClimVar.setMinMonthlyTemp(Float.parseFloat(lineSplit[8]));
			monClimVar.setMaxMonthlyTemp(Float.parseFloat(lineSplit[8]));
			monClimVar.setMinMonthlyHumidity(Float.parseFloat(lineSplit[3]));
			monClimVar.setMaxMonthlyhumidity(Float.parseFloat(lineSplit[3]));
			monClimVar.setMinMonthlyPressure(Float.parseFloat(lineSplit[7]));
			monClimVar.setMaxMonthlyPressure(Float.parseFloat(lineSplit[7]));
			monClimVar.setStartEpochTime(Long.parseLong(lineSplit[9]));
			monClimVar.setEndEpochTime(Long.parseLong(lineSplit[9]));
			locClimMap.put(month, monClimVar);
			locAttr.setLocClimMap(locClimMap);
			locationMap.put(identifiedLoc, locAttr);

		}

		return locationMap;

	}

	private static LocationAttributes checkAttributes(String[] lineSplit, String identifiedLoc, Map<String, LocationAttributes> locationMap) {

		
		LocationAttributes locAttr = locationMap.get(identifiedLoc);
		
		
		locAttr = checkHumidityAttribute(locAttr, lineSplit);
		locAttr = checkpressureAttribute(locAttr, lineSplit);
		locAttr = checkTempAttribute(locAttr, lineSplit);
		locAttr = checkEpochTimeAttribute(locAttr, lineSplit);
		

		return locationMap.put(identifiedLoc, locAttr);

	}

	private static LocationAttributes checkEpochTimeAttribute(LocationAttributes locAttr,  String[] lineSplit) {

		MonthlyClimateVariation monClimVar = new MonthlyClimateVariation();

		long setStartTime = 0;
		long setEndTime = 0;
		long epochTimeValue = Long.parseLong(lineSplit[9]);

		String month = utilObj.getMonth(epochTimeValue);
		if(locAttr.getLocClimMap().containsKey(month)) {

			monClimVar = locAttr.getLocClimMap().get(month);
			setStartTime = monClimVar.getStartEpochTime();
			setEndTime = monClimVar.getEndEpochTime();

			if(epochTimeValue < setStartTime){
				monClimVar.setStartEpochTime(epochTimeValue);
			}

			if(epochTimeValue > setEndTime){
				monClimVar.setEndEpochTime(epochTimeValue);
			}

		}else {

			locAttr = populateDefault(month, lineSplit);
		}

		return locAttr;

	}

	private static LocationAttributes checkTempAttribute(LocationAttributes locAttr,  String[] lineSplit) {

		MonthlyClimateVariation monClimVar = new MonthlyClimateVariation();

		float setMinTempValue = 0.0f;
		float setMaxTempValue = 0.0f;
		long epochTimeValue = Long.parseLong(lineSplit[9]);
		float tempValue =  Float.parseFloat(lineSplit[8]);

		String month = utilObj.getMonth(epochTimeValue);
		if(locAttr.getLocClimMap().containsKey(month)) {

			monClimVar = locAttr.getLocClimMap().get(month);
			setMinTempValue = monClimVar.getMinMonthlyTemp();
			setMaxTempValue = monClimVar.getMaxMonthlyTemp();

			if(tempValue < setMinTempValue){
				monClimVar.setMinMonthlyTemp(tempValue);
			}

			if(tempValue > setMaxTempValue){
				monClimVar.setMaxMonthlyTemp(tempValue);
			}

		}else {

			locAttr = populateDefault(month, lineSplit);
		}

		return locAttr;

	}

	private static LocationAttributes checkpressureAttribute(LocationAttributes locAttr,  String[] lineSplit) {

		MonthlyClimateVariation monClimVar = new MonthlyClimateVariation();

		float setMinPressureValue = 0.0f;
		float setMaxPressureValue = 0.0f;
		long epochTimeValue = Long.parseLong(lineSplit[9]);
		float pressureValue = Float.parseFloat(lineSplit[7]) ;

		String month = utilObj.getMonth(epochTimeValue);
		if(locAttr.getLocClimMap().containsKey(month)) {

			monClimVar = locAttr.getLocClimMap().get(month);
			setMinPressureValue = monClimVar.getMinMonthlyPressure();
			setMaxPressureValue = monClimVar.getMaxMonthlyPressure();

			if(pressureValue < setMinPressureValue){
				monClimVar.setMinMonthlyPressure(pressureValue);
			}

			if(pressureValue > setMaxPressureValue){
				monClimVar.setMaxMonthlyPressure(pressureValue);
			}

		}else {

			locAttr = populateDefault(month, lineSplit);
		}

		return locAttr;

	}

	private static LocationAttributes checkHumidityAttribute(LocationAttributes locAttr, String[] lineSplit) {

		MonthlyClimateVariation monClimVar = new MonthlyClimateVariation();

		float setMinHumidityValue = 0.0f;
		float setMaxHumidityValue = 0.0f;
		float humidityValue = Float.parseFloat(lineSplit[3]);
		long epochTimeValue = Long.parseLong(lineSplit[9]);
		
		String month = utilObj.getMonth(epochTimeValue);
		if(locAttr.getLocClimMap().containsKey(month)) {

			monClimVar = locAttr.getLocClimMap().get(month);
			setMinHumidityValue = monClimVar.getMinMonthlyHumidity();
			setMaxHumidityValue = monClimVar.getMaxMonthlyHumidity();

			if(humidityValue < setMinHumidityValue){
				monClimVar.setMinMonthlyHumidity(humidityValue);
			}

			if(humidityValue > setMaxHumidityValue){
				monClimVar.setMaxMonthlyhumidity(humidityValue);
			}

		}else {

			locAttr = populateDefault(month, lineSplit);

		}

		return locAttr;
	}

	private static LocationAttributes populateDefault(String month, String[] lineSplit) {

		MonthlyClimateVariation monClimVar = new MonthlyClimateVariation();
		LocationAttributes locAttr = new LocationAttributes();
		monClimVar.setMonth(month);
		monClimVar.setMinMonthlyTemp(Float.parseFloat(lineSplit[8]));
		monClimVar.setMaxMonthlyTemp(Float.parseFloat(lineSplit[8]));
		monClimVar.setMinMonthlyHumidity(Float.parseFloat(lineSplit[3]));
		monClimVar.setMaxMonthlyhumidity(Float.parseFloat(lineSplit[3]));
		monClimVar.setMinMonthlyPressure(Float.parseFloat(lineSplit[7]));
		monClimVar.setMaxMonthlyPressure(Float.parseFloat(lineSplit[7]));
		monClimVar.setStartEpochTime(Long.parseLong(lineSplit[9]));
		monClimVar.setEndEpochTime(Long.parseLong(lineSplit[9]));
		locClimMap.put(month, monClimVar);
		locAttr.setLocClimMap(locClimMap);
		locAttr.setLocation(lineSplit[6]);
		locAttr.setElev(Float.parseFloat(lineSplit[2]));
		locAttr.setLattitude(Float.parseFloat(lineSplit[4]));
		locAttr.setLongitude(Float.parseFloat(lineSplit[5]));
		return locAttr;
	}

}
