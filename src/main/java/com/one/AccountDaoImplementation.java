package com.one;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jongo.Jongo;
import org.jongo.MongoCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.WriteResult;
import com.one.Admin.vo.Pharmacy;
import com.one.constants.BaseResponse;
import com.one.constants.MongoDbUtil;
import com.one.vo.*;

@Repository("accountdao")

public class AccountDaoImplementation implements AccountDao {
	
	

	@Autowired
	AccountDao accountdao;
	//private HttpServletRequest request;
	public HttpSession session;

	@Override
	public BaseResponse registration(Registration registratio) throws MyException {
		BaseResponse response = new BaseResponse();
		try {

			System.out.println("db" + MongoDbUtil.getDB());
			Registration registrationUser = new Jongo(MongoDbUtil.getDB()).getCollection("registration")
					.findOne("{$or:[{email:#},{mobileNumber:#}]}", registratio.getEmail(),
							registratio.getMobileNumber())
					.as(Registration.class);

			if (registrationUser != null) {

				if (registrationUser.getMobileNumber() == registratio.getMobileNumber()) {
					response.setStatusCode(601);
					response.setStatusMessage("Mobile Number already exists");
					System.out.println("Mobile Number already exists");

				}
				if (registrationUser.getEmail().equals(registratio.getEmail())) {
					response.setStatusCode(601);
					response.setStatusMessage("Email already exists");
					System.out.println("email already exists");
				}
				return response;

			} else {
				new Jongo(MongoDbUtil.getDB()).getCollection("registration").insert(registratio);
				response.setStatusCode(200);
				response.setStatusMessage("registration successful");
				return response;

			}
		} catch (Exception e) {
			throw new MyException("exception while registration");
		}
	}

	@Override
	public Login loginUser(Login login) throws MyException {
		String password = login.getPassword();
		Login use = new Jongo(MongoDbUtil.getDB()).getCollection("registration").findOne("{password:#}", password)
				.as(Login.class);
		return use;

	}

	@Override
	public BaseResponse login(Login login) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			System.out.println(login.getEmail());
			String email = login.getEmail();
			String pass = login.getPassword();
			if (email.equals("admin") && pass.equals("1234a")) {

				response.setStatusCode(200);
				response.setStatusMessage("login successful");
				;
			} else {
				response.setStatusCode(100);
				response.setStatusMessage("login unsuccessful");
				;
			}
			return response;
		} catch (Exception e) {
			throw new MyException("exception while login");
		}

	}

	@Override
	public BaseResponse insertPharmacy(Pharmacy pharmacy) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			new Jongo(MongoDbUtil.getDB()).getCollection("pharmacyCounter").update("{ _id:1 }").with("{$inc:{seq:1}}");
			Counter counter = new Jongo(MongoDbUtil.getDB()).getCollection("pharmacyCounter").findOne("{ _id: 1}")
					.as(Counter.class);
			pharmacy.setPharmacyId(counter.getSeq());

			new Jongo(MongoDbUtil.getDB()).getCollection("pharmacyDetails").insert(pharmacy);

			response.setStatusCode(200);
			response.setStatusMessage(" pharmacy insertion successful");
			return response;
		} catch (Exception e) {
			throw new MyException("exception while inserting pharmacy");
		}

	}

	@Override
	public BaseResponse deletePharmacy(Pharmacy pharmacy) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			Pharmacy pharma = new Jongo(MongoDbUtil.getDB()).getCollection("pharmacyDetails")
					.findOne("{ pharmacyId : #}", pharmacy.getPharmacyId()).as(Pharmacy.class);
			if (pharma != null) {
				int id = pharma.getPharmacyId();
				new Jongo(MongoDbUtil.getDB()).getCollection("pharmacyDetails").remove("{pharmacyId: #}", id);
				response.setStatusCode(200);
				response.setStatusMessage("pharmacy deletion successful");
				return response;
			} else {
				response.setStatusCode(100);
				response.setStatusMessage("invalid ID or pharmacy doesnt exist");
				return response;
			}

		} catch (Exception e) {
			throw new MyException("exception while deleting pharmacy");
		}

	}

	@Override
	public BaseResponse updatePharmacy(Pharmacy pharmacy) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			Pharmacy pharma = new Jongo(MongoDbUtil.getDB()).getCollection("pharmacyDetails")
					.findOne("{ pharmacyId : #}", pharmacy.getPharmacyId()).as(Pharmacy.class);
			if (pharma != null) {
				WriteResult result = new Jongo(MongoDbUtil.getDB()).getCollection("pharmacyDetails")
						.update("{pharmacyId:# }", pharmacy.getPharmacyId()).with(pharmacy);
				System.out.println(result);

				response.setStatusCode(200);
				response.setStatusMessage("update successful");
				return response;
			} else {
				response.setStatusCode(100);
				response.setStatusMessage("invalid ID or pharmacy doesnt exist");
				return response;
			}
		} catch (Exception e) {
			throw new MyException("exception while updating pharmacy");
		}

	}

	@Override
	public List<Pharmacy> fetchNearestPharmacies(LocationCordinates locationcordinates) throws MyException {
		try {
			List<Pharmacy> list = new ArrayList<Pharmacy>();
			System.out.println("in fetch");
			double lat2 = locationcordinates.getLat();
			double lon2 = locationcordinates.getLon();
			System.out.println(lat2);
			System.out.println(lon2);

			final int R = 6371; // Radius of the earth
			MongoCursor<Pharmacy> pharmacyLocation = new Jongo(MongoDbUtil.getDB()).getCollection("pharmacyDetails")
					.find().as(Pharmacy.class);
			while (pharmacyLocation.hasNext()) {
				Pharmacy pharma = pharmacyLocation.next();
				double lon1 = pharma.getAddress().getLocationCordinates().getLon();
				double lat1 = pharma.getAddress().getLocationCordinates().getLat();
				System.out.println(lat1);
				System.out.println(lon1);
				System.out.println(pharma.getPharmacyId());

				double latDistance = Math.toRadians(lat2 - lat1);
				double lonDistance = Math.toRadians(lon2 - lon1);
				double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
						* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
				double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
				double distance = (R * c); // convert to meters
				System.out.println(distance);
				pharma.setDistance(distance);
				list.add(pharma);

			}
			return list;
		} catch (Exception e) {
			throw new MyException("exception while fetching nearest pharmacies");
		}

	}

	@Override
	public BaseResponse insertHomes(Homes homes) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			new Jongo(MongoDbUtil.getDB()).getCollection("homeCounter").update("{ _id:1 }").with("{$inc:{seq:1}}");
			Counter counter = new Jongo(MongoDbUtil.getDB()).getCollection("homeCounter").findOne("{ _id: 1}")
					.as(Counter.class);
			homes.setBuildingId(counter.getSeq());
			new Jongo(MongoDbUtil.getDB()).getCollection("homeDetails").insert(homes);
			response.setStatusCode(200);
			response.setStatusMessage("home insertion successful");
			return response;
		} catch (Exception e) {
			throw new MyException("exception while inserting homes");
		}

	}

	@Override
	public BaseResponse deleteHomes(Homes homes) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			Homes home = new Jongo(MongoDbUtil.getDB()).getCollection("homeDetails")
					.findOne("{ buildingId : #}", homes.getBuildingId()).as(Homes.class);
			if (home != null) {
				int id = home.getBuildingId();
				new Jongo(MongoDbUtil.getDB()).getCollection("homeDetails").remove("{buildingId: #}", id);
				response.setStatusCode(200);
				response.setStatusMessage("home deletion successful");
				return response;
			} else {
				response.setStatusCode(100);
				response.setStatusMessage("invalid ID or home doesnt exist");
				return response;
			}

		} catch (Exception e) {
			throw new MyException("exception while deleting homes");
		}
	}

	@Override
	public BaseResponse updateHomes(Homes homes) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			Homes home = new Jongo(MongoDbUtil.getDB()).getCollection("homeDetails")
					.findOne("{ buildingId : #}", homes.getBuildingId()).as(Homes.class);
			if (home != null) {
				WriteResult result = new Jongo(MongoDbUtil.getDB()).getCollection("homeDetails")
						.update("{buildingId:# }", homes.getBuildingId()).with(homes);
				System.out.println(result);

				response.setStatusCode(200);
				response.setStatusMessage("update successful in homes");
				return response;
			} else {
				response.setStatusCode(100);
				response.setStatusMessage("invalid ID or home doesnt exist");
				return response;
			}
		} catch (Exception e) {
			throw new MyException("exception while updating homes");
		}
	}

	@Override
	public BaseResponse insertRestaurant(Restaurant restaurant) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			new Jongo(MongoDbUtil.getDB()).getCollection("restaurantCounter").update("{ _id:1 }")
					.with("{$inc:{seq:1}}");
			Counter counter = new Jongo(MongoDbUtil.getDB()).getCollection("restaurantCounter").findOne("{ _id: 1}")
					.as(Counter.class);
			restaurant.setRestaurantId(counter.getSeq());

			new Jongo(MongoDbUtil.getDB()).getCollection("restaurantDetails").insert(restaurant);

			response.setStatusCode(200);
			response.setStatusMessage(" restaurant insertion successful");
			return response;
		} catch (Exception e) {
			throw new MyException("exception while inserting restaurant");
		}
	}

	@Override
	public BaseResponse deleteRestaurant(Restaurant restaurant) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			Restaurant rest = new Jongo(MongoDbUtil.getDB()).getCollection("restaurantDetails")
					.findOne("{ restaurantId : #}", restaurant.getRestaurantId()).as(Restaurant.class);
			if (rest != null) {
				int id = rest.getRestaurantId();
				new Jongo(MongoDbUtil.getDB()).getCollection("restaurantDetails").remove("{restaurantId: #}", id);
				response.setStatusCode(200);
				response.setStatusMessage("restaurant deletion successful");
				return response;
			} else {
				response.setStatusCode(100);
				response.setStatusMessage("invalid ID or restaurant doesnt exist");
				return response;
			}

		} catch (Exception e) {
			throw new MyException("exception while deleting restaurant");
		}
	}

	@Override
	public BaseResponse updateRestaurant(Restaurant restaurant) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			Restaurant rest = new Jongo(MongoDbUtil.getDB()).getCollection("restaurantDetails")
					.findOne("{ restaurantId : #}", restaurant.getRestaurantId()).as(Restaurant.class);
			if (rest != null) {
				WriteResult result = new Jongo(MongoDbUtil.getDB()).getCollection("restaurantDetails")
						.update("{restaurantId:# }", rest.getRestaurantId()).with(restaurant);
				System.out.println(result);

				response.setStatusCode(200);
				response.setStatusMessage("update successful in restaurant");
				return response;
			} else {
				response.setStatusCode(100);
				response.setStatusMessage("invalid ID or restaurant doesnt exist");
				return response;
			}
		} catch (Exception e) {
			throw new MyException("exception while updating restaurant");
		}
	}

	@Override
	public List<Homes> fetchNearestHomes(int categoryId) throws MyException {
		try {
			List<Homes> list = new ArrayList<Homes>();
			LocationCordinates locationcordinates=(LocationCordinates) session.getAttribute("coordinates");
			double lat2 = locationcordinates.getLat();
			double lon2 = locationcordinates.getLon();
			System.out.println(lat2);
			System.out.println(lon2);

			final int R = 6371; // Radius of the earth
			MongoCursor<Homes> pgLocation = new Jongo(MongoDbUtil.getDB()).getCollection("homeDetails")
					.find("{ categoryId : #}",categoryId ).as(Homes.class);
			if (pgLocation != null) {
				System.out.println("inside pg location =======");
			}

			while (pgLocation.hasNext()) {
				Homes pg = pgLocation.next();
				double lon1 = pg.getAddress().getLocationCordinates().getLon();
				double lat1 = pg.getAddress().getLocationCordinates().getLat();
				System.out.println(lat1);
				System.out.println(lon1);

				double latDistance = Math.toRadians(lat2 - lat1);
				double lonDistance = Math.toRadians(lon2 - lon1);
				double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
						* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
				double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
				double distance = (R * c); // convert to meters
				System.out.println(distance);
				pg.setDistance(distance);
				list.add(pg);

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
			System.out.println("in fetch");
			double lat2 = locationcordinates.getLat();
			double lon2 = locationcordinates.getLon();
			System.out.println(lat2);
			System.out.println(lon2);

			final int R = 6371; // Radius of the earth
			MongoCursor<Restaurant> restaurantLocation = new Jongo(MongoDbUtil.getDB()).getCollection("restaurantDetails")
					.find().as(Restaurant.class);
			while (restaurantLocation.hasNext()) {
				Restaurant restaurant = restaurantLocation.next();
				double lon1 = restaurant.getAddress().getLocationCordinates().getLon();
				double lat1 = restaurant.getAddress().getLocationCordinates().getLat();
				System.out.println(lat1);
				System.out.println(lon1);
				System.out.println(restaurant.getRestaurantId());

				double latDistance = Math.toRadians(lat2 - lat1);
				double lonDistance = Math.toRadians(lon2 - lon1);
				double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
						* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
				double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
				double distance = (R * c); // convert to meters
				System.out.println(distance);
				restaurant.setDistance(distance);
				list.add(restaurant);

			}
			return list;
		} catch (Exception e) {
			throw new MyException("exception while fetching nearest restaurants");
		}

	
	}

}
