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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.ManualFields;
import com.transform.cdr.model.Ship;
import com.transform.cdr.model.Type1;
import com.transform.cdr.service.Type1Service;
import com.transform.cdr.util.AccountType;

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
public class Type1Controller {

	@Autowired
	Type1Service type1Service;

	@Autowired
	ServletContext servletContext;

	public Type1Controller() {

	}

	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET, value = "/type1")
	public Type1 showForm(HttpServletRequest request) {
		Type1 type1 = new Type1();
		if (request.getSession().getAttribute("FILE_PATH") != null) {
			type1.setFilePath((String) request.getSession().getAttribute("FILE_PATH"));
			request.getSession().removeAttribute("FILE_PATH");
		}
		return type1;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/type1")
	public String onSubmit(Type1 type1, BindingResult errors, HttpServletRequest request) throws Exception {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = (MultipartFile) multipartRequest.getFile("file");
		if (file.isEmpty()) {
			Object[] args = new Object[] { "Please select a file to upload" };
			errors.rejectValue("file", "Please select a file to upload", args, "Please select a file to upload");
			return "type1";
		}
		if (file.getOriginalFilename().split("-").length < 3) {
			Object[] args = new Object[] { "File name should be proper" };
			errors.rejectValue("file", "File name should be proper", args, "File name should be proper");
			return "type1";
		}
		String ext = (file.getOriginalFilename().split("-")[2].lastIndexOf(".") == -1 ? ""
				: (file.getOriginalFilename().split("-")[2]
						.substring(file.getOriginalFilename().split("-")[2].lastIndexOf(".") + 1)));
		if (!ext.toUpperCase().equals("TXT")) {
			Object[] args = new Object[] { "Only Txt file allowed" };
			errors.rejectValue("file", "Only Txt file allowed", args, "Only Txt file allowed");
			return "type1";
		}
		Ship ship = null;
		List<ManualFields> manualFields = null;
		List data = null;
		try {
			data = type1Service.getShipManualEntries(file.getOriginalFilename().split("-")[0].trim(),
					AccountType.Type_1.getAccountCode());
			ship = (Ship) data.get(0);
			manualFields = (List<ManualFields>) data.get(1);
		} catch (SuperException e) {
			Object[] args = new Object[] { e.getMessage() };
			errors.rejectValue("file", e.getMessage(), args, e.getMessage());
			return "type1";
		}
		long millInSecs = System.currentTimeMillis();
		File fileToWrite = new File(servletContext.getRealPath("/jrxml") + File.separator + millInSecs);
		// File fileToWrite = new File(System.getenv("user.dir") +
		// File.separator + millInSecs);
		FileUtils.writeByteArrayToFile(fileToWrite, file.getBytes());
		request.getSession().setAttribute("ship", ship);
		request.getSession().setAttribute("manualFields", manualFields);
		request.getSession().setAttribute("fileName", file.getOriginalFilename());
		request.getSession().setAttribute("filePath", fileToWrite.getAbsolutePath());
		request.getSession().setAttribute("month", file.getOriginalFilename().split("-")[1]);
		request.getSession().setAttribute("year",
				file.getOriginalFilename().split("-")[2].substring(0,
						(file.getOriginalFilename().split("-")[2].lastIndexOf(".") != -1
								? (file.getOriginalFilename().split("-")[2].lastIndexOf("."))
								: (file.getOriginalFilename().split("-")[2].length()))));
		return "redirect:type1process";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/type1Report")
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
}
