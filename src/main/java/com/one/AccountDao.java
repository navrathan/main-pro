package com.one;

import org.springframework.stereotype.Repository;

import com.one.constants.BaseResponse;
import com.one.vo.Login;
import com.one.vo.Registration;
import com.one.vo.UserPermissions;

@Repository
public interface AccountDao {
	BaseResponse registration(Registration registration) throws MyException;

	Registration loginUser(Login login) throws MyException;

	UserPermissions getUserAndAdminRoles(int roleId) throws MyException;
}
