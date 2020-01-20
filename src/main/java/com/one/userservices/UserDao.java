package com.one.userservices;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.one.MyException;
import com.one.vo.Homes;
import com.one.vo.LocationCordinates;
import com.one.vo.Pharmacy;
import com.one.vo.Restaurant;
import com.one.vo.Role;

@Repository
public interface UserDao {

	List<Pharmacy> fetchNearestPharmacies(LocationCordinates locationcordinates) throws MyException;

	List<Homes> fetchNearestHomes(int categoryId, LocationCordinates locationCordinates) throws MyException;

	List<Restaurant> fetchNearestRestaurant(LocationCordinates locationcordinates) throws MyException;

	List<Role> getAllRoles() throws MyException;

}
