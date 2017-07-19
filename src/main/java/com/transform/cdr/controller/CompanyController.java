package com.transform.cdr.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.transform.cdr.model.Company;
import com.transform.cdr.service.CompanyService;

@Controller
public class CompanyController {

	@Autowired
	CompanyService companyService;

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public String showForm(Model model) {
		model.addAttribute("company", new Company());
		model.addAttribute("listCompanies", companyService.getCompaniesList());
		return "company";
	}

	@RequestMapping(value = "/company",method = RequestMethod.POST)
	public String onSubmit(Company company,Model model, BindingResult errors, HttpServletRequest request) throws Exception {
		if (null == company.getName() || company.getName().equals("")) {
			Object[] args = new Object[] { "Name cannot be empty" };
			errors.rejectValue("name", null, args, "Name cannot be empty");
			model.addAttribute("listCompanies", companyService.getCompaniesList());
			return "company";
		}
		if (company.getId() == 0) {
			companyService.addCompany(company);
		} else {
			companyService.updateCompany(company);
		}
		return "redirect:company";
	}

	@RequestMapping(value = "/editCompany" , method=RequestMethod.GET)
	public String editCompany(@RequestParam("id") int id, Model model, HttpServletRequest request) {
		model.addAttribute("company", companyService.getCompanyById(id));
		model.addAttribute("listCompanies", companyService.getCompaniesList());
		return "company";
	}

}
