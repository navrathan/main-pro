package com.one.userservices;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jongo.Jongo;
import org.jongo.MongoCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.one.MyException;
import com.one.constants.Constants;
import com.one.constants.MongoDbUtil;
import com.one.vo.Homes;
import com.one.vo.LocationCordinates;
import com.one.vo.Pharmacy;
import com.one.vo.Restaurant;
import com.one.vo.Role;

@Repository("userDao")
public class UserDaoImplementation implements UserDao {
	@Autowired
	UserDao userDao;

	@Override
	public List<Pharmacy> fetchNearestPharmacies(LocationCordinates locationcordinates) throws MyException {
		try {
			List<Pharmacy> list = new ArrayList<Pharmacy>();
			MongoCursor<Pharmacy> pharmacyLocation = new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getPharmacy())
					.find().as(Pharmacy.class);
			while (pharmacyLocation.hasNext()) {
				Pharmacy pharma = pharmacyLocation.next();
				pharma.setDistance(FindDistance.findDistance(locationcordinates.getLat(), locationcordinates.getLon(),
						pharma.getAddress().getLocationCordinates().getLat(),
						pharma.getAddress().getLocationCordinates().getLon()));
				list.add(pharma);
			}
			return list;
		} catch (Exception e) {
			throw new MyException("exception while fetching nearest pharmacies", e);
		}

	}

	@Override
	public List<Homes> fetchNearestHomes(int categoryId, LocationCordinates locationCordinates) throws MyException {
		try {
			List<Homes> list = new ArrayList<Homes>();
			MongoCursor<Homes> pgLocation = new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getHomes())
					.find("{ categoryId : #}", categoryId).as(Homes.class);
			if (pgLocation != null) {
				while (pgLocation.hasNext()) {
					Homes pg = pgLocation.next();
					pg.setDistance(FindDistance.findDistance(locationCordinates.getLat(), locationCordinates.getLon(),
							pg.getAddress().getLocationCordinates().getLat(),
							pg.getAddress().getLocationCordinates().getLon()));
					list.add(pg);
				}
			}
			return list;
		} catch (Exception e) {
			throw new MyException("exception while fetching nearest PG's");
		}

	}

	@Override
	public List<Restaurant> fetchNearestRestaurant(LocationCordinates locationcordinates) throws MyException {

		try {
			List<Restaurant> list = new ArrayList<Restaurant>();
			MongoCursor<Restaurant> restaurantLocation = new Jongo(MongoDbUtil.getDB())
					.getCollection(Constants.getRestaurant()).find().as(Restaurant.class);
			while (restaurantLocation.hasNext()) {
				Restaurant restaurant = restaurantLocation.next();
				restaurant.setDistance(FindDistance.findDistance(locationcordinates.getLat(),
						locationcordinates.getLon(), restaurant.getAddress().getLocationCordinates().getLat(),
						restaurant.getAddress().getLocationCordinates().getLon()));
				list.add(restaurant);

			}
			return list;
		} catch (Exception e) {
			throw new MyException("exception while fetching nearest restaurants", e);
		}

	}

	@Override
	public List<Role> getAllRoles() throws MyException {
		Iterator<Role> itr = null;
		List<Role> listOfRoles = new ArrayList<Role>();
		try {
			itr = new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getRolesdetails()).aggregate("{$match:{}}").as(Role.class)
					.iterator();
			while (itr.hasNext()) {
				Role role = itr.next();
				listOfRoles.add(role);

			}
		} catch (Exception e) {
			throw new MyException("Exception Occured Wille Fetching The All Roles ", e);
		}
		return listOfRoles;
	}

}
