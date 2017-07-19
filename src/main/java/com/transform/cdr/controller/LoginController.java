package com.transform.cdr.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.Login;
import com.transform.cdr.service.LoginService;
import com.transform.cdr.service.impl.JasperServiceImpl;

@Controller
@RequestMapping(path = "/login")
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@Autowired
	JasperServiceImpl jasperService;

	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	public Login getLoginPage() {
//		jasperService.generateReport2PDF(null);
		return new Login();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doLogin(Login login, BindingResult errors,HttpServletRequest request) {
		Login login2 = null;
		try {
			login2 = loginService.doLogin(login.getUsername());
		} catch (SuperException e) {
			Object[] args = new Object[] { e.getMessage() };
			errors.rejectValue("username",e.getMessage(), args, e.getMessage());
			return "login";

		}
		request.getSession().setAttribute("username", login2.getUsername());
		return "redirect:/company";
	}

}
