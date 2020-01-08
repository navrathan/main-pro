package com.one;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.one.Admin.vo.Pharmacy;
import com.one.constants.BaseResponse;
import com.one.vo.Homes;
import com.one.vo.LocationCordinates;
import com.one.vo.Login;
import com.one.vo.Registration;
import com.one.vo.Restaurant;

@Repository
public interface AccountDao {
	BaseResponse registration(Registration registration) throws MyException;

	BaseResponse login(Login login) throws MyException;

	BaseResponse insertPharmacy(Pharmacy pharmacy) throws MyException;

	BaseResponse deletePharmacy(Pharmacy pharmacy) throws MyException;

	BaseResponse updatePharmacy(Pharmacy pharmacy) throws MyException;

	List<Pharmacy> fetchNearestPharmacies(LocationCordinates locationcordinates) throws MyException;

	BaseResponse insertHomes(Homes homes) throws MyException;

	BaseResponse deleteHomes(Homes homes) throws MyException;

	BaseResponse updateHomes(Homes homes) throws MyException;

	BaseResponse insertRestaurant(Restaurant restaurant) throws MyException;

	BaseResponse deleteRestaurant(Restaurant restaurant) throws MyException;

	BaseResponse updateRestaurant(Restaurant restaurant) throws MyException;

	List<Homes> fetchNearestHomes(int categoryId)throws MyException;

	Login loginUser(Login login) throws MyException;

	List<Restaurant> fetchNearestRestaurant(LocationCordinates locationcordinates)throws MyException;

}
