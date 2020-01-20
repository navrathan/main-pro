package com.one.vo;

public class LocationCordinates {

	
	public LocationCordinates() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LocationCordinates(double lat, double lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}
	private double lat;
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	private double lon;
}
