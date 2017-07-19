package com.transform.cdr.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class RequestFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestURI = httpRequest.getRequestURI();
		System.out.println(requestURI);
		String relativePath = requestURI.substring(requestURI.lastIndexOf("/") + 1, requestURI.length());
		if (!relativePath.contains(".js")) {
			if (httpRequest.getSession().getAttribute("username") != null
					&& (relativePath.equals("") || relativePath.equals("login"))) {
				request.getRequestDispatcher("/type1").forward(request, response);
				return;
			} else if (null == httpRequest.getSession().getAttribute("username")
					&& (!relativePath.equals("login") && !relativePath.contains("jsp")
							&& !relativePath.contains("css"))) {
				request.getRequestDispatcher("/login").forward(request, response);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
