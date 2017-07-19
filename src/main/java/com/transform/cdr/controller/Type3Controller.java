package com.transform.cdr.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.transform.cdr.model.Ship;
import com.transform.cdr.model.Type3;
import com.transform.cdr.model.Type3Model;
import com.transform.cdr.service.Type3Service;

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
public class Type3Controller {

	@Autowired
	Type3Service type3Service;

	@Autowired
	ServletContext servletContext;

	public Type3Controller() {

	}

	@RequestMapping(method = RequestMethod.GET, value = "/type3")
	public String showForm(ModelMap model, HttpServletRequest request, @RequestParam(value="format", required=false)Integer format) {
		Type3 type3 = new Type3();
		if(null == format){
			format = 0;
		}
		request.getSession().setAttribute("FORMAT",format);
		if (request.getSession().getAttribute("FILE_PATH") != null) {
			type3.setFilePath((String) request.getSession().getAttribute("FILE_PATH"));
			type3.setFormat(format);
			type3.setTitle(type3.getFormat()==1?"Type3a Upload":"Type3Upload");
			request.getSession().removeAttribute("FILE_PATH");
		}
		model.put("type3", type3);
		return "type3";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/type3")
	public String onSubmit(ModelMap model, @ModelAttribute("type3") Type3 type3, BindingResult errors,
			HttpServletRequest request) throws Exception {
		List<MultipartFile> files = type3.getFiles();
		if (null == files || files.isEmpty()) {
			Object[] args = new Object[] { "Please select a file to upload" };
			errors.rejectValue("files", "Please select a file to upload", args, "Please select a file to upload");
			return "type3";
		}

		List<String> reportFileNames = new ArrayList<String>();
		for (MultipartFile multipartFile : files) {
			if (!multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1)
					.equals("pdf")) {
				type3.setFiles(files);
				Object[] args = new Object[] { "Only PDF files allowed" };
				errors.rejectValue("files", "Only PDF files allowed", args, "Only PDF files allowed");
				return "type3";
			}
			reportFileNames.add(multipartFile.getOriginalFilename());
		}
		List dataList = type3Service.findShipList(reportFileNames);
		Map<String, Ship> shipMap = (Map<String, Ship>) ((null == dataList || dataList.isEmpty())
				? new HashMap<String, Ship>() : dataList.get(0));
		String missingShips = (null == dataList || dataList.isEmpty()) ? "" : (String) dataList.get(1);
		if (shipMap.size() != reportFileNames.size()) {
			Object[] args = new Object[] { "Ships with Mapping Name " + missingShips + " not found" };
			errors.rejectValue("files", "Ships with Mapping Name " + missingShips + " not found", args,
					"Ships with Mapping Name " + missingShips + " not found");
			return "type3";
		}
		File fileToWrite = null;
		String reportPath = null;
		List<String> reportPaths = new ArrayList<String>();
		for (MultipartFile file : files) {
			reportPath = servletContext.getRealPath("/jrxml") + File.separator + file.getOriginalFilename();
			fileToWrite = new File(reportPath);
			FileUtils.writeByteArrayToFile(fileToWrite, file.getBytes());
			reportPaths.add(reportPath);
		}
		Type3Model type3Model = new Type3Model();
		type3Model.setReportPaths(reportPaths);
		type3Model.setShipMap(shipMap);
		type3Model.setFormat((Integer) (request.getSession().getAttribute("FORMAT")==null?0:request.getSession().getAttribute("FORMAT")));
		String filePath = type3Service.saveType3ReportData(type3Model);
		request.getSession().setAttribute("FILE_PATH", filePath);
		return "redirect:type3?format="+(request.getSession().getAttribute("FORMAT")==null?0:request.getSession().getAttribute("FORMAT"));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/type3Report")
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
