package com.test.weather.simulator.beans;

public class MonthlyClimateVariation {
	

	
	private float minMonthlyTemp;
	private float maxMonthlyTemp;
	private float minMonthlyHumidity;
	private float maxMonthlyHumidity;
	private float minMonthlyPressure;
	private float maxMonthlyPressure;
	private long startEpochTime;
	private long endEpochTime;
	private String month;
	
	public MonthlyClimateVariation(){
		
	}

	
	public MonthlyClimateVariation(float minMonthlyTemp, float maxMonthlyTemp, float minMonthlyHumidity,
			float maxMonthlyHumidity, float minMonthlyPressure, float maxMonthlyPressure, long startEpochTime,
			long endEpochTime, String month) {
		super();
		this.minMonthlyTemp = minMonthlyTemp;
		this.maxMonthlyTemp = maxMonthlyTemp;
		this.minMonthlyHumidity = minMonthlyHumidity;
		this.maxMonthlyHumidity = maxMonthlyHumidity;
		this.minMonthlyPressure = minMonthlyPressure;
		this.maxMonthlyPressure = maxMonthlyPressure;
		this.startEpochTime = startEpochTime;
		this.endEpochTime = endEpochTime;
		this.month = month;
	}


	public float getMinMonthlyTemp() {
		return minMonthlyTemp;
	}

	public void setMinMonthlyTemp(float minMonthlyTemp) {
		this.minMonthlyTemp = minMonthlyTemp;
	}

	public float getMaxMonthlyTemp() {
		return maxMonthlyTemp;
	}

	public void setMaxMonthlyTemp(float maxMonthlyTemp) {
		this.maxMonthlyTemp = maxMonthlyTemp;
	}

	public float getMinMonthlyHumidity() {
		return minMonthlyHumidity;
	}

	public void setMinMonthlyHumidity(float minMonthlyHumidity) {
		this.minMonthlyHumidity = minMonthlyHumidity;
	}

	public float getMaxMonthlyHumidity() {
		return maxMonthlyHumidity;
	}

	public void setMaxMonthlyhumidity(float maxMonthlyHumidity) {
		this.maxMonthlyHumidity = maxMonthlyHumidity;
	}

	public float getMinMonthlyPressure() {
		return minMonthlyPressure;
	}

	public void setMinMonthlyPressure(float minMonthlyPressure) {
		this.minMonthlyPressure = minMonthlyPressure;
	}

	public float getMaxMonthlyPressure() {
		return maxMonthlyPressure;
	}

	public void setMaxMonthlyPressure(float maxMonthlyPressure) {
		this.maxMonthlyPressure = maxMonthlyPressure;
	}

	public long getStartEpochTime() {
		return startEpochTime;
	}

	public void setStartEpochTime(long startEpochTime) {
		this.startEpochTime = startEpochTime;
	}

	public long getEndEpochTime() {
		return endEpochTime;
	}

	public void setEndEpochTime(long endEpochTime) {
		this.endEpochTime = endEpochTime;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Override
	public String toString() {
		return "MonthlyClimateVariation [minMonthlyTemp=" + minMonthlyTemp + ", maxMonthlyTemp=" + maxMonthlyTemp
				+ ", minMonthlyHumidity=" + minMonthlyHumidity + ", maxMonthlyhumidity=" + maxMonthlyHumidity
				+ ", minMonthlyPressure=" + minMonthlyPressure + ", maxMonthlyPressure=" + maxMonthlyPressure
				+ ", startEpochTime=" + startEpochTime + ", endEpochTime=" + endEpochTime + ", month=" + month + "]";
	}

}
