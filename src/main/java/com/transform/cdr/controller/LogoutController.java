package com.transform.cdr.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/logout")
public class LogoutController {

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:login";
	}
}
