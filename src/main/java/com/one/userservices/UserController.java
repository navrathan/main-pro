package com.one.userservices;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.one.AccountController;
import com.one.MyException;
import com.one.vo.Homes;
import com.one.vo.LocationCordinates;
import com.one.vo.Pharmacy;
import com.one.vo.Restaurant;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	private static final Logger logger = Logger.getLogger(AccountController.class);

	@PreAuthorize("hasPermission('null','view')")
	@RequestMapping(value = "/secure/fetchpharmacy", method = RequestMethod.POST, headers = "Accept=application/json")
	public List<Pharmacy> fetchingNearestPharmacies(@RequestBody LocationCordinates locationcordinates) {
		try {
			if (locationcordinates != null && locationcordinates.getLat() != 0 && locationcordinates.getLon() != 0) {
				List<Pharmacy> list = userService.fetchNearestPharmacies(locationcordinates);
				return list;
			}
		} catch (MyException t) {
			logger.error("exception while fetching nearest pharmacies", t);
		}
		return new ArrayList<Pharmacy>();
	}

	@PreAuthorize("hasPermission('null','view')")
	@RequestMapping(value = "/secure/fetchhomes", method = RequestMethod.POST, headers = "Accept=application/json")
	public List<Homes> fetchingNearestHomes(@RequestParam int categoryId, @RequestParam double lat,
			@RequestParam double lon) {
		try {
			if ( categoryId!=0 && lat != 0 && lon != 0) {
				LocationCordinates locationCordinates = new LocationCordinates();
				locationCordinates.setLat(lat);
				locationCordinates.setLon(lon);
				List<Homes> list = userService.fetchNearestHomes(categoryId, locationCordinates);
				return list;
			}
		} catch (MyException t) {
			logger.error("exception while fecthing nearest homes", t);
		}
		return new ArrayList<Homes>();
	}

	@PreAuthorize("hasPermission('null','view')")
	@RequestMapping(value = "/secure/fetchrestaurant", method = RequestMethod.POST, headers = "Accept=application/json")
	public List<Restaurant> fetchingNearestRestaurant(@RequestBody LocationCordinates locationcordinates) {
		try {
			if (locationcordinates != null && locationcordinates.getLat() != 0 && locationcordinates.getLon() != 0) {
				List<Restaurant> list = userService.fetchNearestRestaurant(locationcordinates);
				return list;
			}
		} catch (MyException t) {
			logger.error("exception while fecthing nearest restaurant", t);
		}
		return new ArrayList<Restaurant>();
	}
}
