package com.one.Admin;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.one.AccountDao;
import com.one.MyException;
import com.one.vo.Pharmacy;
import com.one.constants.BaseResponse;
import com.one.constants.Constants;
import com.one.security.JwtValidator;
import com.one.security.vo.JwtUser;
import com.one.vo.Admin;
import com.one.vo.Homes;
import com.one.vo.Login;
import com.one.vo.Permission;
import com.one.vo.Restaurant;
import com.one.vo.UserPermissions;

@Service("adminService")
public class AdminService {
	@Autowired
	AdminDao admindao;
	@Autowired
	AccountDao accountdao;
	@Autowired
	JwtValidator jwtValidator;

	public Admin login(Login login) throws MyException {
		try {
			Admin admin = new Admin();
			if (login.getEmail().equals(Constants.getAdminname())
					&& login.getPassword().equals(Constants.getAdminpass())) {
				UserPermissions userPermissions = accountdao.getUserAndAdminRoles(100);
				Set<String> permissionSet = new HashSet<>();
				for (Permission permission : userPermissions.getPermissionList()) {
					permissionSet.add(permission.getPermissionName());
				}
				JwtUser jwtUser = new JwtUser();
				jwtUser.setUsername("admin");
				jwtUser.setUserId(786);
				jwtUser.setUserType(JwtUser.ADMIN);
				jwtUser.setRoles(100 + "");

				String sessionToken = jwtValidator.generate(jwtUser);
				admin.setSessionToken(sessionToken);
				admin.setStatusCode(200);
				admin.setStatusMessage("login successful");
			} else {
				admin.setStatusCode(100);
				admin.setStatusMessage("login unsuccessful");
			}
			return admin;
		} catch (Exception e) {
			throw new MyException("exception while admin login", e);
		}
	}

	public BaseResponse insertPharmacy(Pharmacy pharmacy) throws MyException {
		BaseResponse response = admindao.insertPharmacy(pharmacy);
		return response;
	}

	public BaseResponse deletePharmacy(Pharmacy pharmacy) throws MyException {
		BaseResponse response = admindao.deletePharmacy(pharmacy);
		return response;

	}

	public BaseResponse updatePharmacy(Pharmacy pharmacy) throws MyException {
		BaseResponse response = admindao.updatePharmacy(pharmacy);
		return response;
	}

	public BaseResponse insertHomes(Homes homes) throws MyException {
		BaseResponse response = admindao.insertHomes(homes);
		return response;
	}

	public BaseResponse deleteHomes(Homes homes) throws MyException {
		BaseResponse response = admindao.deleteHomes(homes);
		return response;
	}

	public BaseResponse updateHomes(Homes homes) throws MyException {
		BaseResponse response = admindao.updateHomes(homes);
		return response;
	}

	public BaseResponse insertRestaurant(Restaurant restaurant) throws MyException {
		BaseResponse response = admindao.insertRestaurant(restaurant);
		return response;
	}

	public BaseResponse deleteRestaurant(Restaurant restaurant) throws MyException {
		BaseResponse response = admindao.deleteRestaurant(restaurant);
		return response;
	}

	public BaseResponse updateRestaurant(Restaurant restaurant) throws MyException {
		BaseResponse response = admindao.updateRestaurant(restaurant);
		return response;
	}

}
