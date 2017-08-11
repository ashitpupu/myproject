package com.test.weather.simulator.beans;

public class LocationPrintAttr {
	
	String location ;
	float lattitude;
	float longitude;
	float elevation;
	String dateTime;
	String weatherCondition;
	float temp;
	float pressure;
	float humidity;
	
	public LocationPrintAttr(){
		
	}
	
	public LocationPrintAttr(String location, float lattitude, float longitude,
			float elevation, String dateTime, String weatherCondition,
			float temp, float pressure, float humidity) {
		super();
		this.location = location;
		this.lattitude = lattitude;
		this.longitude = longitude;
		this.elevation = elevation;
		this.dateTime = dateTime;
		this.weatherCondition = weatherCondition;
		this.temp = temp;
		this.pressure = pressure;
		this.humidity = humidity;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public float getLattitude() {
		return lattitude;
	}
	public void setLattitude(float lattitude) {
		this.lattitude = lattitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getElevation() {
		return elevation;
	}
	public void setElevation(float elevation) {
		this.elevation = elevation;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getWeatherCondition() {
		return weatherCondition;
	}
	public void setWeatherCondition(String weatherCondition) {
		this.weatherCondition = weatherCondition;
	}
	public float getTemp() {
		return temp;
	}
	public void setTemp(float temp) {
		this.temp = temp;
	}
	public float getPressure() {
		return pressure;
	}
	public void setPressure(float pressure) {
		this.pressure = pressure;
	}
	public float getHumidity() {
		return humidity;
	}
	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}
	
	@Override
	public String toString() {
		return location+"|"+
				lattitude + "," + longitude + "," + elevation + "|" +
				dateTime + "|" +
				weatherCondition + "|" +
				temp      + "|" +
				pressure  + "|" +
				humidity;

	}

	

}
