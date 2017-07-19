package com.transform.cdr.service;

import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.Login;

public interface LoginService {

	public Login doLogin(String username) throws SuperException;

}
