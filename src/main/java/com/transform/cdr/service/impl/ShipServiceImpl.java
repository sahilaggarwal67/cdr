package com.transform.cdr.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transform.cdr.dao.ShipDao;
import com.transform.cdr.model.Ship;
import com.transform.cdr.service.ShipService;
import com.transform.cdr.util.AccountType;

@Service("shipService")
public class ShipServiceImpl implements ShipService {

	@Autowired
	ShipDao shipDao;

	public void addShip(Ship ship) {
		shipDao.save(ship);
	}

	public List<Ship> getShipList(int companyId) {
		List<Ship> shipList = shipDao.findByCompanyId(companyId);
		if (null == shipList || shipList.isEmpty()) {
			return shipList;
		}
		for (Ship ship : shipList) {
			ship.setShipType(getAccountType(ship.getAccountType()));
		}
		Collections.sort(shipList, new Comparator<Ship>() {

			@Override
			public int compare(Ship o1, Ship o2) {
				// TODO Auto-generated method stub
				return o1.getName().toUpperCase().compareTo(o2.getName().toUpperCase());
			}
		});
		return shipList;
	}

	public void updateShip(Ship ship) {
		shipDao.save(ship);
	}

	public Ship getShipById(int id) {
		return shipDao.findById(id);
	}

	private String getAccountType(int accountType) {
		return AccountType.Type_1.getAccountCode() == accountType ? AccountType.Type_1.getAccountType()
				: (AccountType.Type_2.getAccountCode() == accountType ? AccountType.Type_2.getAccountType()
						: (AccountType.CITADEL.getAccountCode() == accountType ? AccountType.CITADEL.getAccountType()
								: AccountType.FULL.getAccountType()));
	}

}
