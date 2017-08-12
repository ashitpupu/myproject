package com.test.weather.simulator;


import org.apache.log4j.Logger;

import com.test.weather.simulator.training.WeatherDataTrainer;

public class MainClass {

	final static Logger logger = Logger.getLogger(MainClass.class);
	
	public static void main(String[] args) {
		
		
		logger.info("Preparing the system to read and analyze the test data");
		WeatherDataTrainer trainerObj = new WeatherDataTrainer();
		trainerObj.trainWithData();
		logger.info("Population of data completed");

	}

}
