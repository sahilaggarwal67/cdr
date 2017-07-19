package com.transform.cdr.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

@Service("jasperService")
public class JasperServiceImpl {
	@Autowired
	ServletContext servletContext;

	public String generateReport2PDF(List<String> jasperName, List<List> dataList, List<Map> parameterMap,
			String finalReportName) {
		try {
			if (null == jasperName || jasperName.isEmpty()) {
				return null;
			}
			JasperReport[] jasperReport = new JasperReport[jasperName.size()];

			// JasperPrint[] jasperPrint = new JasperPrint[jasperName.size()];
			List<JasperPrint> jasperPrint = new ArrayList<JasperPrint>(jasperName.size());
			/*
			 * jasperName="sample"; List dataList1 = new ArrayList();
			 * dataList1.add(new Sample("Sahil", "India")); dataList1.add(new
			 * Sample("Arpit", "Africa")); dataList1.add(new Sample("Mayank",
			 * "USA")); dataList1.add(new Sample("Prakhar", "Uganda"));
			 */
			JRBeanCollectionDataSource beanColDataSource = null;
			Map<String, Object> parameters = null;
			for (int jasperCount = 0; jasperCount < jasperName.size(); jasperCount++) {
				beanColDataSource = new JRBeanCollectionDataSource(dataList.get(jasperCount));
				parameters = parameterMap.get(jasperCount);
				/*
				 * parameters.put("SUBREPORT_DIR",
				 * System.getProperty("user.dir") + File.separator);
				 * parameters.put("LOGO", System.getProperty("user.dir") +
				 * File.separator + "Logo.PNG"); parameters.put("DETAILS",
				 * System.getProperty("user.dir") + File.separator +
				 * "Details.PNG");
				 */
				parameters.put("SUBREPORT_DIR", servletContext.getRealPath("/jrxml") + File.separator);
				parameters.put("LOGO", servletContext.getRealPath("/images") + File.separator + "Logo.PNG");
				parameters.put("LOGO2", servletContext.getRealPath("/images") + File.separator + "Logo2.PNG");
				parameters.put("DETAILS", servletContext.getRealPath("/images") + File.separator + "Details.PNG");
				jasperReport[jasperCount] = JasperCompileManager.compileReport(
						servletContext.getRealPath("/jrxml") + File.separator + jasperName.get(jasperCount) + ".jrxml");
				jasperPrint.add(JasperFillManager.fillReport(jasperReport[jasperCount], parameters, beanColDataSource));
			}
			// String reportFileName = System.getProperty("user.dir") +
			// File.separator + jasperName.get(0) + ".pdf";
//			String reportFileName = servletContext.getRealPath("/jrxml") + File.separator + jasperName.get(0) + ".pdf";
			// JasperExportManager.exportReportToPdfFile(jasperPrint,reportFileName);
			JRPdfExporter exporter = new JRPdfExporter();
			finalReportName = servletContext.getRealPath("/jrxml") + File.separator + finalReportName;
			exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(finalReportName));
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			configuration.setCreatingBatchModeBookmarks(true);
			exporter.setConfiguration(configuration);
			exporter.exportReport();
			/*
			 * System.err.println("PDF creation time : " +
			 * (System.currentTimeMillis() - start));
			 */
			return finalReportName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}