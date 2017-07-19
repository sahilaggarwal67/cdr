package com.transform.cdr.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.transform.cdr.model.ManualFields;

public interface ManualFieldsDao extends JpaRepository<ManualFields, Integer> {

	/*
	 * @Query(
	 * "SELECT s from ShipManualEntries s where s.shipId=:shipId and s.applicableDate=(select max(s1.applicableDate) from ShipManualEntries s1 where s1.shipId=s.shipId)"
	 * ) public List<ShipManualEntries> findByShipId(@Param("shipId") int
	 * shipId);
	 */

	public List<ManualFields> findByAccountType(int accountType);
}
