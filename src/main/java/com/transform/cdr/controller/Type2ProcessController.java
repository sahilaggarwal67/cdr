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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.ManualFields;
import com.transform.cdr.model.Ship;
import com.transform.cdr.model.Type1ProcessModel;
import com.transform.cdr.model.Type1process;
import com.transform.cdr.model.Type2ShipModel;
import com.transform.cdr.service.Type1Service;

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
@RequestMapping("/type2process")
public class Type2ProcessController {

	@Autowired
	Type1Service type1Service;

	public Type2ProcessController() {

	}

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(ModelMap model, HttpServletRequest request) {
		Type1process type1process = new Type1process();		
		type1process.setShipModels((List<Type2ShipModel>)request.getSession().getAttribute("shipModels"));
//		request.getSession().removeAttribute("manualFields");
		request.getSession().removeAttribute("shipModels");
		model.addAttribute("type2process", type1process);
		return "type2process";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Type1process type2process, BindingResult errors, HttpServletRequest request,
			HttpServletResponse response) throws SuperException {
		String fileName = (String) request.getSession().getAttribute("fileName");
		String filePath = (String) request.getSession().getAttribute("filePath");
		String month = (String) request.getSession().getAttribute("month");
		Integer year = (Integer) request.getSession().getAttribute("year");
		Type1ProcessModel type1ProcessModel = new Type1ProcessModel();
		type1ProcessModel.setFileName(fileName);
		type1ProcessModel.setFilePath(filePath);
		type1ProcessModel.setMonth(month);
		type1ProcessModel.setYear(String.valueOf(year));
		type1ProcessModel.setType1process(type2process);
		type1ProcessModel.setUsername((String) request.getSession().getAttribute("username"));
		String reportPath = null;
		try {
			reportPath = type1Service.saveReportDataType2(type1ProcessModel);
		} catch (SuperException e) {
			throw e;
		}
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
		request.getSession().removeAttribute("fileName");
		request.getSession().removeAttribute("filePath");
		request.getSession().setAttribute("FILE_PATH",reportPath);
		return "redirect:type2";
	}
}
