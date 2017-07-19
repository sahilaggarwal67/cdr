package com.transform.cdr.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.ManualFields;
import com.transform.cdr.model.Ship;
import com.transform.cdr.model.Type1ProcessModel;
import com.transform.cdr.model.Type1process;
import com.transform.cdr.model.Type2RateModel;
import com.transform.cdr.model.Type2RateParentModel;
import com.transform.cdr.service.Type1Service;
import com.transform.cdr.service.Type2RateService;

/**
 * Controller class to upload Files.
 * <p/>
 * <p>
 * <a href="FileUploadFormController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/type2rate")
public class Type2RateController {

	@Autowired
	Type2RateService type2RateService;

	public Type2RateController() {

	}

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(ModelMap model, HttpServletRequest request) {
		Type2RateParentModel type2RateParentModel = new Type2RateParentModel();
		type2RateParentModel.setRateList((List<Type2RateModel>) request.getSession().getAttribute("type2rateList"));
		model.put("type2rateModels", type2RateParentModel);
		request.getSession().removeAttribute("type2rateList");
		return "type2rate";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Type2RateParentModel type2rateModels) throws SuperException {
		type2RateService.saveType2Rate(type2rateModels.getRateList());
		return "redirect:type2process";
	}
}
