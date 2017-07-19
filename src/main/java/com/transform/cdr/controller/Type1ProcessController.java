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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.ManualFields;
import com.transform.cdr.model.Ship;
import com.transform.cdr.model.Type1ProcessModel;
import com.transform.cdr.model.Type1process;
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
@RequestMapping("/type1process")
public class Type1ProcessController {

	@Autowired
	Type1Service type1Service;

	public Type1ProcessController() {

	}

	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	public Type1process showForm(HttpServletRequest request) {
		Type1process type1process = new Type1process();
		type1process.setEntries((List<ManualFields>) request.getSession().getAttribute("manualFields"));
		type1process.setShip((Ship) request.getSession().getAttribute("ship"));
		request.getSession().removeAttribute("manualFields");
		request.getSession().removeAttribute("ship");
		return type1process;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Type1process type1process, BindingResult errors, HttpServletRequest request) throws SuperException {
		String fileName = (String) request.getSession().getAttribute("fileName");
		String filePath = (String) request.getSession().getAttribute("filePath");
		String month = (String) request.getSession().getAttribute("month");
		String year = (String) request.getSession().getAttribute("year");
		Type1ProcessModel type1ProcessModel = new Type1ProcessModel();
		type1ProcessModel.setFileName(fileName);
		type1ProcessModel.setFilePath(filePath);
		type1ProcessModel.setType1process(type1process);
		type1ProcessModel.setUsername((String) request.getSession().getAttribute("username"));
		type1ProcessModel.setMonth(month);
		type1ProcessModel.setYear(year);
		String reportPath = null;
		try {
			reportPath = type1Service.saveReportData(type1ProcessModel);
		} catch (SuperException e) {
			throw e;
		}
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
		request.getSession().removeAttribute("fileName");
		request.getSession().removeAttribute("filePath");
		request.getSession().setAttribute("FILE_PATH", reportPath);
		return "redirect:type1";
	}
}
