package com.one.Admin;

import org.jongo.Jongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.one.MyException;
import com.one.vo.Pharmacy;
import com.one.constants.BaseResponse;
import com.one.constants.Constants;
import com.one.constants.MongoDbUtil;
import com.one.vo.Counter;
import com.one.vo.Homes;
import com.one.vo.Restaurant;

@Repository("admindao")
public class AdminDaoImplementation implements AdminDao {
	@Autowired
	AdminDao admindao;

	@Override
	public BaseResponse insertPharmacy(Pharmacy pharmacy) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getPharmacycounter()).update("{ _id:1 }")
					.with("{$inc:{seq:1}}");
			Counter counter = new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getPharmacycounter())
					.findOne("{ _id: 1}").as(Counter.class);
			pharmacy.setPharmacyId(counter.getSeq());

			new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getPharmacy()).insert(pharmacy);
			response.setStatusCode(200);
			response.setStatusMessage(" pharmacy insertion successful");
			return response;
		} catch (Exception e) {
			throw new MyException("exception while inserting pharmacy", e);
		}

	}

	@Override
	public BaseResponse deletePharmacy(Pharmacy pharmacy) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			Pharmacy pharma = new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getPharmacy())
					.findOne("{ pharmacyId : #}", pharmacy.getPharmacyId()).as(Pharmacy.class);
			if (pharma != null) {
				new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getPharmacy()).remove("{pharmacyId: #}",
						pharma.getPharmacyId());
				response.setStatusCode(200);
				response.setStatusMessage("pharmacy deletion successful");
				return response;
			} else {
				response.setStatusCode(100);
				response.setStatusMessage("invalid ID or pharmacy doesnt exist");
				return response;
			}

		} catch (Exception e) {
			throw new MyException("exception while deleting pharmacy", e);
		}

	}

	@Override
	public BaseResponse updatePharmacy(Pharmacy pharmacy) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			Pharmacy pharma = new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getPharmacy())
					.findOne("{ pharmacyId : #}", pharmacy.getPharmacyId()).as(Pharmacy.class);
			if (pharma != null) {
				new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getPharmacy())
						.update("{pharmacyId:# }", pharmacy.getPharmacyId()).with(pharmacy);
				response.setStatusCode(200);
				response.setStatusMessage("update successful");
				return response;
			} else {
				response.setStatusCode(100);
				response.setStatusMessage("invalid ID or pharmacy doesnt exist");
				return response;
			}
		} catch (Exception e) {
			throw new MyException("exception while updating pharmacy", e);
		}

	}

	@Override
	public BaseResponse insertHomes(Homes homes) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getHomescounter()).update("{ _id:1 }")
					.with("{$inc:{seq:1}}");
			Counter counter = new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getHomescounter())
					.findOne("{ _id: 1}").as(Counter.class);
			homes.setBuildingId(counter.getSeq());
			new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getHomes()).insert(homes);
			response.setStatusCode(200);
			response.setStatusMessage("home insertion successful");
			return response;
		} catch (Exception e) {
			throw new MyException("exception while inserting homes", e);
		}

	}

	@Override
	public BaseResponse deleteHomes(Homes homes) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			Homes home = new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getHomes())
					.findOne("{ buildingId : #}", homes.getBuildingId()).as(Homes.class);
			if (home != null) {
				int id = home.getBuildingId();
				new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getHomes()).remove("{buildingId: #}", id);
				response.setStatusCode(200);
				response.setStatusMessage("home deletion successful");
				return response;
			} else {
				response.setStatusCode(100);
				response.setStatusMessage("invalid ID or home doesnt exist");
				return response;
			}

		} catch (Exception e) {
			throw new MyException("exception while deleting homes", e);
		}
	}

	@Override
	public BaseResponse updateHomes(Homes homes) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			Homes home = new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getHomes())
					.findOne("{ buildingId : #}", homes.getBuildingId()).as(Homes.class);
			if (home != null) {
				new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getHomes())
						.update("{buildingId:# }", homes.getBuildingId()).with(homes);
				response.setStatusCode(200);
				response.setStatusMessage("update successful in homes");
				return response;
			} else {
				response.setStatusCode(100);
				response.setStatusMessage("invalid ID or home doesnt exist");
				return response;
			}
		} catch (Exception e) {
			throw new MyException("exception while updating homes", e);
		}
	}

	@Override
	public BaseResponse insertRestaurant(Restaurant restaurant) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getRestaurantcounter()).update("{ _id:1 }")
					.with("{$inc:{seq:1}}");
			Counter counter = new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getRestaurantcounter())
					.findOne("{ _id: 1}").as(Counter.class);
			restaurant.setRestaurantId(counter.getSeq());
			new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getRestaurant()).insert(restaurant);
			response.setStatusCode(200);
			response.setStatusMessage(" restaurant insertion successful");
			return response;
		} catch (Exception e) {
			throw new MyException("exception while inserting restaurant", e);
		}
	}

	@Override
	public BaseResponse deleteRestaurant(Restaurant restaurant) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			Restaurant rest = new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getRestaurant())
					.findOne("{ restaurantId : #}", restaurant.getRestaurantId()).as(Restaurant.class);
			if (rest != null) {
				int id = rest.getRestaurantId();
				new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getRestaurant()).remove("{restaurantId: #}",
						id);
				response.setStatusCode(200);
				response.setStatusMessage("restaurant deletion successful");
				return response;
			} else {
				response.setStatusCode(100);
				response.setStatusMessage("invalid ID or restaurant doesnt exist");
				return response;
			}

		} catch (Exception e) {
			throw new MyException("exception while deleting restaurant", e);
		}
	}

	@Override
	public BaseResponse updateRestaurant(Restaurant restaurant) throws MyException {
		try {
			BaseResponse response = new BaseResponse();
			Restaurant rest = new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getRestaurant())
					.findOne("{ restaurantId : #}", restaurant.getRestaurantId()).as(Restaurant.class);
			if (rest != null) {
				new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getRestaurant())
						.update("{restaurantId:# }", rest.getRestaurantId()).with(restaurant);
				response.setStatusCode(200);
				response.setStatusMessage("update successful in restaurant");
				return response;
			} else {
				response.setStatusCode(100);
				response.setStatusMessage("invalid ID or restaurant doesnt exist");
				return response;
			}
		} catch (Exception e) {
			throw new MyException("exception while updating restaurant", e);
		}
	}

}
