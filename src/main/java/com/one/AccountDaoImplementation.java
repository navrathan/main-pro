package com.one;

import java.util.Iterator;
import org.jongo.Jongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.one.constants.BaseResponse;
import com.one.constants.Constants;
import com.one.constants.MongoDbUtil;
import com.one.vo.*;

@Repository("accountdao")
public class AccountDaoImplementation implements AccountDao {

	@Autowired
	AccountDao accountdao;

	@Override
	public BaseResponse registration(Registration registratio) throws MyException {
		BaseResponse response = new BaseResponse();
		try {
			Registration registrationUser = new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getRegistration())
					.findOne("{$or:[{email:#},{mobileNumber:#}]}", registratio.getEmail(),
							registratio.getMobileNumber())
					.as(Registration.class);

			if (registrationUser != null) {

				if (registrationUser.getMobileNumber() == registratio.getMobileNumber()) {
					response.setStatusCode(601);
					response.setStatusMessage("Mobile Number already exists");

				}
				if (registrationUser.getEmail().equals(registratio.getEmail())) {
					response.setStatusCode(601);
					response.setStatusMessage("Email already exists");
				}
				return response;

			} else {
				new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getRegistrationcounter()).update("{ _id:1 }")
						.with("{$inc:{seq:1}}");
				Counter counter = new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getRegistrationcounter())
						.findOne("{ _id: 1}").as(Counter.class);
				registratio.setUserId(counter.getSeq());
				new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getRegistration()).insert(registratio);
				response.setStatusCode(200);
				response.setStatusMessage("registration successful");
				return response;
			}
		} catch (Exception e) {
			throw new MyException("exception while registration", e);
		}
	}

	@Override
	public Registration loginUser(Login login) throws MyException {
		Registration use = new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getRegistration())
				.findOne("{$and:[{password:#},{email:#}]}", login.getPassword(), login.getEmail())
				.as(Registration.class);
		return use;
	}

	@Override
	public UserPermissions getUserAndAdminRoles(int roleId) throws MyException {
		Iterator<UserPermissions> itr = null;
		UserPermissions userPermissions = new UserPermissions();
		try {
			itr = new Jongo(MongoDbUtil.getDB()).getCollection(Constants.getRolesdetails()).aggregate("{$match:{roleId:#}}", roleId)
					.and("{$group:{_id:null,permissionList: { $first: '$permissions'},roleIdList: { $push: '$roleId' }}}")
					.as(UserPermissions.class).iterator();
			if (itr.hasNext()) {
				userPermissions = itr.next();
			}
		} catch (Exception e) {
			throw new MyException("Exception Occured While Fetching The All Roles ", e);
		}
		return userPermissions;
	}
}
