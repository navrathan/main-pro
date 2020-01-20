package com.one.userservices;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.one.MyException;
import com.one.vo.Homes;
import com.one.vo.LocationCordinates;
import com.one.vo.Pharmacy;
import com.one.vo.Restaurant;
import com.one.vo.Role;
import com.one.vo.Sort;

@Service("userService")
public class UserService {
	@Autowired
	UserDao userDao;

	public List<Pharmacy> fetchNearestPharmacies(LocationCordinates locationcordinates) throws MyException {
		List<Pharmacy> list = userDao.fetchNearestPharmacies(locationcordinates);
		Collections.sort(list, new Sort());
		return list;
	}

	public List<Homes> fetchNearestHomes(int categoryId, LocationCordinates locationCordinates) throws MyException {
		List<Homes> list = userDao.fetchNearestHomes(categoryId, locationCordinates);
		Collections.sort(list, new Sort());
		return list;
	}

	public List<Restaurant> fetchNearestRestaurant(LocationCordinates locationcordinates) throws MyException {
		List<Restaurant> list = userDao.fetchNearestRestaurant(locationcordinates);
		Collections.sort(list, new Sort());
		return list;
	}

	public List<Role> getAllRoles() throws MyException {
		List<Role> listRoles  = userDao.getAllRoles();
		return listRoles;
	}

}
