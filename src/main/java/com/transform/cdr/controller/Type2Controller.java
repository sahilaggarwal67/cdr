package com.transform.cdr.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.ManualFields;
import com.transform.cdr.model.Ship;
import com.transform.cdr.model.Type1;
import com.transform.cdr.model.Type2RateModel;
import com.transform.cdr.model.Type2ShipModel;
import com.transform.cdr.service.Type1Service;
import com.transform.cdr.service.Type2RateService;
import com.transform.cdr.util.AccountType;
import com.transform.cdr.util.MonthEnum;

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
public class Type2Controller {

	@Autowired
	Type1Service type1Service;

	@Autowired
	Type2RateService type2RateService;

	@Autowired
	ServletContext servletContext;

	public Type2Controller() {

	}

	@RequestMapping(method = RequestMethod.GET, value = "/type2")
	public String showForm(ModelMap model, HttpServletRequest request) {
		Type1 type1 = new Type1();
		if (request.getSession().getAttribute("FILE_PATH") != null) {
			type1.setFilePath((String) request.getSession().getAttribute("FILE_PATH"));
			request.getSession().removeAttribute("FILE_PATH");
		}
		model.put("type2", type1);
		return "type2";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/type2")
	public String onSubmit(@ModelAttribute("type2") Type1 type2, BindingResult errors, HttpServletRequest request)
			throws Exception {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = (MultipartFile) multipartRequest.getFile("file");
		if (file.isEmpty()) {
			Object[] args = new Object[] { "Please select a file to upload" };
			errors.rejectValue("file", "Please select a file to upload", args, "Please select a file to upload");
			return "type2";
		}
		if (file.getOriginalFilename().split("-").length < 8) {
			Object[] args = new Object[] { "File name should be proper" };
			errors.rejectValue("file", "File name should be proper", args, "File name should be proper");
			return "type2";
		}
		String ext = (file.getOriginalFilename().split("-")[7].lastIndexOf(".") == -1 ? ""
				: (file.getOriginalFilename().split("-")[7]
						.substring(file.getOriginalFilename().split("-")[7].lastIndexOf(".") + 1)));
		if (!(ext.toUpperCase().equals("XLS") || ext.toUpperCase().equals("XLSX"))) {
			Object[] args = new Object[] { "Only xls,xlsx file allowed" };
			errors.rejectValue("file", "Only xls,xlsx file allowed", args, "Only xls,xlsx file allowed");
			return "type2";
		}
		long millInSecs = System.currentTimeMillis();
		File fileToWrite = new File(servletContext.getRealPath("/jrxml") + File.separator + millInSecs);
		FileUtils.writeByteArrayToFile(fileToWrite, file.getBytes());
		String missingImeis = null;
		try {
			missingImeis = type1Service.checkAllImsiExists(fileToWrite.getAbsolutePath());
		} catch (SuperException se) {
			Object[] args = new Object[] { se.getMessage() };
			errors.rejectValue("file", se.getMessage(), args, se.getMessage());
			if (fileToWrite.exists()) {
				fileToWrite.delete();
			}
			return "type2";
		}
		if (null != missingImeis && !missingImeis.isEmpty()) {
			Object[] args = new Object[] { "Imei not found:" + missingImeis.substring(0, missingImeis.length() - 1) };
			errors.rejectValue("file", "Imei not found:" + missingImeis.substring(0, missingImeis.length() - 1), args,
					"Imei not found:" + missingImeis.substring(0, missingImeis.length() - 1));
			if (fileToWrite.exists()) {
				fileToWrite.delete();
			}
			return "type2";
		}
		List<Ship> ship = null;
		List<ManualFields> manualFieldsList = null;
		List<Type2ShipModel> type2ShipModels = null;
		String monthStr = "";
		String yearStr = "";
		monthStr = file.getOriginalFilename().split("-")[3];
		yearStr = file.getOriginalFilename().split("-")[2];
		int month = 0;
		int year = 0;
		try {
			month = Integer.valueOf(monthStr);
			year = Integer.valueOf(yearStr);
		} catch (NumberFormatException nfe) {
			month = 0;
			year = 0;
		}
		year = month == 1 ? year - 1 : year;
		month = month == 1 ? 12 : month - 1;
		try {
			type2ShipModels = type1Service.getShipManualEntriesType2(AccountType.Type_2.getAccountCode());
		} catch (SuperException e) {
			Object[] args = new Object[] { e.getMessage() };
			errors.rejectValue("file", e.getMessage(), args, e.getMessage());
			return "type2";
		}
		/*
		 * long millInSecs = System.currentTimeMillis(); // File fileToWrite =
		 * new File(System.getenv("user.dir") + File.separator + millInSecs);
		 * File fileToWrite = new File(servletContext.getRealPath("/jrxml") +
		 * File.separator + millInSecs);
		 * FileUtils.writeByteArrayToFile(fileToWrite, file.getBytes());
		 */
		request.getSession().setAttribute("shipModels", type2ShipModels);
		// request.getSession().setAttribute("manualFields", manualFieldsList);
		request.getSession().setAttribute("fileName", file.getOriginalFilename());
		request.getSession().setAttribute("filePath", fileToWrite.getAbsolutePath());
		// request.getSession().setAttribute("month",
		// file.getOriginalFilename().split("-")[1]);
		request.getSession().setAttribute("month", getMonth(month));
		/*request.getSession().setAttribute("year",
				file.getOriginalFilename().split("-")[2].substring(0,
						(file.getOriginalFilename().split("-")[2].lastIndexOf(".") != -1
								? (file.getOriginalFilename().split("-")[2].lastIndexOf("."))
								: (file.getOriginalFilename().split("-")[2].length()))));*/
		request.getSession().setAttribute("year",year);
		List<Type2RateModel> type2RateModels = type2RateService
				.getUnlistedDescriptionRate(fileToWrite.getAbsolutePath());
		if (null == type2RateModels || type2RateModels.isEmpty()) {
			return "redirect:type2process";
		}
		request.getSession().setAttribute("type2rateList", type2RateModels);
		return "redirect:type2rate";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/type2Report")
	public void generateReport(@RequestParam("filePath") String reportPath, HttpServletResponse response)
			throws Exception {

		Path file = Paths.get(reportPath);
		if (Files.exists(file)) {
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename="
					+ (reportPath.substring(reportPath.lastIndexOf(File.separator) + 1, reportPath.length())));
			try {
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
				response.getOutputStream().close();
				Files.delete(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
	}

	private String getMonth(int month) {
		for (MonthEnum monthEnum : MonthEnum.values()) {
			if (monthEnum.getMonth() == month) {
				return monthEnum.toString();
			}
		}
		return "";
	}
}
