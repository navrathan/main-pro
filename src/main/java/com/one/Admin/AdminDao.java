package com.one.Admin;

import org.springframework.stereotype.Repository;

import com.one.MyException;
import com.one.vo.Pharmacy;
import com.one.constants.BaseResponse;
import com.one.vo.Homes;
import com.one.vo.Restaurant;

@Repository
public interface AdminDao {

	BaseResponse insertPharmacy(Pharmacy pharmacy) throws MyException;

	BaseResponse deletePharmacy(Pharmacy pharmacy) throws MyException;

	BaseResponse updatePharmacy(Pharmacy pharmacy) throws MyException;

	BaseResponse insertHomes(Homes homes) throws MyException;

	BaseResponse deleteHomes(Homes homes) throws MyException;

	BaseResponse updateHomes(Homes homes) throws MyException;

	BaseResponse insertRestaurant(Restaurant restaurant) throws MyException;

	BaseResponse deleteRestaurant(Restaurant restaurant) throws MyException;

	BaseResponse updateRestaurant(Restaurant restaurant) throws MyException;

}
