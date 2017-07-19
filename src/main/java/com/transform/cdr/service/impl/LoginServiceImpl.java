package com.transform.cdr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transform.cdr.dao.LoginDao;
import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.Login;
import com.transform.cdr.service.LoginService;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDao loginDao;

	public LoginServiceImpl() {

	}

	public Login doLogin(String username) throws SuperException {
		if (null == username || username.isEmpty()) {
			throw new SuperException("Username can not be blank");
		}
		Login login = loginDao.findByUsername(username);
		if (null == login) {
			throw new SuperException("User not found");
		}
		return login;
	}

}
