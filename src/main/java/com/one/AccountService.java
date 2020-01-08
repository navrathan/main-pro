package com.one;

import java.net.http.HttpRequest;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.one.Admin.vo.Pharmacy;
import com.one.constants.BaseResponse;
import com.one.vo.Encrypt;
import com.one.vo.Homes;
import com.one.vo.LocationCordinates;
import com.one.vo.Login;
import com.one.vo.Registration;
import com.one.vo.Restaurant;

@Service("accountService")
public class AccountService {
	@Autowired
	AccountDao accountdao;
	private HttpServletRequest request;
	public HttpSession session;

	public BaseResponse registration(Registration registration) throws MyException {
		BaseResponse baseresponse = new BaseResponse();
		registration.setFullName(registration.getFirstName() + " " + registration.getLastName());
		registration.setPassword(Encrypt.passwordEncyption(registration.getPassword()));
		accountdao.registration(registration);
		return baseresponse;
	}

	public BaseResponse loginUser(Login login) throws MyException {

		BaseResponse resp = new BaseResponse();
		Encrypt passwordEncrypt = new Encrypt();
		String pass = login.getPassword();
		login.setPassword(passwordEncrypt.passwordEncyption(pass));
		Login user = accountdao.loginUser(login);
		System.out.println("in account service");

		if (user != null) {
			session = request.getSession(true);
			resp.setStatusCode(200);
			resp.setStatusMessage("login successful");
			return resp;
		} else {
			resp.setStatusCode(100);
			resp.setStatusMessage("login unsuccessful");
			return resp;
		}
	}

	public BaseResponse login(Login login) throws MyException {
		BaseResponse response = new BaseResponse();
		Encrypt passwordEncryptio = new Encrypt();
		String pass = login.getPassword();
		login.setPassword(passwordEncryptio.passwordEncyption(pass));
		response = accountdao.login(login);
		System.out.println("in account service");

		HttpSession session;
		if (response != null) {
			session = request.getSession(true);

		}
		return response;
	}

	public BaseResponse insertPharmacy(Pharmacy pharmacy) throws MyException {
		BaseResponse response = new BaseResponse();

		response = accountdao.insertPharmacy(pharmacy);
		return response;
	}

	public BaseResponse deletePharmacy(Pharmacy pharmacy) throws MyException {
		BaseResponse response = new BaseResponse();
		response = accountdao.deletePharmacy(pharmacy);
		return response;

	}

	public BaseResponse updatePharmacy(Pharmacy pharmacy) throws MyException {
		BaseResponse response = new BaseResponse();
		response = accountdao.updatePharmacy(pharmacy);
		return response;
	}

	public List<Pharmacy> fetchNearestPharmacies(LocationCordinates locationcordinates) throws MyException {
		List<Pharmacy> list = accountdao.fetchNearestPharmacies(locationcordinates);

		return list;
	}

	public BaseResponse insertHomes(Homes homes) throws MyException {
		BaseResponse response = new BaseResponse();
		response = accountdao.insertHomes(homes);
		return response;
	}

	public BaseResponse deleteHomes(Homes homes) throws MyException {
		BaseResponse response = new BaseResponse();
		response = accountdao.deleteHomes(homes);
		return response;
	}

	public BaseResponse updateHomes(Homes homes) throws MyException {
		BaseResponse response = new BaseResponse();
		response = accountdao.updateHomes(homes);
		return response;
	}

	public BaseResponse insertRestaurant(Restaurant restaurant) throws MyException {
		BaseResponse response = new BaseResponse();
		response = accountdao.insertRestaurant(restaurant);
		return response;
	}

	public BaseResponse deleteRestaurant(Restaurant restaurant) throws MyException {
		BaseResponse response = new BaseResponse();
		response = accountdao.deleteRestaurant(restaurant);
		return response;
	}

	public BaseResponse updateRestaurant(Restaurant restaurant) throws MyException {
		BaseResponse response = new BaseResponse();
		response = accountdao.updateRestaurant(restaurant);
		return response;
	}

	public List<Homes> fetchNearestHomes(int categoryId) throws MyException {
		List<Homes> list = accountdao.fetchNearestHomes(categoryId);

		return list;
	}

	public void fetchUserLocation(LocationCordinates locationCordinates)throws MyException {
		session.setAttribute("coordinates", locationCordinates);
		LocationCordinates locationcordinates=(LocationCordinates) session.getAttribute("coordinates");
		
	}

	public List<Restaurant> fetchNearestRestaurant(LocationCordinates locationcordinates)throws MyException {
		List<Restaurant> list = accountdao.fetchNearestRestaurant(locationcordinates);
		return list;
	}

}
