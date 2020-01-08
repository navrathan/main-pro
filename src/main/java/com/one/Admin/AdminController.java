package com.one.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.one.AccountService;
import com.one.MyException;
import com.one.Admin.vo.Pharmacy;
import com.one.constants.BaseResponse;
import com.one.vo.Homes;
import com.one.vo.Login;
import com.one.vo.Restaurant;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AccountService accountService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse login(@RequestBody Login login) throws MyException {
		BaseResponse response = new BaseResponse();
		System.out.println("INSIDE THE Login");
		response = accountService.login(login);
		System.out.println("out");
		return response;

	}

	@RequestMapping(value = "/insertpharmacy", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse insertPharmacy(@RequestBody Pharmacy pharmacy) throws MyException {
		BaseResponse response = new BaseResponse();
		System.out.println("INSIDE THE INSERT PHARMACY");
		response = accountService.insertPharmacy(pharmacy);

		System.out.println("out");
		return response;
	}

	@RequestMapping(value = "/deletepharmacy", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse deletePharmacy(@RequestBody Pharmacy pharmacy) throws MyException {
		BaseResponse response = new BaseResponse();
		System.out.println("INSIDE THE DELETE PHARMACY");
		response = accountService.deletePharmacy(pharmacy);
		return response;
	}

	@RequestMapping(value = "/updatepharmacy", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse updatePharmacy(@RequestBody Pharmacy pharmacy) throws MyException {
		BaseResponse response = new BaseResponse();
		System.out.println("INSIDE THE UPDATE PHARMACY");
		response = accountService.updatePharmacy(pharmacy);
		return response;
	}

	@RequestMapping(value = "/inserthomes", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse insertHomes(@RequestBody Homes homes) throws MyException {
		BaseResponse response = new BaseResponse();
		System.out.println("INSIDE THE INSERT HOMES");
		response = accountService.insertHomes(homes);
		return response;
	}

	@RequestMapping(value = "/deletehomes", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse deleteHomes(@RequestBody Homes homes) throws MyException {
		BaseResponse response = new BaseResponse();
		System.out.println("INSIDE THE DELETE HOMES");
		response = accountService.deleteHomes(homes);
		return response;
	}

	@RequestMapping(value = "/updatehomes", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse updateHomes(@RequestBody Homes homes) throws MyException {
		BaseResponse response = new BaseResponse();
		System.out.println("INSIDE THE UPDATE Homes");
		response = accountService.updateHomes(homes);
		return response;
	}

	@RequestMapping(value = "/insertrestaurant", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse insertRestaurant(@RequestBody Restaurant restaurant) throws MyException {
		BaseResponse response = new BaseResponse();
		System.out.println("INSIDE THE INSERT RESTAURANT");
		response = accountService.insertRestaurant(restaurant);
		return response;
	}

	@RequestMapping(value = "/deleterestaurant", method = RequestMethod.POST, headers = "Accept=application/json" )
	public BaseResponse deleteRestaurant(@RequestBody Restaurant restaurant) throws MyException {
		
		BaseResponse response = new BaseResponse();
		System.out.println("INSIDE THE DELETE RESTAURANT");
		response = accountService.deleteRestaurant(restaurant);
		return response;
	}

	@RequestMapping(value = "/updaterestaurant", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse updateRestaurant(@RequestBody Restaurant restaurant) throws MyException {
		BaseResponse response = new BaseResponse();
		System.out.println("INSIDE THE UPDATE RESTAURANT");
		response = accountService.updateRestaurant(restaurant);
		return response;
	}

}
