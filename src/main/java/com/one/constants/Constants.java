package com.one.constants;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class Constants {
	private static final String MONGODB_HOST = "localhost";
	private static final int MONGODB_PORT = 27017;
	private static final String MONGODB_DATABASE = "account1";
	private static final String REGISTRATION = "registration";
	private static final String REGISTRATIONCOUNTER = "registrationCounter";
	private static final String ROLESDETAILS = "rolesDetails";
	private static final String ADMINNAME = "admin";
	private static final String ADMINPASS = "1234a";
	private static final String PHARMACY = "pharmacyDetails";
	private static final String PHARMACYCOUNTER = "pharmacyCounter";
	private static final String HOMES = "homeDetails";
	private static final String HOMESCOUNTER = "homeCounter";
	private static final String RESTAURANT = "restaurantDetails";
	private static final String RESTAURANTCOUNTER = "restaurantCounter";

	public static String getDataBaseName() {
		return MONGODB_DATABASE;

	}

	public static List<ServerAddress> getMongoServerAddresses() {
		List<ServerAddress> serverAddressList = new ArrayList<>();
		serverAddressList.add(new ServerAddress(MONGODB_HOST, MONGODB_PORT));
		return serverAddressList;
	}

	public static List<MongoCredential> getMongoCredentials() {
		List<MongoCredential> credentials = new ArrayList<>();
		return credentials;
	}

	public static String getRegistration() {
		return REGISTRATION;
	}

	public static String getRegistrationcounter() {
		return REGISTRATIONCOUNTER;
	}

	public static String getRolesdetails() {
		return ROLESDETAILS;
	}

	public static String getAdminname() {
		return ADMINNAME;
	}

	public static String getAdminpass() {
		return ADMINPASS;
	}

	public static String getPharmacy() {
		return PHARMACY;
	}

	public static String getPharmacycounter() {
		return PHARMACYCOUNTER;
	}

	public static String getHomes() {
		return HOMES;
	}

	public static String getHomescounter() {
		return HOMESCOUNTER;
	}

	public static String getRestaurant() {
		return RESTAURANT;
	}

	public static String getRestaurantcounter() {
		return RESTAURANTCOUNTER;
	}
}
