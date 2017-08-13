package com.test.weather.simulator.training;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;




import org.apache.log4j.Logger;

import com.test.weather.simulator.beans.LocationAttributes;
import com.test.weather.simulator.beans.MonthlyClimateVariation;
import com.test.weather.simulator.jobs.JobClass;
import com.test.weather.simulator.utility.CommonUtility;


public class WeatherDataTrainer {

	final static Logger logger = Logger.getLogger(WeatherDataTrainer.class);

	static Map<String, LocationAttributes> locationMap = new HashMap<String, LocationAttributes>();
	static Map<String, MonthlyClimateVariation> locClimMap = new HashMap<String, MonthlyClimateVariation>();
	static CommonUtility utilObj = new CommonUtility();
	static ArrayList<String> locationList = new ArrayList<String>();
	static JobClass job = new JobClass();
	Map<String, Object> propertyMap = new HashMap<String, Object>();

	/*******************************************************
	 * Training the system with the provided data to 
	 * analyse min and max topographic conditions
	 * 
	 ******************************************************/
	public void trainWithData() {

		BufferedReader br = null;
		FileReader fr = null;

		try {

			String path = new File("config").getCanonicalPath();
			logger.info("Config path is " + path);
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
			propertyMap = utilObj.getYearAndMonth();
			job.populateData(locationMap, locationList, propertyMap);

		} catch (Exception e) {


			logger.error("Error reading the file provided " + e);

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				logger.error("Unable to close the bufferreader " + ex);

			}

		}

	}

	/***************************************************************
	 * This method splits each line and populates data in min and max
	 * @param sCurrentLine
	 * @return
	 ***************************************************************/
	@SuppressWarnings("finally")
	private Map<String, LocationAttributes> populateDetails(String sCurrentLine) {



		LocationAttributes locAttr = new LocationAttributes();
		MonthlyClimateVariation monClimVar = new MonthlyClimateVariation();


		String[] lineSplit = sCurrentLine.split(",");

		try{

			if(locationMap.isEmpty()){

				String location  = lineSplit[6];
				locAttr.setLocation(location);
				logger.info("Populating attributes for location " + location);
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

		}catch(Exception e){
			logger.error("Error caught while populating data " + e);
		} finally {
			return locationMap;
		}

	}

	/************************************************************************
	 * In case of a location value added earlier this method is called and it compares
	 * the given data with previously populated min and max value are smaller .
	 * If the condition fails then max or min value is changed accordingly
	 * @param lineSplit
	 * @param locationMap2
	 * @return
	 * @throws Exception
	 *********************************************************************/
	private Map<String, LocationAttributes> evaluateAndAdd(String[] lineSplit, 
			Map<String, LocationAttributes> locationMap) throws Exception {

		String identifiedLoc = lineSplit[6].toString();

		LocationAttributes locAttr = new LocationAttributes();
		MonthlyClimateVariation monClimVar = new MonthlyClimateVariation();
		boolean locationPresent = false;

		try{

			for ( String locKey : locationMap.keySet() ) {

				if(locKey.equalsIgnoreCase(identifiedLoc)){
					locationPresent = true;
				}
			}

			if(locationPresent){

				locAttr = checkAttributes(lineSplit, identifiedLoc, locationMap);

			}else {

				locAttr = new LocationAttributes();
				locClimMap = new HashMap<String, MonthlyClimateVariation>();
				locAttr.setLocation(identifiedLoc);
				logger.info("Populating attributes for location " + identifiedLoc);
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

		}catch(Exception ex){
			throw new Exception("Could not generate value : ",ex);
		}

	}

	private static LocationAttributes checkAttributes(String[] lineSplit, String identifiedLoc, 
			Map<String, LocationAttributes> locationMap) throws Exception {


		LocationAttributes locAttr = locationMap.get(identifiedLoc);

		try{

			locAttr = checkHumidityAttribute(locAttr, lineSplit);
			locAttr = checkpressureAttribute(locAttr, lineSplit);
			locAttr = checkTempAttribute(locAttr, lineSplit);
			locAttr = checkEpochTimeAttribute(locAttr, lineSplit);

		}catch(Exception ex){
			throw new Exception("Could not generate value : ",ex);
		}


		return locationMap.put(identifiedLoc, locAttr);

	}

	private static LocationAttributes checkEpochTimeAttribute(LocationAttributes locAttr,  String[] lineSplit) throws Exception{

		MonthlyClimateVariation monClimVar = new MonthlyClimateVariation();

		long setStartTime = 0;
		long setEndTime = 0;
		long epochTimeValue = Long.parseLong(lineSplit[9]);

		String month = utilObj.getMonth(epochTimeValue);

		try {

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

		}catch(Exception ex){
			throw new Exception("Could not generate date time value : ",ex);
		}


		return locAttr;

	}

	private static LocationAttributes checkTempAttribute(LocationAttributes locAttr,  String[] lineSplit) throws Exception {

		MonthlyClimateVariation monClimVar = new MonthlyClimateVariation();

		float setMinTempValue = 0.0f;
		float setMaxTempValue = 0.0f;
		long epochTimeValue = Long.parseLong(lineSplit[9]);
		float tempValue =  Float.parseFloat(lineSplit[8]);

		String month = utilObj.getMonth(epochTimeValue);
		try{

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

		}catch(Exception ex){
			throw new Exception("Could not generate temperature value : ",ex);
		}


		return locAttr;

	}

	private static LocationAttributes checkpressureAttribute(LocationAttributes locAttr,  String[] lineSplit) throws Exception {

		MonthlyClimateVariation monClimVar = new MonthlyClimateVariation();

		float setMinPressureValue = 0.0f;
		float setMaxPressureValue = 0.0f;
		long epochTimeValue = Long.parseLong(lineSplit[9]);
		float pressureValue = Float.parseFloat(lineSplit[7]) ;

		String month = utilObj.getMonth(epochTimeValue);
		try{
			
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
			
		}catch(Exception ex){
			throw new Exception("Could not generate pressure value : ",ex);
		}
		

		return locAttr;

	}

	private static LocationAttributes checkHumidityAttribute(LocationAttributes locAttr, String[] lineSplit) throws Exception {

		MonthlyClimateVariation monClimVar = new MonthlyClimateVariation();

		float setMinHumidityValue = 0.0f;
		float setMaxHumidityValue = 0.0f;
		float humidityValue = Float.parseFloat(lineSplit[3]);
		long epochTimeValue = Long.parseLong(lineSplit[9]);

		String month = utilObj.getMonth(epochTimeValue);
		try{
			
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
		}catch(Exception ex){
			throw new Exception("Could not generate humidity value : ",ex);
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
