package com.test.weather.simulator.beans;

import java.util.Map;


public class LocationAttributes {

	private float elev;
	private float lattitude;
	private float longitude;
	private String location;
	private Map<String, MonthlyClimateVariation> locClimMap;
	
	public LocationAttributes(){
		
	}
	
	

	public LocationAttributes(float elev, float lattitude, float longitude, String location,
			Map<String, MonthlyClimateVariation> locClimMap) {
		super();
		this.elev = elev;
		this.lattitude = lattitude;
		this.longitude = longitude;
		this.location = location;
		this.locClimMap = locClimMap;
	}



	public float getElev() {
		return elev;
	}

	public void setElev(float elev) {
		this.elev = elev;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Map<String, MonthlyClimateVariation> getLocClimMap() {
		return locClimMap;
	}

	public void setLocClimMap(Map<String, MonthlyClimateVariation> locClimMap) {
		this.locClimMap = locClimMap;
	}



	@Override
	public String toString() {
		return "LocationAttributes [elev=" + elev + ", lattitude=" + lattitude + ", longitude=" + longitude
				+ ", location=" + location + ", locClimMap=" + locClimMap + "]";
	}
	
}
