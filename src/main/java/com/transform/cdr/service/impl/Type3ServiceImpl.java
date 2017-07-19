package com.transform.cdr.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transform.cdr.dao.CompanyDao;
import com.transform.cdr.dao.ShipDao;
import com.transform.cdr.dao.Type3CompanyParentDao;
import com.transform.cdr.dao.Type3DescriptionDao;
import com.transform.cdr.dao.Type3ItemDao;
import com.transform.cdr.dao.Type3ParentDao;
import com.transform.cdr.dao.Type3ReportDao;
import com.transform.cdr.dao.Type3ReportDataDao;
import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.Company;
import com.transform.cdr.model.Ship;
import com.transform.cdr.model.Type3CompanyParent;
import com.transform.cdr.model.Type3Description;
import com.transform.cdr.model.Type3Item;
import com.transform.cdr.model.Type3Model;
import com.transform.cdr.model.Type3OverallDTO;
import com.transform.cdr.model.Type3Parent;
import com.transform.cdr.model.Type3Report;
import com.transform.cdr.model.Type3ReportData;
import com.transform.cdr.model.Type3ReportJasper;
import com.transform.cdr.service.Type3Service;
import com.transform.cdr.util.AccountType;

import net.sf.jasperreports.engine.export.tabulator.Row;

@Service("type3Service")
public class Type3ServiceImpl implements Type3Service {

	@Autowired
	ShipDao shipDao;

	@Autowired
	Type3ParentDao type3ParentDao;

	@Autowired
	Type3ItemDao type3ItemDao;

	@Autowired
	Type3DescriptionDao type3DescriptionDao;

	@Autowired
	Type3ReportDataDao type3ReportDataDao;

	@Autowired
	Type3ReportDao type3ReportDao;

	@Autowired
	Type3CompanyParentDao type3CompanyParentDao;

	@Autowired
	CompanyDao companyDao;

	@Autowired
	JasperServiceImpl jasperService;

	@Autowired
	ServletContext servletContext;

	@Override
	public List findShipList(List<String> fileNames) {
		if (null == fileNames || fileNames.isEmpty()) {
			return null;
		}
		String mappingName = null;
		int accountType;
		String fileNamePart2;
		Map<String, Integer> accountTypeMap = getAccountTypeMap();
		Ship ship = null;
		Map<String, Ship> ships = new HashMap<String, Ship>();
		List<String> missingMappingShips = new ArrayList<String>();
		StringBuffer missingShipsStr = new StringBuffer();
		for (String fileName : fileNames) {
			accountType = AccountType.FULL.getAccountCode();
			try {
				mappingName = fileName.substring(0, fileName.indexOf("_"));
				fileNamePart2 = fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("."));
				accountType = mappingName.toUpperCase().contains(AccountType.CITADEL.getAccountType().toUpperCase())
						? AccountType.CITADEL.getAccountCode() : accountTypeMap.get(fileNamePart2.toUpperCase());
				mappingName = mappingName.replace(AccountType.CITADEL.getAccountType(), "")
						.replace(AccountType.FULL.getAccountType(), "");
			} catch (Exception e) {
				mappingName = fileName.substring(0, fileName.indexOf("."));
				if (mappingName.contains(AccountType.CITADEL.getAccountType())) {
					accountType = AccountType.CITADEL.getAccountCode();
				}
				mappingName = mappingName.replace(AccountType.CITADEL.getAccountType(), "")
						.replace(AccountType.FULL.getAccountType(), "");

			}
			ship = shipDao.findByMappingNameAndAccountType(mappingName, accountType);
			if (null != ship) {
				ships.put(fileName, ship);
			} else {
				if (!missingMappingShips.contains(mappingName)) {
					missingMappingShips.add(mappingName);
					missingShipsStr.append(mappingName + ", ");
				}
			}
		}
		List dataList = new ArrayList<>();
		dataList.add(ships);
		dataList.add(missingShipsStr.toString());
		return dataList;
	}

	public Map<String, Integer> getAccountTypeMap() {
		Map<String, Integer> accountTypeMap = new HashMap<String, Integer>();
		for (AccountType accountType : AccountType.values()) {
			accountTypeMap.put(accountType.getAccountType().toUpperCase(), accountType.getAccountCode());
		}
		return accountTypeMap;
	}

	public Map<Integer, String> getAccountIdMap() {
		Map<Integer, String> accountTypeMap = new HashMap<Integer, String>();
		for (AccountType accountType : AccountType.values()) {
			accountTypeMap.put(accountType.getAccountCode(), accountType.getAccountType().toUpperCase());
		}
		return accountTypeMap;
	}

	/*
	 * @Override public String saveType3ReportData(Type3Model type3Model) throws
	 * SuperException { if (null == type3Model.getReportPaths() ||
	 * type3Model.getReportPaths().isEmpty()) { throw new SuperException(
	 * "Please specify ship"); } List<Type3Item> type3Items = (List<Type3Item>)
	 * type3ItemDao.findAll(); List<Type3Description> type3Decsriptions =
	 * (List<Type3Description>) type3DescriptionDao.findAll(); Map<String,
	 * Integer> type3ItemsMap = new HashMap<String, Integer>(); Map<String,
	 * Integer> type3DescriptionsMap = new HashMap<String, Integer>(); if (null
	 * != type3Items) { for (Type3Item type3Item : type3Items) {
	 * type3ItemsMap.put(type3Item.getItem(), type3Item.getId()); } } if (null
	 * != type3Items || !type3Items.isEmpty()) { for (Type3Item type3Item :
	 * type3Items) { type3ItemsMap.put(type3Item.getItem(), type3Item.getId());
	 * } } if (null != type3Decsriptions || !type3Decsriptions.isEmpty()) { for
	 * (Type3Description type3Description : type3Decsriptions) {
	 * type3DescriptionsMap.put(type3Description.getDescription(),
	 * type3Description.getId()); } } // BufferedReader br = null; String line =
	 * null; File file = null; RandomAccessFile randomAccessFile = null;
	 * PDFParser parser; PDFTextStripper pdfStripper; PDDocument pdDoc;
	 * COSDocument cosDoc; Map<String, Ship> reportShipMap =
	 * type3Model.getShipMap(); Map<String, List<Type3ReportData>> reportDataMap
	 * = new HashMap<String, List<Type3ReportData>>(); Map<String, Type3Report>
	 * reportMap = new HashMap<String, Type3Report>(); Map<Integer, Double>
	 * companyTotalMap = new HashMap<Integer, Double>(); String text; String
	 * fileContents[]; String invoiceNo; String invoiceDate; double
	 * invoiceSubtotal; String[] date; // String INVOICE_DATE_CONSTANT =
	 * "Invoice Date"; // String INVOICE_NUMBER_CONSTANT = "Invoice Number"; //
	 * String INVOICE_SEARCH_CONSTANT = "Quantity Item Description Amount";
	 * String INVOICE_SEARCH_CONSTANT =
	 * "QUANTITY ITEM NUMBER DESCRIPTION AMOUNT"; // String
	 * INVOICE_STATIC_FEE_CONSTANT = "Fees & Charges"; String
	 * INVOICE_STATIC_FEE_CONSTANT = "Fees"; String WIRING_INSTRUCTIONS =
	 * "Wiring Instructions"; String TOTAL_FLAG = "Total"; String TRAFFIC_PERIOD
	 * = "Traffic Period"; String PAYMENT_DUE_BY = "Payment Due By"; String
	 * fileName = ""; SimpleDateFormat invoiceDateFormat = new
	 * SimpleDateFormat("MM/dd/yyyy"); SimpleDateFormat actualFormat = new
	 * SimpleDateFormat("dd MMM, yyyy"); double overallTotal = 0; Type3Report
	 * type3Report = null; Type3ReportData type3ReportData = null;
	 * List<Type3ReportData> type3ReportDatas = null; boolean itemFlag = false;
	 * double itemPrice = 0; BigDecimal bigDecimal; BigDecimal roundedWithScale;
	 * // Pattern regex = Pattern.compile("\\d+[.]?\\d*"); Pattern regex =
	 * Pattern.compile("(\\d+[.]?\\d*)\\D(\\d+[.]?\\d*)"); Matcher matcher =
	 * null; try { for (String reportPath : type3Model.getReportPaths()) { file
	 * = new File(reportPath); randomAccessFile = new RandomAccessFile(file,
	 * "r"); parser = new PDFParser(randomAccessFile); parser.parse(); cosDoc =
	 * parser.getDocument(); pdfStripper = new PDFTextStripper(); pdDoc =
	 * PDDocument.load(file); pdDoc.getNumberOfPages();
	 * pdfStripper.setEndPage(pdDoc.getNumberOfPages());
	 * pdfStripper.setAddMoreFormatting(true);
	 * pdfStripper.setLineSeparator("\n"); pdfStripper.setSortByPosition(true);
	 * pdfStripper.setSuppressDuplicateOverlappingText(true); text =
	 * pdfStripper.getText(pdDoc); // System.out.println(text); fileContents =
	 * text.split("\n"); type3Report = new Type3Report(); type3ReportDatas = new
	 * ArrayList<Type3ReportData>(); invoiceDate = ""; invoiceNo = ""; itemFlag
	 * = false; invoiceSubtotal = 0; pdDoc.close(); cosDoc.close();
	 * randomAccessFile.close(); file.delete(); int lineNo = 0; String[]
	 * invoiceNumberAndDate = null; for (String content : fileContents) {
	 * lineNo++; if (content.trim().equals("")) { continue; } if
	 * (content.contains(INVOICE_DATE_CONSTANT) &&
	 * content.contains(INVOICE_NUMBER_CONSTANT)) { continue; } if
	 * (content.contains(INVOICE_DATE_CONSTANT)) { date =
	 * content.replace(INVOICE_DATE_CONSTANT, "").trim().split("\\s+");
	 * invoiceDate = (date.length > 2 ? (date[1] + " " + date[0] + ", " +
	 * date[2]) : date[0]); continue; } if
	 * (content.contains(INVOICE_NUMBER_CONSTANT)) { invoiceNo =
	 * content.replace(INVOICE_NUMBER_CONSTANT, "").trim(); continue; } if
	 * (lineNo == 7) { invoiceNumberAndDate = content.split("\\s+"); try {
	 * invoiceDate = actualFormat.format((Date)
	 * invoiceDateFormat.parse(invoiceNumberAndDate[0].trim())); } catch
	 * (ParseException e) { // TODO Auto-generated catch block date =
	 * invoiceNumberAndDate[0].trim().split("/"); invoiceDate = (date.length > 2
	 * ? (date[1] + " " + date[0] + ", " + date[2]) : date[0]); } invoiceNo =
	 * invoiceNumberAndDate[1].trim(); continue; }
	 * 
	 * if (content.contains(INVOICE_SEARCH_CONSTANT)) { itemFlag = true;
	 * continue; } if (content.contains(TRAFFIC_PERIOD) ||
	 * content.contains(PAYMENT_DUE_BY)) { continue; } if (itemFlag) { if
	 * (content.contains(TOTAL_FLAG)) { itemFlag = false; continue; } if
	 * (content.contains(WIRING_INSTRUCTIONS)) { itemFlag = false; continue; }
	 * if (content.contains(INVOICE_STATIC_FEE_CONSTANT)) { continue; } matcher
	 * = regex.matcher(content); if (matcher.find()) { try { itemPrice =
	 * Double.valueOf(matcher.group(1)); } catch (NumberFormatException nfe) {
	 * itemPrice = 0; } catch (IndexOutOfBoundsException ioe) { itemPrice = 0; }
	 * } type3ReportData = new Type3ReportData();
	 * type3ReportData.setDescription(content.replaceAll("\\d+[.]?\\d*",
	 * "").trim()); bigDecimal = new BigDecimal(itemPrice); roundedWithScale =
	 * bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
	 * type3ReportData.setAmount(bigDecimal.doubleValue()); invoiceSubtotal +=
	 * type3ReportData.getAmount(); type3ReportDatas.add(type3ReportData); } }
	 * fileName = reportPath.substring(reportPath.lastIndexOf(File.separator) +
	 * 1); bigDecimal = new
	 * BigDecimal(reportShipMap.get(fileName).getFeeAndStaticCharge());
	 * roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
	 * type3Report.setFeeCharges(roundedWithScale.doubleValue());
	 * invoiceSubtotal += type3Report.getFeeCharges(); bigDecimal = new
	 * BigDecimal(invoiceSubtotal); roundedWithScale = bigDecimal.setScale(2,
	 * BigDecimal.ROUND_HALF_UP);
	 * type3Report.setInvoiceSubtotal(roundedWithScale.doubleValue());
	 * type3Report.setVat(0); type3Report.setOther(0);
	 * type3Report.setTotal(roundedWithScale.doubleValue()); overallTotal +=
	 * roundedWithScale.doubleValue(); if
	 * (!companyTotalMap.containsKey(reportShipMap.get(fileName).getCompanyId())
	 * ) { companyTotalMap.put(reportShipMap.get(fileName).getCompanyId(), 0D);
	 * } companyTotalMap.put(reportShipMap.get(fileName).getCompanyId(),
	 * companyTotalMap.get(reportShipMap.get(fileName).getCompanyId()) +
	 * roundedWithScale.doubleValue()); type3Report.setInvoiceNo(invoiceNo);
	 * type3Report.setInvoiceDate(invoiceDate);
	 * type3Report.setMappingName(reportShipMap.get(fileName).getMappingName());
	 * type3Report.setReportName(fileName);
	 * type3Report.setShipId(reportShipMap.get(fileName).getId());
	 * type3Report.setCompanyId(reportShipMap.get(fileName).getCompanyId());
	 * reportMap.put(reportPath, type3Report); reportDataMap.put(reportPath,
	 * type3ReportDatas); } } catch (IOException e) { throw new SuperException(
	 * "Not able to find file"); } for (String item : type3ItemsMap.keySet()) {
	 * for (String reportPath : reportDataMap.keySet()) { for (Type3ReportData
	 * type3data : reportDataMap.get(reportPath)) { if
	 * (type3data.getDescription().contains(item)) { type3data.setItem(item);
	 * type3data.setDescription(type3data.getDescription().replace(item,
	 * "").trim()); } } } } Type3CompanyParent type3CompanyParent = new
	 * Type3CompanyParent(); bigDecimal = new BigDecimal(overallTotal);
	 * roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
	 * type3CompanyParent.setOverallTotal(roundedWithScale.doubleValue());
	 * type3CompanyParentDao.save(type3CompanyParent); Map<Integer, Type3Parent>
	 * reportParentMap = new HashMap<Integer, Type3Parent>(); Type3Parent
	 * type3Parent = null; double companyTotal = 0D; for (Integer companyId :
	 * companyTotalMap.keySet()) { type3Parent = new Type3Parent();
	 * type3Parent.setCompanyId(companyId); companyTotal =
	 * companyTotalMap.get(companyId); bigDecimal = new
	 * BigDecimal(companyTotal); roundedWithScale = bigDecimal.setScale(2,
	 * BigDecimal.ROUND_HALF_UP);
	 * type3Parent.setOverallTotal(roundedWithScale.doubleValue());
	 * type3Parent.setParentId(type3CompanyParent.getId());
	 * type3ParentDao.save(type3Parent); reportParentMap.put(companyId,
	 * type3Parent); } for (String reportPath : reportMap.keySet()) {
	 * type3Report = reportMap.get(reportPath);
	 * type3Report.setType3ReportId(reportParentMap.get(type3Report.getCompanyId
	 * ()).getId()); type3ReportDao.save(type3Report); for (Type3ReportData
	 * type3ReportData2 : reportDataMap.get(reportPath)) {
	 * type3ReportData2.setReportId(type3Report.getId()); }
	 * type3ReportDataDao.save(reportDataMap.get(reportPath)); } return
	 * generateType3Report(type3CompanyParent.getId()); }
	 */

	@Override
	public String saveType3ReportData(Type3Model type3Model) throws SuperException {
		if (null == type3Model.getReportPaths() || type3Model.getReportPaths().isEmpty()) {
			throw new SuperException("Please specify ship");
		}
		List<Type3Item> type3Items = (List<Type3Item>) type3ItemDao.findAll();
		List<Type3Description> type3Decsriptions = (List<Type3Description>) type3DescriptionDao.findAll();
		Map<String, Integer> type3ItemsMap = new HashMap<String, Integer>();
		Map<String, Integer> type3DescriptionsMap = new HashMap<String, Integer>();
		if (null != type3Items) {
			for (Type3Item type3Item : type3Items) {
				type3ItemsMap.put(type3Item.getItem(), type3Item.getId());
			}
		}
		if (null != type3Items || !type3Items.isEmpty()) {
			for (Type3Item type3Item : type3Items) {
				type3ItemsMap.put(type3Item.getItem(), type3Item.getId());
			}
		}
		if (null != type3Decsriptions || !type3Decsriptions.isEmpty()) {
			for (Type3Description type3Description : type3Decsriptions) {
				type3DescriptionsMap.put(type3Description.getDescription(), type3Description.getId());
			}
		}
		// BufferedReader br = null;
		String line = null;
		File file = null;
		RandomAccessFile randomAccessFile = null;
		PDFParser parser;
		PDFTextStripper pdfStripper;
		PDDocument pdDoc;
		COSDocument cosDoc;
		Map<String, Ship> reportShipMap = type3Model.getShipMap();
		Map<String, List<Type3ReportData>> reportDataMap = new HashMap<String, List<Type3ReportData>>();
		Map<String, Type3Report> reportMap = new HashMap<String, Type3Report>();
		Map<Integer, Double> companyTotalMap = new HashMap<Integer, Double>();
		String text;
		String fileContents[];
		String invoiceNo;
		String invoiceDate;
		double invoiceSubtotal;
		String[] date;
		String INVOICE_DATE_CONSTANT = "Invoice Date";
		String INVOICE_NUMBER_CONSTANT = "Invoice Number";
		String INVOICE_SEARCH_CONSTANT = "Quantity Item Description Amount";
		String INVOICE_SEARCH_CONSTANT_FORMAT_1 = "QUANTITY ITEM NUMBER DESCRIPTION AMOUNT";
		String INVOICE_STATIC_FEE_CONSTANT = "Fees & Charges";
		String INVOICE_STATIC_FEE_CONSTANT_FORMAT_1 = "Fees";
		String BROADBAND_SIM_CARD_FEE_CONSTANT_FORMAT_1 ="Broadband Sim Card Fee";
		String WIRING_INSTRUCTIONS = "Wiring Instructions";
		String TOTAL_FLAG = "Total";
		String TRAFFIC_PERIOD = "Traffic Period";
		String PAYMENT_DUE_BY = "Payment Due By";
		String fileName = "";
		SimpleDateFormat invoiceDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat actualFormat = new SimpleDateFormat("dd MMM, yyyy");
		double overallTotal = 0;
		Type3Report type3Report = null;
		Type3ReportData type3ReportData = null;
		List<Type3ReportData> type3ReportDatas = null;
		boolean itemFlag = false;
		double itemPrice = 0;
		BigDecimal bigDecimal;
		BigDecimal roundedWithScale;
		Pattern regex = Pattern.compile("\\d+[.]?\\d*");
		Pattern regex_FORMAT_1 = Pattern.compile("(\\d+[.]?\\d*)\\D(\\d+[.]?\\d*)");
		Matcher matcher = null;
		try {
			for (String reportPath : type3Model.getReportPaths()) {
				file = new File(reportPath);
				randomAccessFile = new RandomAccessFile(file, "r");
				parser = new PDFParser(randomAccessFile);
				parser.parse();
				cosDoc = parser.getDocument();
				pdfStripper = new PDFTextStripper();
				pdDoc = PDDocument.load(file);
				pdDoc.getNumberOfPages();
				pdfStripper.setEndPage(pdDoc.getNumberOfPages());
				pdfStripper.setAddMoreFormatting(true);
				pdfStripper.setLineSeparator("\n");
				pdfStripper.setSortByPosition(true);
				pdfStripper.setSuppressDuplicateOverlappingText(true);
				text = pdfStripper.getText(pdDoc);
				// System.out.println(text);
				fileContents = text.split("\n");
				type3Report = new Type3Report();
				type3ReportDatas = new ArrayList<Type3ReportData>();
				invoiceDate = "";
				invoiceNo = "";
				itemFlag = false;
				invoiceSubtotal = 0;
				pdDoc.close();
				cosDoc.close();
				randomAccessFile.close();
				file.delete();
				int lineNo = 0;
				String[] invoiceNumberAndDate = null;
				for (String content : fileContents) {
					lineNo++;
					if (content.trim().equals("")) {
						continue;
					}
					if (type3Model.getFormat() == 1) {
						if (content.contains(INVOICE_DATE_CONSTANT) && content.contains(INVOICE_NUMBER_CONSTANT)) {
							continue;
						}
						if (content.contains(INVOICE_DATE_CONSTANT)) {
							date = content.replace(INVOICE_DATE_CONSTANT, "").trim().split("\\s+");
							invoiceDate = (date.length > 2 ? (date[1] + " " + date[0] + ", " + date[2]) : date[0]);
							continue;
						}
						if (content.contains(INVOICE_NUMBER_CONSTANT)) {
							invoiceNo = content.replace(INVOICE_NUMBER_CONSTANT, "").trim();
							continue;
						}
					} else {
						if (lineNo == 7) {
							invoiceNumberAndDate = content.split("\\s+");
							try {
								invoiceDate = actualFormat
										.format((Date) invoiceDateFormat.parse(invoiceNumberAndDate[0].trim()));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								date = invoiceNumberAndDate[0].trim().split("/");
								invoiceDate = (date.length > 2 ? (date[1] + " " + date[0] + ", " + date[2]) : date[0]);
							}
							invoiceNo = invoiceNumberAndDate[1].trim();
							continue;
						}
					}

					if (type3Model.getFormat() == 1) {
						if (content.contains(INVOICE_SEARCH_CONSTANT)) {
							itemFlag = true;
							continue;
						}
					} else {
						if (content.contains(INVOICE_SEARCH_CONSTANT_FORMAT_1)) {
							itemFlag = true;
							continue;
						}
					}
					if (content.contains(TRAFFIC_PERIOD) || content.contains(PAYMENT_DUE_BY)) {
						continue;
					}
					if (itemFlag) {
						if (type3Model.getFormat() == 1) {
							if (content.contains(TOTAL_FLAG)) {
								itemFlag = false;
								continue;
							}
						}
						if (type3Model.getFormat() != 1) {
							if (content.contains(WIRING_INSTRUCTIONS)) {
								itemFlag = false;
								continue;
							}
							if (content.contains(INVOICE_STATIC_FEE_CONSTANT_FORMAT_1) || content.contains(BROADBAND_SIM_CARD_FEE_CONSTANT_FORMAT_1)) {
								continue;
							}
							matcher = regex_FORMAT_1.matcher(content);
							if (matcher.find()) {
								try {
									itemPrice = Double.valueOf(matcher.group(1)+"."+matcher.group(2));
								} catch (NumberFormatException nfe) {
									itemPrice = 0;
								} catch (IndexOutOfBoundsException ioe) {
									itemPrice = 0;
								}
							}
						}
						else{
							if (content.contains(INVOICE_STATIC_FEE_CONSTANT)) {
								continue;
							}
							matcher = regex.matcher(content);
							if (matcher.find()) {
								try {
									itemPrice = Double.valueOf(matcher.group(0));
								} catch (NumberFormatException nfe) {
									itemPrice = 0;
								} catch (IndexOutOfBoundsException ioe) {
									itemPrice = 0;
								}
							}
						}
						type3ReportData = new Type3ReportData();
						type3ReportData.setDescription(content.replaceAll("\\d+[.]?\\d*", "").trim());
						bigDecimal = new BigDecimal(itemPrice);
						roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
						type3ReportData.setAmount(bigDecimal.doubleValue());
						invoiceSubtotal += type3ReportData.getAmount();
						type3ReportDatas.add(type3ReportData);
					}
				}
				fileName = reportPath.substring(reportPath.lastIndexOf(File.separator) + 1);
				bigDecimal = new BigDecimal(reportShipMap.get(fileName).getFeeAndStaticCharge());
				roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
				type3Report.setFeeCharges(roundedWithScale.doubleValue());
				invoiceSubtotal += type3Report.getFeeCharges();
				bigDecimal = new BigDecimal(invoiceSubtotal);
				roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
				type3Report.setInvoiceSubtotal(roundedWithScale.doubleValue());
				type3Report.setVat(0);
				type3Report.setOther(0);
				type3Report.setTotal(roundedWithScale.doubleValue());
				overallTotal += roundedWithScale.doubleValue();
				if (!companyTotalMap.containsKey(reportShipMap.get(fileName).getCompanyId())) {
					companyTotalMap.put(reportShipMap.get(fileName).getCompanyId(), 0D);
				}
				companyTotalMap.put(reportShipMap.get(fileName).getCompanyId(),
						companyTotalMap.get(reportShipMap.get(fileName).getCompanyId())
								+ roundedWithScale.doubleValue());
				type3Report.setInvoiceNo(invoiceNo);
				type3Report.setInvoiceDate(invoiceDate);
				type3Report.setMappingName(reportShipMap.get(fileName).getMappingName());
				type3Report.setReportName(fileName);
				type3Report.setShipId(reportShipMap.get(fileName).getId());
				type3Report.setCompanyId(reportShipMap.get(fileName).getCompanyId());
				reportMap.put(reportPath, type3Report);
				reportDataMap.put(reportPath, type3ReportDatas);
			}
		} catch (IOException e) {
			throw new SuperException("Not able to find file");
		}
		for (String item : type3ItemsMap.keySet()) {
			for (String reportPath : reportDataMap.keySet()) {
				for (Type3ReportData type3data : reportDataMap.get(reportPath)) {
					if (type3data.getDescription().contains(item)) {
						type3data.setItem(item);
						type3data.setDescription(type3data.getDescription().replace(item, "").trim());
					}
				}
			}
		}
		Type3CompanyParent type3CompanyParent = new Type3CompanyParent();
		bigDecimal = new BigDecimal(overallTotal);
		roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		type3CompanyParent.setOverallTotal(roundedWithScale.doubleValue());
		type3CompanyParentDao.save(type3CompanyParent);
		Map<Integer, Type3Parent> reportParentMap = new HashMap<Integer, Type3Parent>();
		Type3Parent type3Parent = null;
		double companyTotal = 0D;
		for (Integer companyId : companyTotalMap.keySet()) {
			type3Parent = new Type3Parent();
			type3Parent.setCompanyId(companyId);
			companyTotal = companyTotalMap.get(companyId);
			bigDecimal = new BigDecimal(companyTotal);
			roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			type3Parent.setOverallTotal(roundedWithScale.doubleValue());
			type3Parent.setParentId(type3CompanyParent.getId());
			type3Parent.setFormat(type3Model.getFormat());
			type3ParentDao.save(type3Parent);
			reportParentMap.put(companyId, type3Parent);
		}
		for (String reportPath : reportMap.keySet()) {
			type3Report = reportMap.get(reportPath);
			type3Report.setType3ReportId(reportParentMap.get(type3Report.getCompanyId()).getId());
			type3ReportDao.save(type3Report);
			for (Type3ReportData type3ReportData2 : reportDataMap.get(reportPath)) {
				type3ReportData2.setReportId(type3Report.getId());
			}
			type3ReportDataDao.save(reportDataMap.get(reportPath));
		}
		return generateType3Report(type3CompanyParent.getId());
	}

	@Override
	public String generateType3Report(int parentReportId) throws SuperException {
		String FEE_STR_CONSTANT = "Fees";
		Type3CompanyParent type3CompanyParent = type3CompanyParentDao.findOne(parentReportId);
		if (null == type3CompanyParent) {
			throw new SuperException("Report Not found");
		}
		List<Type3Parent> type3Parents = type3ParentDao.findByParentId(type3CompanyParent.getId());
		if (null == type3Parents || type3Parents.isEmpty()) {
			throw new SuperException("Report Not found");
		}

		List<String> finalFileNames = new ArrayList<String>();
		for (Type3Parent type3Parent : type3Parents) {
			List<Type3Report> type3Reports = type3ReportDao.findByType3ReportId(type3Parent.getId());
			if (null == type3Reports || type3Reports.isEmpty()) {
				throw new SuperException("Report Not found");
			}
			Company company = companyDao.findOne(type3Parent.getCompanyId());
			Ship ship = null;
			List<String> shipReportsName = new ArrayList<String>();
			List<Type3ReportData> type3ReportDatas = null;

			Map<Integer, String> accountTypeMap = getAccountIdMap();

			Map<Integer, String> reportDescriptionMap = null;

			Map<Integer, String> reportAmountMap = null;

			DecimalFormat decimalFormat = new DecimalFormat("#0.00");

			Type3ReportJasper type3ReportJasper = null;

			List<Type3ReportJasper> type3ReportJaspers = null;

			Map<Integer, Ship> shipsMap = new HashMap<Integer, Ship>();

			int counter = 0;

			List<List> dataList = null;
			List<Map> parametersMap = null;
			Map<String, Object> parameterMap1 = null;
			String date[];
			String trafficType;
			List<Type3OverallDTO> type3OverallDTOs = new ArrayList<Type3OverallDTO>();
			Type3OverallDTO type3OverallDTO = null;
			for (Type3Report type3Report : type3Reports) {
				counter = 1;
				reportDescriptionMap = new HashMap<Integer, String>();
				reportAmountMap = new HashMap<Integer, String>();

				if (shipsMap.containsKey(type3Report.getShipId())) {
					ship = shipsMap.get(type3Report.getShipId());
				} else {
					ship = shipDao.findById(type3Report.getShipId());
					shipsMap.put(type3Report.getShipId(), ship);
				}
				type3ReportDatas = type3ReportDataDao.findByReportId(type3Report.getId());

				if (null != type3ReportDatas) {
					for (Type3ReportData type3ReportData : type3ReportDatas) {
						reportDescriptionMap.put(counter,
								(type3ReportData.getItem() + ": " + type3ReportData.getDescription()));
						reportAmountMap.put(counter, (type3ReportData.getAmount() == 0 ? "-"
								: decimalFormat.format(type3ReportData.getAmount())));
						counter++;
					}
				}
				reportDescriptionMap.put(counter, FEE_STR_CONSTANT);
				reportAmountMap.put(counter,
						(type3Report.getFeeCharges() == 0 ? "-" : decimalFormat.format(type3Report.getFeeCharges())));
				date = type3Report.getInvoiceDate().split(",");
				if(type3Parent.getFormat()==1){
				 trafficType = (date[0].split(" ")[0].trim() + "-" +
				 (date.length > 1 ? date[1] : ""));
				}
				else{
				trafficType = (date[0].split(" ").length > 1 ? (date[0].split(" ")[1] + " - ") : "")
						+ (date.length > 1 ? date[1] : "");
				
				}
				trafficType = "Traffic period: " + trafficType;
				type3ReportJasper = new Type3ReportJasper(
						(type3Report.getFeeCharges() == 0 ? "-" : decimalFormat.format(type3Report.getFeeCharges())),
						null != ship ? ship.getMappingName() : "", trafficType, null != ship ? ship.getName() : "",
						null != ship ? (accountTypeMap.get(ship.getAccountType()) + " Unit") : "",
						type3Report.getInvoiceNo(), type3Report.getInvoiceDate(),
						(type3Report.getInvoiceSubtotal() == 0 ? "-"
								: decimalFormat.format(type3Report.getInvoiceSubtotal())),
						(decimalFormat.format(type3Report.getVat()) + "%"),
						(type3Report.getOther() == 0 ? "-" : decimalFormat.format(type3Report.getOther())),
						(type3Report.getTotal() == 0 ? "-" : decimalFormat.format(type3Report.getTotal())),
						reportDescriptionMap, reportAmountMap, null == company ? "" : company.getName());
				type3OverallDTO = new Type3OverallDTO(
						"ALL_20" + type3Report.getInvoiceNo().split("-")[0].substring(3) + "_"
								+ type3Report.getInvoiceNo().split("-")[0].substring(0, 3) + "_S1",
						type3Report.getInvoiceDate(), null != ship ? ship.getName() : "",
						(null != ship ? ((ship.getAccountType() == AccountType.FULL.getAccountCode() ? "IOP"
								: accountTypeMap.get(ship.getAccountType())) + " - ") : "")
								+ type3Report.getInvoiceNo(),
						"1", type3Report.getTotal() == 0 ? "-" : decimalFormat.format(type3Report.getTotal()), "USD",
						type3Report.getTotal() == 0 ? "-" : decimalFormat.format(type3Report.getTotal()),
						type3Parent.getOverallTotal() == 0 ? "-" : decimalFormat.format(type3Parent.getOverallTotal()),
						"0.00",
						type3Parent.getOverallTotal() == 0 ? "-" : decimalFormat.format(type3Parent.getOverallTotal()),
						type3Report.getInvoiceNo(), null == company ? "" : company.getName());
				type3OverallDTOs.add(type3OverallDTO);

				type3ReportJaspers = new ArrayList<Type3ReportJasper>();
				type3ReportJaspers.add(type3ReportJasper);

				List<String> jasperName = new ArrayList<String>();
				jasperName.add("Type3Report");
				dataList = new ArrayList<List>();
				dataList.add(type3ReportJaspers);
				parametersMap = new ArrayList<Map>();
				parameterMap1 = new HashMap<String, Object>();
				parametersMap.add(parameterMap1);

				shipReportsName.add(jasperService.generateReport2PDF(jasperName, dataList, parametersMap,
						(type3ReportJasper.getInvoiceNo() + " Invoice.pdf")));
			}
			Collections.sort(type3OverallDTOs, new Comparator<Type3OverallDTO>() {

				@Override
				public int compare(Type3OverallDTO o1, Type3OverallDTO o2) {
					// TODO Auto-generated method stub
					return o1.getInvoiceNo().compareTo(o2.getInvoiceNo());
				}
			});
			List<String> jasperName = new ArrayList<String>();
			jasperName.add("Type3ReportOverall");
			dataList = new ArrayList<List>();
			dataList.add(type3OverallDTOs);
			parametersMap = new ArrayList<Map>();
			parameterMap1 = new HashMap<String, Object>();
			parametersMap.add(parameterMap1);
			String overallReportName = jasperService.generateReport2PDF(jasperName, dataList, parametersMap,
					(type3OverallDTOs.get(0).getInvoiceSummary()
							+ (null == company ? "" : ("_" + company.getName().replace(" ", "_"))) + ".pdf"));

			String reportFileName = servletContext.getRealPath("/jrxml") + File.separator + "Type3Report"
					+ (null == company ? "" : ("_" + company.getName().replace(" ", "_"))) + ".zip";
			String overallXlsReportFile = generateType3OverallXls(type3OverallDTOs, company);
			File zipFile = new File(reportFileName);

			ZipOutputStream out = null;
			finalFileNames.add(reportFileName);
			finalFileNames.add(overallReportName);
			finalFileNames.add(overallXlsReportFile);
			if (zipFile.exists())
				zipFile.delete();
			try {
				out = new ZipOutputStream(new FileOutputStream(zipFile));

				for (String reportName : shipReportsName) {
					addToZipFile(reportName, out);
				}
				out.close();
				out.flush();

			} catch (IOException e) {

			} finally {
				if (null != out) {
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		String overallReportFileName = servletContext.getRealPath("/jrxml") + File.separator + "Type3ReportOverall.zip";
		File overallZipFile = new File(overallReportFileName);
		ZipOutputStream overallZipOut = null;
		if (overallZipFile.exists())
			overallZipFile.delete();
		try {
			overallZipOut = new ZipOutputStream(new FileOutputStream(overallZipFile));
			for (String reportName : finalFileNames) {
				if (null != reportName) {
					addToZipFile(reportName, overallZipOut);
				}
			}
			overallZipOut.close();
			overallZipOut.flush();
		} catch (IOException e) {

		} finally {
			if (null != overallZipOut) {
				try {
					overallZipOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return overallReportFileName;

	}

	private void addToZipFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		ZipEntry zipEntry = new ZipEntry(fileName.substring(fileName.lastIndexOf(File.separator) + 1));
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}
		zos.closeEntry();
		fis.close();
		file.delete();
	}

	private String generateType3OverallXls(List<Type3OverallDTO> type3OverallDTOs, Company company) {
		String templateFile = servletContext.getRealPath("/xls") + File.separator + "ALL_S1.xlsx";
		String reportFileName = servletContext.getRealPath("/xls") + File.separator
				+ type3OverallDTOs.get(0).getInvoiceSummary()
				+ (null == company ? "" : ("_" + company.getName().replace(" ", "_"))) + ".xlsx";
		/*
		 * File template = new File(templateFile); File reportFile = new
		 * File(reportFileName); if (reportFile.exists()) { reportFile.delete();
		 * } try { Files.copy(template.toPath(), reportFile.toPath(),
		 * StandardCopyOption.REPLACE_EXISTING); } catch (IOException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */
		try {
			FileInputStream fsIP = new FileInputStream(templateFile);
			XSSFWorkbook wb = new XSSFWorkbook(fsIP);
			XSSFSheet worksheet = wb.getSheetAt(0);
			XSSFCell cell = null;
			cell = worksheet.getRow(1).getCell(5);
			cell.setCellValue(type3OverallDTOs.get(0).getInvoiceSummary());
			cell = worksheet.getRow(2).getCell(5);
			cell.setCellValue(type3OverallDTOs.get(0).getInvoiceDate());
			fsIP.close(); // Close the InputStream
			int row = 15;
			if (type3OverallDTOs.size() > 1) {
				worksheet.shiftRows(16, worksheet.getLastRowNum(), type3OverallDTOs.size() - 1);
			}
			List<XSSFRow> copyRows = new ArrayList<XSSFRow>();
			copyRows.add(worksheet.getRow(15));
			CellCopyPolicy cellCopyPolicy = new CellCopyPolicy();
			for (int row1 = 16; row1 < row + type3OverallDTOs.size(); row1++) {
				worksheet.createRow(row1);
				worksheet.copyRows(copyRows, row1, cellCopyPolicy);
			}
			row = 15;
			cell = worksheet.getRow(4).getCell(5);
			cell.setCellValue(null == company ? "" : company.getName());
			for (Type3OverallDTO type3OverallDTO : type3OverallDTOs) {
				cell = worksheet.getRow(row).getCell(0);
				cell.setCellValue(type3OverallDTO.getVesselName());
				cell = worksheet.getRow(row).getCell(1);
				cell.setCellValue(type3OverallDTO.getDescription());
				cell = worksheet.getRow(row).getCell(2);
				cell.setCellValue(type3OverallDTO.getQuantity());
				cell = worksheet.getRow(row).getCell(3);
				cell.setCellValue(type3OverallDTO.getAmount());
				cell = worksheet.getRow(row).getCell(4);
				cell.setCellValue(type3OverallDTO.getCurrency());
				cell = worksheet.getRow(row).getCell(5);
				cell.setCellValue(type3OverallDTO.getAmount2());
				row++;
			}
			cell = worksheet.getRow(row).getCell(5);
			cell.setCellValue(type3OverallDTOs.get(0).getInvoiceSubtotal());
			row++;
			cell = worksheet.getRow(row).getCell(5);
			cell.setCellValue("0.00");
			row++;
			cell = worksheet.getRow(row).getCell(5);
			cell.setCellValue(type3OverallDTOs.get(0).getTotal());

			/*
			 * int row1 = row; for (; row1 < 115; row1++) {
			 * worksheet.removeRow(worksheet.getRow(row1)); }
			 * 
			 * worksheet.shiftRows(115, worksheet.getLastRowNum(), (115 - row));
			 */

			FileOutputStream output_file = new FileOutputStream(reportFileName);

			wb.write(output_file); // write changes
			output_file.flush();
			output_file.close();
			return reportFileName;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // close the stream

		return null;

	}

}
