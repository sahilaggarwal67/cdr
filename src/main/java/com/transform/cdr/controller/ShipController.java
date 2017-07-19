package com.transform.cdr.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.transform.cdr.model.Company;
import com.transform.cdr.model.Ship;
import com.transform.cdr.model.ShipControllerModel;
import com.transform.cdr.service.CompanyService;
import com.transform.cdr.service.ShipService;
import com.transform.cdr.util.AccountType;

@Controller
public class ShipController {

	@Autowired
	CompanyService companyService;

	@Autowired
	ShipService shipService;

	@RequestMapping(value = "/ship", method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {
		ShipControllerModel shipControllerModel = new ShipControllerModel();
		shipControllerModel.setShip(new Ship());
		List<Company> companiesList = getCompaniesList();
		shipControllerModel.setCompanies(companiesList);
		int companyId = request.getSession().getAttribute("shipCompanyId") != null
				? (Integer) request.getSession().getAttribute("shipCompanyId") : companiesList.get(0).getId();
		shipControllerModel.setShipList(shipService.getShipList(companyId));
		shipControllerModel.setSelectedCompany(companyId);
		shipControllerModel.setAccountTypes(getAccountTypes());
		model.addAttribute("shipModel", shipControllerModel);
		request.getSession().removeAttribute("shipCompanyId");
		return "ship";
	}

	@RequestMapping(value = "/ship", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("shipModel") ShipControllerModel shipControllerModel, Model model,
			BindingResult errors, HttpServletRequest request) throws Exception {
		if (null == shipControllerModel.getShip().getName() || shipControllerModel.getShip().getName().equals("")) {
			Object[] args = new Object[] { "Name cannot be empty" };
			errors.rejectValue("ship.name", null, args, "Name cannot be empty");
			shipControllerModel.setCompanies(getCompaniesList());
			shipControllerModel.setShipList(shipService.getShipList(shipControllerModel.getSelectedCompany()));
			shipControllerModel.setAccountTypes(getAccountTypes());
			model.addAttribute("shipModel", shipControllerModel);
			return "ship";
		}
		if ((null == shipControllerModel.getShip().getName() || shipControllerModel.getShip().getName().equals(""))
				&& (shipControllerModel.getShip().getAccountType() == AccountType.CITADEL.getAccountCode()
						|| shipControllerModel.getShip().getAccountType() == AccountType.FULL.getAccountCode())) {
			Object[] args = new Object[] { "Mapping Name cannot be empty" };
			errors.rejectValue("ship.mappingName", null, args, "Mapping Name cannot be empty");
			shipControllerModel.setCompanies(getCompaniesList());
			shipControllerModel.setShipList(shipService.getShipList(shipControllerModel.getSelectedCompany()));
			shipControllerModel.setAccountTypes(getAccountTypes());
			model.addAttribute("shipModel", shipControllerModel);
			return "ship";
		}
		Ship ship = shipControllerModel.getShip();
		ship.setCompanyId(shipControllerModel.getSelectedCompany());
		if (shipControllerModel.getShip().getAccountType() == AccountType.Type_1.getAccountCode()) {
			/*
			 * ship.setPrice1(0); ship.setPrice2(0);
			 */
			ship.setImsi1("");
			ship.setImsi2("");
			ship.setMappingName("");
			ship.setFeeAndStaticCharge(0);
		} else if (shipControllerModel.getShip().getAccountType() == AccountType.Type_2.getAccountCode()) {
			/*
			 * ship.setVoiceToTerrestial(0); ship.setVoiceToCellular(0);
			 */
			ship.setIridiumCitadelMonthlyFee(0);
			ship.setMappingName("");
			ship.setFeeAndStaticCharge(0);
		} else if (shipControllerModel.getShip().getAccountType() == AccountType.CITADEL.getAccountCode()
				|| shipControllerModel.getShip().getAccountType() == AccountType.FULL.getAccountCode()) {
			ship.setVoiceToTerrestial(0);
			ship.setVoiceToCellular(0);
			ship.setIridiumCitadelMonthlyFee(0);
			ship.setVoiceRebate(0);
			ship.setDataRebate(0);
			/*
			 * ship.setPrice1(0); ship.setPrice2(0);
			 */
			ship.setImsi1("");
			ship.setImsi2("");
		}
		if (ship.getId() == 0) {
			shipService.addShip(ship);
		} else {
			shipService.updateShip(ship);
		}
		request.getSession().setAttribute("shipCompanyId", shipControllerModel.getSelectedCompany());
		return "redirect:ship";
	}

	@RequestMapping(value = "/editShip", method = RequestMethod.GET)
	public String editCompany(@RequestParam("id") int id,
			@ModelAttribute("shipModel") ShipControllerModel shipControllerModel, Model model,
			HttpServletRequest request) {
		// ShipControllerModel shipControllerModel = new ShipControllerModel();
		shipControllerModel.setShip(shipService.getShipById(id));
		shipControllerModel.setCompanies(getCompaniesList());
		shipControllerModel.setShipList(shipService.getShipList(shipControllerModel.getShip().getCompanyId()));
		shipControllerModel.setSelectedCompany(shipControllerModel.getShip().getCompanyId());
		shipControllerModel.setAccountTypes(getAccountTypes());
		model.addAttribute("shipModel", shipControllerModel);
		return "ship";
	}

	@RequestMapping(value = "/changeCompany", method = RequestMethod.GET)
	public String changeCompany(@RequestParam("companyId") int companyId, Model model) {
		ShipControllerModel shipControllerModel = new ShipControllerModel();
		shipControllerModel.setShip(new Ship());
		shipControllerModel.setCompanies(getCompaniesList());
		shipControllerModel.setShipList(shipService.getShipList(companyId));
		shipControllerModel.setSelectedCompany(companyId);
		shipControllerModel.setAccountTypes(getAccountTypes());
		model.addAttribute("shipModel", shipControllerModel);
		return "ship";
	}

	public List<Company> getCompaniesList() {
		List<Company> companyList = companyService.getCompaniesList();
		if (null == companyList || companyList.isEmpty()) {
			companyList = new ArrayList<Company>();
			Company company = new Company(0, "");
			companyList.add(company);
			return companyList;
		}
		return companyList;
	}

	public List<AccountType> getAccountTypes() {
		return Arrays.asList(AccountType.values());
	}

}
