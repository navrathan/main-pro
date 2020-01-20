package com.one;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.one.constants.BaseResponse;
import com.one.security.JwtValidator;
import com.one.security.vo.JwtUser;
import com.one.security.Encrypt;
import com.one.vo.Login;
import com.one.vo.Permission;
import com.one.vo.Registration;
import com.one.vo.UserPermissions;

@Service("accountService")
public class AccountService {
	@Autowired
	AccountDao accountdao;
	@Autowired
	private JwtValidator jwtValidator;

	public BaseResponse registration(Registration registration) throws MyException {
		registration.setFullName(registration.getFirstName() + " " + registration.getLastName());
		registration.setPassword(Encrypt.passwordEncyption(registration.getPassword()));
		BaseResponse baseresponse = accountdao.registration(registration);
		return baseresponse;
	}

	public Login loginUser(Login login) throws MyException {
		Login resp = new Login();
		login.setPassword(Encrypt.passwordEncyption(login.getPassword()));
		Registration user = accountdao.loginUser(login);
		if (user != null) {
			UserPermissions userPermissions = accountdao.getUserAndAdminRoles(101);
			Set<String> permissionSet = new HashSet<>();
			for (Permission permission : userPermissions.getPermissionList()) {
				permissionSet.add(permission.getPermissionName());
			}
			JwtUser jwtUser = new JwtUser();
			jwtUser.setUsername(user.getEmail());
			jwtUser.setUserId(user.getUserId());
			jwtUser.setUserType(JwtUser.USER);
			jwtUser.setRoles(101 + "");
			String sessionToken = jwtValidator.generate(jwtUser);
			resp.setSessionToken(sessionToken);
			resp.setUserId(user.getUserId());
			resp.setStatusCode(200);
			resp.setStatusMessage("login successfull");
			return resp;
		} else {
			resp.setStatusCode(100);
			resp.setStatusMessage("login unsuccessfull");
			return resp;
		}
	}

}
