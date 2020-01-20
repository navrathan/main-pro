package com.one;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.one.constants.BaseResponse;
import com.one.vo.Login;
import com.one.vo.Registration;

@RestController
@RequestMapping("/account")

public class AccountController {
	@Autowired
	AccountService accountService;

	private static final Logger logger = Logger.getLogger(AccountController.class);

	@RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse registrtion(@RequestBody Registration registration) {
		try {
			if (registration != null && registration.getEmail() != null && !registration.getEmail().isEmpty()) {
				BaseResponse baseresponse = accountService.registration(registration);
				return baseresponse;
			}
		} catch (MyException e) {
			logger.error(" exception while registration", e);
		}
		return new BaseResponse();
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public Login loginUser(@RequestBody Login login) {
		try {
			if (login != null && login.getEmail() != null && login.getPassword() != null && !login.getEmail().isEmpty()
					&& !login.getPassword().isEmpty()) {
				Login resp = accountService.loginUser(login);
				return resp;
			}
		} catch (MyException e) {
			logger.error("exception while login user", e);
		}
		return new Login();
	}

}
