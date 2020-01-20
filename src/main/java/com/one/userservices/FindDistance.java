package com.one.userservices;

public class FindDistance {
	static final int R = 6371;
	static double distance = 0;
	public static double findDistance(double lat1, double lon1, double lat2, double lon2) {
		double userLon = Math.toRadians(lon1);
		lon2 = Math.toRadians(lon2);
		double userLat = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double dlon = lon2 - userLon;
		double dlat = lat2 - userLat;
		double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
		double c = 2 * Math.asin(Math.sqrt(a));
		distance = (c * R);
		return distance;

	}
}
