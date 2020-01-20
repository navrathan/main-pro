package com.one.Admin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.one.AccountService;
import com.one.MyException;
import com.one.vo.Pharmacy;
import com.one.constants.BaseResponse;
import com.one.vo.Admin;
import com.one.vo.Homes;
import com.one.vo.Login;
import com.one.vo.Restaurant;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AccountService accountService;

	@Autowired
	AdminService adminService;

	private static final Logger logger = Logger.getLogger(AdminController.class);

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public Admin login(@RequestBody Login login) {
		try {
			if (login != null && login.getEmail() != null && login.getPassword() != null && !login.getEmail().isEmpty()
					&& !login.getPassword().isEmpty()) {
				Admin admin = adminService.login(login);
				return admin;
			}
		} catch (MyException t) {
			logger.error("admin login failure", t);
		}
		return new Admin();

	}

	@PreAuthorize("hasPermission('null','add')")
	@RequestMapping(value = "/secure/insertpharmacy", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse insertPharmacy(@RequestBody com.one.vo.Pharmacy pharmacy) {
		try {
			if (pharmacy != null && pharmacy.getPharmacyName() != null
					&& pharmacy.getAddress().getLocationCordinates().getLat() != 0
					&& pharmacy.getAddress().getLocationCordinates().getLon() != 0) {
				BaseResponse response = adminService.insertPharmacy(pharmacy);
				return response;
			}
		} catch (MyException t) {
			logger.error("exception while inserting pharmacy", t);
		}
		return new BaseResponse();
	}

	@PreAuthorize("hasPermission('null','delete')")
	@RequestMapping(value = "/secure/deletepharmacy", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse deletePharmacy(@RequestBody Pharmacy pharmacy) {
		try {
			if (pharmacy != null && pharmacy.getPharmacyId() != 0) {
				BaseResponse response = adminService.deletePharmacy(pharmacy);
				return response;
			}
		} catch (MyException t) {
			logger.error("exception while deleting pharmacy", t);
		}
		return new BaseResponse();
	}

	@PreAuthorize("hasPermission('null','update')")
	@RequestMapping(value = "/secure/updatepharmacy", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse updatePharmacy(@RequestBody Pharmacy pharmacy) {
		try {
			if (pharmacy != null && pharmacy.getPharmacyId() != 0) {
				BaseResponse response = adminService.updatePharmacy(pharmacy);
				return response;
			}
		} catch (MyException t) {
			logger.error("exception while updating pharmacy", t);
		}
		return new BaseResponse();
	}

	@PreAuthorize("hasPermission('null','add')")
	@RequestMapping(value = "/secure/inserthomes", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse insertHomes(@RequestBody Homes homes) {
		try {
			if (homes != null && homes.getBuildingName() != null
					&& homes.getAddress().getLocationCordinates().getLat() != 0
					&& homes.getAddress().getLocationCordinates().getLon() != 0 && homes.getCategoryId() != 0) {
				BaseResponse response = adminService.insertHomes(homes);
				return response;
			}
		} catch (MyException t) {
			logger.error("exception while inserting homes", t);
		}
		return new BaseResponse();
	}

	@PreAuthorize("hasPermission('null','delete')")
	@RequestMapping(value = "/secure/deletehomes", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse deleteHomes(@RequestBody Homes homes) {
		try {
			if (homes != null && homes.getBuildingId() != 0) {
				BaseResponse response = adminService.deleteHomes(homes);
				return response;
			}
		} catch (MyException t) {
			logger.error("exception while deleting homes", t);
		}
		return new BaseResponse();
	}

	@PreAuthorize("hasPermission('null','update')")
	@RequestMapping(value = "/secure/updatehomes", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse updateHomes(@RequestBody Homes homes) {
		try {
			if (homes != null && homes.getBuildingId() != 0 && homes.getCategoryId()!=0) {
				BaseResponse response = adminService.updateHomes(homes);
				return response;
			}
		} catch (MyException t) {
			logger.error("exception while updating homes", t);
		}
		return new BaseResponse();
	}

	@PreAuthorize("hasPermission('null','add')")
	@RequestMapping(value = "/secure/insertrestaurant", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse insertRestaurant(@RequestBody Restaurant restaurant) {
		try {
			if (restaurant != null && restaurant.getRestaurantName() != null
					&& restaurant.getAddress().getLocationCordinates().getLat() != 0
					&& restaurant.getAddress().getLocationCordinates().getLon() != 0) {
				BaseResponse response = adminService.insertRestaurant(restaurant);
				return response;
			}
		} catch (MyException t) {
			logger.error("exception while inserting restaurant", t);
		}
		return new BaseResponse();
	}

	@PreAuthorize("hasPermission('null','delete')")
	@RequestMapping(value = "/secure/deleterestaurant", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse deleteRestaurant(@RequestBody Restaurant restaurant) {
		try {
			if (restaurant != null && restaurant.getRestaurantId() != 0) {
				BaseResponse response = adminService.deleteRestaurant(restaurant);
				return response;
			}
		} catch (MyException t) {
			logger.error("exception while deleting restaurants", t);
		}
		return new BaseResponse();
	}

	@PreAuthorize("hasPermission('null','update')")
	@RequestMapping(value = "/secure/updaterestaurant", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse updateRestaurant(@RequestBody Restaurant restaurant) {
		try {
			if (restaurant != null && restaurant.getRestaurantId() != 0) {
				BaseResponse response = adminService.updateRestaurant(restaurant);
				return response;
			}
		} catch (MyException t) {
			logger.error("exception while updating restaurants", t);
		}
		return new BaseResponse();
	}

}
