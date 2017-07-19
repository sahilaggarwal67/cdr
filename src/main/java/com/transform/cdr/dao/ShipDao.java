package com.transform.cdr.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.transform.cdr.model.Ship;

public interface ShipDao extends CrudRepository<Ship, Integer> {

	public Ship findByName(String name);

	public List<Ship> findByCompanyId(int companyId);

	public Ship findById(int id);

	public Ship findByNameAndAccountType(String name, int accountType);

	public List<Ship> findByAccountType(int accountType);

	public Ship findByMappingNameAndAccountType(String mappingName, int accountType);

}
