package com.test.weather.simulator;

import com.test.weather.simulator.training.WeatherDataTrainer;

public class MainClass {

	public static void main(String[] args) {
		
		WeatherDataTrainer trainerObj = new WeatherDataTrainer();
		trainerObj.trainWithData();

	}

}
