package com.one;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.one.Admin.vo.Pharmacy;
import com.one.constants.BaseResponse;
import com.one.vo.Homes;
import com.one.vo.LocationCordinates;
import com.one.vo.Login;
import com.one.vo.Registration;
import com.one.vo.Restaurant;

@RestController
@RequestMapping("/account")

public class AccountController {
	@Autowired
	AccountService accountService;
	
	private static final Logger logger = Logger.getLogger(AccountController.class);

	@RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse registrtion(@RequestBody Registration registration) throws MyException {
		BaseResponse baseresponse = new BaseResponse();
		System.out.println("regiostration");
		baseresponse = accountService.registration(registration);
		System.out.println("done done skn");
		return baseresponse;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse loginUser(@RequestBody Login login) throws MyException {
		BaseResponse resp = new BaseResponse();
		resp = accountService.loginUser(login);
		return resp;
	}

	@RequestMapping(value = "/fetchpharmacy", method = RequestMethod.POST, headers = "Accept=application/json")
	public List<Pharmacy> fetchingNearestPharmacies(@RequestBody LocationCordinates locationcordinates)
			throws MyException {
		System.out.println("in");
		List<Pharmacy> list = accountService.fetchNearestPharmacies(locationcordinates);
		return list;
	}

	@RequestMapping(value = "/fetchhomes", method = RequestMethod.POST, headers = "Accept=application/json")
	public List<Homes> fetchingNearestHomes(@RequestParam int categoryId) throws MyException {
		System.out.println("in");
		List<Homes> list = accountService.fetchNearestHomes(categoryId);
		return list;
	}
	
	@RequestMapping(value = "/fetchrestaurant", method = RequestMethod.POST, headers = "Accept=application/json")
	public List<Restaurant> fetchingNearestRestaurant(@RequestBody LocationCordinates locationcordinates) throws MyException {
		System.out.println("in");
		List<Restaurant> list = accountService.fetchNearestRestaurant(locationcordinates);
		return list;
	}

	@RequestMapping(value = "/fetchUserLocation", method = RequestMethod.POST, headers = "Accept=application/json")
	public void userLocation(@RequestBody LocationCordinates locationCordinates) throws MyException {
		accountService.fetchUserLocation(locationCordinates);
	}

}
