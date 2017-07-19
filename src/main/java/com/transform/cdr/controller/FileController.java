package com.transform.cdr.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.File;
import com.transform.cdr.service.FileUploadService;
import com.transform.cdr.service.Type1Service;
import com.transform.cdr.util.Type;

@Controller
public class FileController {

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	Type1Service type1Service;

	@RequestMapping(value = "/file", method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {
		File file = new File();
		file.setMonths(fileUploadService.getMonthsList());
		file.setMonth(file.getMonths().get(0));
		file.setYears(fileUploadService.getYearList());
		file.setYear(file.getYears().get(0));
		file.setFilesUploaded(fileUploadService.getFileList(file));
		model.addAttribute("file", file);
		return "file";
	}

	@RequestMapping(value = "/listFiles", method = RequestMethod.GET)
	public String listFiles(@RequestParam("year") String year, @RequestParam("month") String month, Model model) {
		File file = new File();
		file.setMonths(fileUploadService.getMonthsList());
		file.setMonth(month);
		file.setYears(fileUploadService.getYearList());
		file.setYear(year);
		file.setFilesUploaded(fileUploadService.getFileList(file));
		model.addAttribute("file", file);
		return "file";
	}

	@RequestMapping(value = "/actualFile", method = RequestMethod.GET)
	public void downloadActualFile(@RequestParam("reportId") int reportId, @RequestParam("type") String type,
			HttpServletResponse response) {
		String filePath = "";
		try {
			if (type.equals(Type.Type1.toString())) {

				filePath = type1Service.generateType1ActualReport(reportId);

			} else {
				filePath = type1Service.generateType2ActualReport(reportId);
			}
		} catch (SuperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Path file = Paths.get(filePath);
		if (Files.exists(file)) {
			if (type.equals(Type.Type1.toString())) {
				response.setContentType("application/text");
			} else {
				response.setContentType("application/xls");
			}
			response.addHeader("Content-Disposition", "attachment; filename="
					+ (filePath.substring(filePath.lastIndexOf(java.io.File.separator) + 1, filePath.length())));
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

	@RequestMapping(value = "/pdfFile", method = RequestMethod.GET)
	public void downloadPdfFile(@RequestParam("reportId") int reportId, @RequestParam("type") String type,
			HttpServletResponse response) {
		String filePath = "";
		try {
			if (type.equals(Type.Type1.toString())) {

				filePath = type1Service.generateType1Report(reportId);

			} else {
				filePath = type1Service.generateType2Report(reportId);
			}
		} catch (SuperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Path file = Paths.get(filePath);
		if (Files.exists(file)) {
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename="
					+ (filePath.substring(filePath.lastIndexOf(java.io.File.separator) + 1, filePath.length())));
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
