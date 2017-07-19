package com.transform.cdr.dao;

import org.springframework.data.repository.CrudRepository;

import com.transform.cdr.model.Login;

public interface LoginDao extends CrudRepository<Login, Integer>{

	public Login findByUsername(String username);

}
