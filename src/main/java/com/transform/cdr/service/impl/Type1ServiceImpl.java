package com.transform.cdr.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transform.cdr.dao.ManualFieldsDao;
import com.transform.cdr.dao.ShipDao;
import com.transform.cdr.dao.Type1ReportDao;
import com.transform.cdr.dao.Type1ReportDataDao;
import com.transform.cdr.dao.Type1ReportHeaderDataDao;
import com.transform.cdr.dao.Type1ReportManualEntriesDao;
import com.transform.cdr.dao.Type2ParentDao;
import com.transform.cdr.dao.Type2RateDao;
import com.transform.cdr.dao.Type2ReportDataDao;
import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.ManualFields;
import com.transform.cdr.model.Ship;
import com.transform.cdr.model.Type1ProcessModel;
import com.transform.cdr.model.Type1Report;
import com.transform.cdr.model.Type1ReportData;
import com.transform.cdr.model.Type1ReportDataJasper;
import com.transform.cdr.model.Type1ReportHeaderData;
import com.transform.cdr.model.Type1ReportJasper;
import com.transform.cdr.model.Type1ReportManualEntries;
import com.transform.cdr.model.Type2Parent;
import com.transform.cdr.model.Type2Rate;
import com.transform.cdr.model.Type2ReportData;
import com.transform.cdr.model.Type2ReportDataJasper;
import com.transform.cdr.model.Type2ShipModel;
import com.transform.cdr.service.Type1Service;
import com.transform.cdr.util.AccountType;
import com.transform.cdr.util.Type1EntryEnum;
import com.transform.cdr.util.Type2EntryEnum;
import com.transform.cdr.util.Type2RateEnum;

@Service("type1Service")
public class Type1ServiceImpl implements Type1Service {

	@Autowired
	ShipDao shipDao;

	@Autowired
	ManualFieldsDao manualFieldsDao;

	@Autowired
	Type1ReportDao type1ReportDao;

	@Autowired
	Type1ReportHeaderDataDao type1ReportHeaderDataDao;

	@Autowired
	Type1ReportDataDao type1ReportDataDao;

	@Autowired
	Type1ReportManualEntriesDao type1ReportManualEntriesDao;

	@Autowired
	Type2ReportDataDao type2ReportDataDao;

	@Autowired
	JasperServiceImpl jasperService;

	@Autowired
	Type2RateDao type2RateDao;

	@Autowired
	Type2ParentDao type2ParentDao;

	@Autowired
	ServletContext servletContext;

	/*
	 * public List getShipManualEntries(String shipName) throws SuperException {
	 * Ship ship = shipDao.findByName(shipName); if (null == ship) { throw new
	 * SuperException("Ship with name " + shipName + " not found"); } List data
	 * = new ArrayList(); data.add(ship);
	 * data.add(shipManualEntriesDao.findByShipId(ship.getId())); return data; }
	 */

	public List getShipManualEntries(String shipName, int accountType) throws SuperException {
		Ship ship = shipDao.findByNameAndAccountType(shipName, accountType);
		if (null == ship) {
			throw new SuperException("Ship with name " + shipName + " not found");
		}
		List data = new ArrayList();
		data.add(ship);
		List<ManualFields> manualEntries = manualFieldsDao.findByAccountType(accountType);
		if (null == manualEntries || manualEntries.isEmpty()) {
			data.add(manualEntries);
		}
		for (ManualFields manualFields : manualEntries) {
			if (manualFields.getId() >= Type1EntryEnum.IridiumOpenport.getId()) {
				manualFields.setEntryUnits(1);
			}
		}
		data.add(manualFieldsDao.findByAccountType(accountType));
		return data;
	}

	public List getShipManualEntriesType2(int accountType) throws SuperException {
		List<Ship> ships = shipDao.findByAccountType(accountType);
		if (null == ships || ships.isEmpty()) {
			throw new SuperException("Vessels not found");
		}
		List<ManualFields> manualFields = manualFieldsDao.findByAccountType(accountType);
		List<Type2ShipModel> type2ShipModels = new ArrayList<Type2ShipModel>();
		Type2ShipModel type2ShipModel = null;
		for (Ship ship : ships) {
			type2ShipModel = new Type2ShipModel(ship, manualFields);
			type2ShipModels.add(type2ShipModel);
		}
		return type2ShipModels;
	}

	public String saveReportData(Type1ProcessModel type1ProcessModel) throws SuperException {
		if (null == type1ProcessModel.getType1process().getShip()) {
			throw new SuperException("Please specify ship");
		}
		File file = new File(type1ProcessModel.getFilePath());
		if (!file.exists()) {
			throw new SuperException("Please specify ship");
		}
		BufferedReader br = null;
		String line = null;
		Map<Integer, Type1ReportHeaderData> reportHeaderMap = new LinkedHashMap<Integer, Type1ReportHeaderData>();
		Map<Integer, List<Type1ReportData>> reportDataMap = new LinkedHashMap<Integer, List<Type1ReportData>>();
		try {
			br = new BufferedReader(new FileReader(file));
			int counter = 1;
			Type1ReportData type1ReportData = null;
			Type1ReportHeaderData type1ReportHeaderData = null;
			String[] data = null;
			double pricePerMin = 0;
			// String pricePerMinStr = "";
			double rate = 0;
			double hour, min, sec = 0;
			double totalMins = 0;
			BigDecimal bigDecimal = null;
			BigDecimal roundedWithScale = null;
			double reportCallPrice = 0;
			double actualPrice = 0;
			String actualPriceStr = "0";
			Pattern regex = Pattern.compile("\\d+[.]?\\d*");
			Matcher matcher = null;
			while ((line = br.readLine()) != null) {
				hour = 0;
				min = 0;
				sec = 0;
				totalMins = 0;
				if (line.trim().equals("")) {
					continue;
				}
				if (line.contains("Voice to") && !line.contains("Total")) {
					actualPriceStr = "0";
					matcher = regex.matcher(line);
					if (matcher.find()) {
						actualPriceStr = matcher.group(0);
					}
					try {
						actualPrice = Double.valueOf(actualPriceStr);
					} catch (NumberFormatException nfe) {
						actualPrice = 0;
					}
					pricePerMin = line.contains("Cellular")
							? type1ProcessModel.getType1process().getShip().getVoiceToCellular()
							: type1ProcessModel.getType1process().getShip().getVoiceToTerrestial();
					if (reportHeaderMap.containsKey(counter)) {
						counter++;
					}
					type1ReportHeaderData = new Type1ReportHeaderData();
					type1ReportHeaderData
							.setHeaderName(
									line.contains("Cellular")
											? line.trim().replaceAll("\\d+[.]?\\d*",
													String.valueOf(type1ProcessModel.getType1process().getShip()
															.getVoiceToCellular()))
											: line.trim().replaceAll("\\d+[.]?\\d*", String.valueOf(type1ProcessModel
													.getType1process().getShip().getVoiceToTerrestial())));
					type1ReportHeaderData.setActualHeaderName(line);
					reportHeaderMap.put(counter, type1ReportHeaderData);
					reportDataMap.put(counter, new ArrayList<Type1ReportData>());
					continue;
				}
				if (line.contains("Voice to") && line.contains("Total")) {
					counter++;
					continue;
				}
				type1ReportData = new Type1ReportData();
				data = line.split("\\s{2,}");
				type1ReportData.setTime(data[0]);
				type1ReportData.setNetwork(data[1]);
				type1ReportData.setFrom_msisdn(data[2]);
				type1ReportData.setTo_msisdn(data[3]);
				type1ReportData.setDuration(data[4]);
				type1ReportData.setActualPrice(actualPrice);
				try {
					rate = Double.valueOf(data[5]);
				} catch (NumberFormatException nfe) {
					rate = 0;
				}
				try {
					hour = Double.valueOf(data[4].split(":")[0]);
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException nfe) {
					hour = 0;
				}
				try {
					min = Double.valueOf(data[4].split(":")[1]);
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException nfe) {
					min = 0;
				}
				try {
					sec = Double.valueOf(data[4].split(":")[2]);
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException nfe) {
					sec = 0;
				}
				totalMins = (hour * 60) + min + (sec / 60);
				type1ReportData.setRate(rate);
				bigDecimal = new BigDecimal(totalMins * pricePerMin);
				roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
				type1ReportData.setPrice(roundedWithScale.doubleValue());
				reportCallPrice += roundedWithScale.doubleValue();
				reportDataMap.get(counter).add(type1ReportData);
			}
			br.close();
			Type1Report type1Report = new Type1Report();
			type1Report.setCreatedBy(type1ProcessModel.getUsername());
			bigDecimal = new BigDecimal(type1ProcessModel.getType1process().getShip().getMonthlyFee());
			roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
			double monthlyFee = roundedWithScale.doubleValue();
			type1Report.setMonthlyFee(monthlyFee);
			type1Report.setShipId(type1ProcessModel.getType1process().getShip().getId());
			bigDecimal = new BigDecimal(type1ProcessModel.getType1process().getShip().getStaticIpFee());
			roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
			double staticIpFee = roundedWithScale.doubleValue();
			type1Report.setStaticIpFee(staticIpFee);
			type1Report.setReportName(type1ProcessModel.getFileName());
			type1Report.setMonth(type1ProcessModel.getMonth());
			type1Report.setYear(type1ProcessModel.getYear());
			type1Report.setCreatedTime(Calendar.getInstance());
			bigDecimal = new BigDecimal(reportCallPrice);
			roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
			type1Report.setBusinessLineCalls(roundedWithScale.doubleValue());
			type1ReportDao.save(type1Report);
			List<Type1ReportData> reportDatas = null;
			for (Integer count : reportHeaderMap.keySet()) {
				type1ReportHeaderData = reportHeaderMap.get(count);
				type1ReportHeaderData.setReportId(type1Report.getId());
				type1ReportHeaderDataDao.save(type1ReportHeaderData);
				if (!reportDataMap.containsKey(count)) {
					continue;
				}
				reportDatas = reportDataMap.get(count);
				if (null == reportDatas || reportDatas.isEmpty()) {
					continue;
				}
				for (Type1ReportData type1ReportData2 : reportDatas) {
					type1ReportData2.setReportId(type1Report.getId());
					type1ReportData2.setHeaderId(type1ReportHeaderData.getId());
					type1ReportDataDao.save(type1ReportData2);
				}
			}
			double overallTotal = 0;
			double rebate = 0;
			double rebate1 = 0;
			double rebate2 = 0;
			overallTotal = type1Report.getMonthlyFee() + type1Report.getStaticIpFee();
			Type1ReportManualEntries type1ReportManualEntries = null;
			if (null != type1ProcessModel.getType1process().getEntries()
					&& !type1ProcessModel.getType1process().getEntries().isEmpty()) {
				for (ManualFields manualFields : type1ProcessModel.getType1process().getEntries()) {
					if (manualFields.getId() == Type1EntryEnum.CrewDataMb.getId()) {
						// rebate1 = 0.05 * manualFields.getEntryCostPerUnit() *
						// manualFields.getEntryUnits();
						/*
						 * rebate1 =
						 * type1ProcessModel.getType1process().getDataRebate()
						 * manualFields.getEntryCostPerUnit() *
						 * manualFields.getEntryUnits();
						 */
						rebate1 = type1ProcessModel.getType1process().getShip().getDataRebate()
								* manualFields.getEntryCostPerUnit() * manualFields.getEntryUnits();
						bigDecimal = new BigDecimal(rebate1);
						roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
						rebate1 = roundedWithScale.doubleValue();
					} else if (manualFields.getId() == Type1EntryEnum.CrewVoipMinutes.getId()) {
						// rebate2 = 0 * manualFields.getEntryCostPerUnit() *
						// manualFields.getEntryUnits();
						/*
						 * rebate2 =
						 * type1ProcessModel.getType1process().getVoiceRebate()
						 * manualFields.getEntryCostPerUnit() *
						 * manualFields.getEntryUnits();
						 */
						rebate2 = type1ProcessModel.getType1process().getShip().getVoiceRebate()
								* manualFields.getEntryCostPerUnit() * manualFields.getEntryUnits();
						bigDecimal = new BigDecimal(rebate2);
						roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
						rebate2 = roundedWithScale.doubleValue();
					}
					bigDecimal = new BigDecimal(manualFields.getEntryCostPerUnit() * manualFields.getEntryUnits()
							+ (manualFields.getId() == Type1EntryEnum.IridiumCitadel.getId()
									? type1ProcessModel.getType1process().getShip().getIridiumCitadelMonthlyFee() : 0));
					roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
					type1ReportManualEntries = new Type1ReportManualEntries();
					type1ReportManualEntries.setManualEntryId(manualFields.getId());
					type1ReportManualEntries.setManualEntryValue(roundedWithScale.doubleValue());
					type1ReportManualEntries.setManualEntryCostPerUnit(manualFields.getEntryCostPerUnit());
					type1ReportManualEntries.setManualEntryUnit(manualFields.getEntryUnits());
					type1ReportManualEntries.setReportId(type1Report.getId());
					type1ReportManualEntriesDao.save(type1ReportManualEntries);
					if (manualFields.getId() == Type1EntryEnum.SatC.getId()
							|| manualFields.getId() == Type1EntryEnum.Email.getId()
							|| manualFields.getId() == Type1EntryEnum.SMS.getId()
							|| manualFields.getId() == Type1EntryEnum.PDN.getId()
							|| manualFields.getId() == Type1EntryEnum.IridiumCitadel.getId()
							|| manualFields.getId() == Type1EntryEnum.IridiumOpenport.getId()
							|| manualFields.getId() == Type1EntryEnum.SeabrowserCards.getId()) {
						overallTotal += type1ReportManualEntries.getManualEntryValue();
					}
				}
			}
			/*
			 * rebate = -(rebate1 + rebate2); bigDecimal = new
			 * BigDecimal(rebate); roundedWithScale = bigDecimal.setScale(2,
			 * BigDecimal.ROUND_UP); rebate = roundedWithScale.doubleValue();
			 */
			overallTotal += type1Report.getBusinessLineCalls();
			rebate = rebate1 + rebate2;
			overallTotal -= rebate;
			bigDecimal = new BigDecimal(rebate);
			roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
			rebate = roundedWithScale.doubleValue();
			bigDecimal = new BigDecimal(overallTotal);
			roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
			overallTotal = roundedWithScale.doubleValue();
			type1Report.setRebate(rebate);
			type1Report.setRebate1(rebate1);
			type1Report.setRebate2(rebate2);
			type1Report.setOverallTotal(overallTotal);
			type1Report.setDataRebate(type1ProcessModel.getType1process().getShip().getDataRebate());
			type1Report.setVoiceRebate(type1ProcessModel.getType1process().getShip().getVoiceRebate());
			type1ReportDao.save(type1Report);
			String reportPath = generateType1Report(type1Report.getId());
			if (null == reportPath) {
				throw new SuperException("Report Not found");
			}
			return reportPath;
		} catch (IOException e) {
			e.printStackTrace();
			throw new SuperException("Unable to read file");
		}
	}

	@Override
	public String generateType1Report(int reportId) throws SuperException {
		Type1Report type1Report = type1ReportDao.findOne(reportId);
		if (null == type1Report) {
			throw new SuperException("Report Not found");
		}
		Ship ship = shipDao.findById(type1Report.getShipId());
		List<Type1ReportHeaderData> type1ReportHeaderDatas = type1ReportHeaderDataDao.findByReportId(reportId);
		List<Type1ReportData> type1ReportDatas = type1ReportDataDao.findByReportId(reportId);
		List<Type1ReportManualEntries> type1ReportManualEntriesList = type1ReportManualEntriesDao
				.findByReportId(reportId);
		List<ManualFields> manualFieldsList = manualFieldsDao.findByAccountType(AccountType.Type_1.getAccountCode());
		SortedMap<Integer, Type1ReportHeaderData> type1ReportHeaderDataMap = new TreeMap<Integer, Type1ReportHeaderData>();
		Map<Integer, List<Type1ReportData>> type1ReportDataMap = new HashMap<Integer, List<Type1ReportData>>();
		Map<Integer, String> manualEntriesMap = new HashMap<Integer, String>();
		Map<Integer, String> type1ManualEntriesMap = new HashMap<Integer, String>();
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		for (Type1ReportHeaderData type1ReportHeaderData : type1ReportHeaderDatas) {
			type1ReportHeaderDataMap.put(type1ReportHeaderData.getId(), type1ReportHeaderData);
		}
		for (Type1ReportData type1ReportData : type1ReportDatas) {
			if (!type1ReportDataMap.containsKey(type1ReportData.getHeaderId())) {
				type1ReportDataMap.put(type1ReportData.getHeaderId(), new ArrayList<Type1ReportData>());
			}
			type1ReportDataMap.get(type1ReportData.getHeaderId()).add(type1ReportData);
		}
		for (ManualFields manualFields : manualFieldsList) {
			manualEntriesMap.put(manualFields.getId(), manualFields.getEntryName());
		}
		double totalData = 0;
		for (Type1ReportManualEntries type1ReportManualEntries : type1ReportManualEntriesList) {
			if (type1ReportManualEntries.getManualEntryId() == Type1EntryEnum.CrewDataMb.getId()
					|| type1ReportManualEntries.getManualEntryId() == Type1EntryEnum.BusinessDataMb.getId()) {
				totalData += type1ReportManualEntries.getManualEntryValue();
			}
			type1ManualEntriesMap.put(type1ReportManualEntries.getManualEntryId(),
					decimalFormat.format(type1ReportManualEntries.getManualEntryValue()));
		}
		/*
		 * public Type1ReportJasper(String monthName, double monthlyFee, double
		 * staticIpFee, String createdBy, String reportName, String year, double
		 * businessLineCalls, double rebate, double rebate1, double rebate2,
		 * double overallTotal, Map<String, Double> manualEntry, Map<String,
		 * String> manualEntryName) {
		 */

		Type1ReportJasper type1ReportJasper = new Type1ReportJasper(type1Report.getMonth(),
				decimalFormat.format(type1Report.getMonthlyFee()), decimalFormat.format(type1Report.getStaticIpFee()),
				type1Report.getCreatedBy(), type1Report.getReportName(), type1Report.getYear(),
				decimalFormat.format(type1Report.getBusinessLineCalls()), decimalFormat.format(type1Report.getRebate()),
				decimalFormat.format(type1Report.getRebate1()), decimalFormat.format(type1Report.getRebate2()),
				decimalFormat.format(type1Report.getOverallTotal()), type1ManualEntriesMap, manualEntriesMap,
				decimalFormat.format(totalData));
		List<Type1ReportDataJasper> type1ReportDataJaspers = new ArrayList<Type1ReportDataJasper>();
		Type1ReportDataJasper type1ReportDataJasper = null;
		for (Integer headerId : type1ReportHeaderDataMap.keySet()) {
			for (Type1ReportData type1ReportData : type1ReportDataMap.get(headerId)) {
				type1ReportDataJasper = new Type1ReportDataJasper(type1ReportData.getTime(),
						type1ReportData.getNetwork(), type1ReportData.getFrom_msisdn(), type1ReportData.getTo_msisdn(),
						type1ReportData.getDuration(), decimalFormat.format(type1ReportData.getPrice()),
						type1ReportData.getRate(), type1ReportHeaderDataMap.get(headerId).getHeaderName());
				type1ReportDataJaspers.add(type1ReportDataJasper);
			}
		}
		/*
		 * type1ReportJasper.setType1ReportDataJaspers(type1ReportDataJaspers);
		 * List<Type1ReportManualEntriesJasper> type1ReportManualEntriesJaspers
		 * = new ArrayList<Type1ReportManualEntriesJasper>();
		 * Type1ReportManualEntriesJasper type1ReportManualEntriesJasper = null;
		 * for (Integer type1ManualEntryId : type1ManualEntriesMap.keySet()) {
		 * type1ReportManualEntriesJasper = new Type1ReportManualEntriesJasper(
		 * manualEntriesMap.containsKey(type1ManualEntriesMap.get(
		 * type1ManualEntryId).getManualEntryId()) ?
		 * manualEntriesMap.get(type1ManualEntriesMap.get(type1ManualEntryId).
		 * getManualEntryId()) : "",
		 * type1ManualEntriesMap.get(type1ManualEntryId).getManualEntryValue());
		 * type1ReportManualEntriesJaspers.add(type1ReportManualEntriesJasper);
		 * } type1ReportJasper.setType1ManualEntriesJaspers(
		 * type1ReportManualEntriesJaspers);
		 */List<Type1ReportJasper> type1ReportJaspers = new ArrayList<Type1ReportJasper>();
		type1ReportJaspers.add(type1ReportJasper);
		List<String> jasperName = new ArrayList<String>();
		jasperName.add("Type1Report");
		jasperName.add("Type1Report_subreport2");
		List<List> dataList = new ArrayList<List>();
		List dataList2 = new ArrayList();
		dataList.add(type1ReportJaspers);
		dataList.add(type1ReportDataJaspers);
		List<Map> parametersMap = new ArrayList<Map>();
		Map<String, Object> parameterMap1 = new HashMap<String, Object>();
		parameterMap1.put("SHIP_NAME", ship.getName());
		Map<String, Object> parameterMap2 = new HashMap<String, Object>();
		parameterMap2.put("SHIP_NAME", ship.getName());
		parameterMap2.put("MONTH", type1Report.getMonth());
		parameterMap2.put("YEAR", type1Report.getYear());
		parametersMap.add(parameterMap1);
		parametersMap.add(parameterMap2);
		return jasperService.generateReport2PDF(jasperName, dataList, parametersMap, "Type1Report.pdf");
	}

	@Override
	public String saveReportDataType2(Type1ProcessModel type1ProcessModel) throws SuperException {

		if (null == type1ProcessModel.getType1process().getShipModels()
				|| type1ProcessModel.getType1process().getShipModels().isEmpty()) {
			throw new SuperException("Ship not found");
		}
		File file = new File(type1ProcessModel.getFilePath());
		if (!file.exists()) {
			throw new SuperException("Please specify ship");
		}
		FileInputStream excelFile = null;
		Workbook workbook = null;
		Sheet datatypeSheet = null;
		Iterator<Row> iterator = null;
		String cdrId = null;
		String msisdn = null;
		String imsi = null;
		Date callStart = null;
		String description = null;
		String bNumber = null;
		String bundleIndicator = null;
		double unitPrice = 0;
		double quantityCharged = 0;
		double quantity = 0;
		String callType = null;
		double resellerPrice = 0;
		String billingPeriod = null;
		String eventId = null;
		String mobileNumber;
		String otherPartyMsisdn;
		String duration = null;
		String callDate = null;
		String callTime = null;
		double totalPrice;
		double price1 = 0;
		double price2 = 0;
		String imsiPattern = "\\d{15}";
		boolean showInReport = true;
		Date callStartDate = null;
		/*
		 * SimpleDateFormat callStartFormat = new SimpleDateFormat(
		 * "MMM dd, yyyy HH:mm:ss a");
		 */
		SimpleDateFormat callStartFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		SimpleDateFormat callDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat callTimeFormat = new SimpleDateFormat("HH:mm:ss");
		HashMap<Integer, List<Type2ReportData>> type2ReportDatas = new HashMap<Integer, List<Type2ReportData>>();
		Type2ReportData type2ReportData = null;
		BigDecimal bigDecimal;
		BigDecimal roundedWithScale;
		List<Type2Rate> type2Rates = (List<Type2Rate>) type2RateDao.findAll();
		Map<String, Type2Rate> type2ratesMap = new HashMap<String, Type2Rate>();
		HashMap<Integer, Double> shipReportCallPrice = new HashMap<Integer, Double>();
		Type2Rate type2RateFetch = null;
		for (Type2Rate type2Rate : type2Rates) {
			type2ratesMap.put(type2Rate.getRateEntryName(), type2Rate);
		}
		Map<String, Integer> imeiListMapping = new HashMap<String, Integer>();
		Map<Integer, Double> price1TypeMap = new HashMap<Integer, Double>();
		Map<Integer, Double> price2TypeMap = new HashMap<Integer, Double>();
		for (Type2ShipModel type2ShipModel : type1ProcessModel.getType1process().getShipModels()) {
			price1TypeMap.put(type2ShipModel.getShip().getId(), type2ShipModel.getShip().getVoiceToCellular());
			price2TypeMap.put(type2ShipModel.getShip().getId(), type2ShipModel.getShip().getVoiceToTerrestial());
			if (null != type2ShipModel.getShip().getImsi1() && !type2ShipModel.getShip().getImsi1().equals("")) {
				imeiListMapping.put(type2ShipModel.getShip().getImsi1(), type2ShipModel.getShip().getId());
			}
			if (null != type2ShipModel.getShip().getImsi2() && !type2ShipModel.getShip().getImsi2().equals("")) {
				imeiListMapping.put(type2ShipModel.getShip().getImsi2(), type2ShipModel.getShip().getId());
			}
		}
		try {

			excelFile = new FileInputStream(file);
			workbook = new HSSFWorkbook(excelFile);
			datatypeSheet = workbook.getSheetAt(0);
			iterator = datatypeSheet.iterator();
			int shipId = 0;
			int recordNo = 0;
			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				int rowNumber = currentRow.getRowNum();
				if (rowNumber == 0) {
					continue;
				}
				cdrId = "";
				msisdn = "";
				imsi = "";
				billingPeriod = "";
				callStart = null;
				callDate = "";
				callTime = "";
				eventId = "";
				description = "";
				bNumber = "";
				bundleIndicator = "";
				unitPrice = 0;
				quantityCharged = 0;
				quantity = 0;
				callType = "";
				resellerPrice = 0;
				shipId = 0;
				price1 = 0;
				price2 = 0;
				showInReport = true;
				Iterator<Cell> cellIterator = currentRow.iterator();
				type2ReportData = new Type2ReportData();
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					int columnIndex = currentCell.getColumnIndex();
					switch (columnIndex) {
					case 0:
						currentCell.setCellType(Cell.CELL_TYPE_STRING);
						cdrId = currentCell.getStringCellValue();
						break;
					case 1:
						currentCell.setCellType(Cell.CELL_TYPE_STRING);
						msisdn = currentCell.getStringCellValue();
						break;
					case 2:
						currentCell.setCellType(Cell.CELL_TYPE_STRING);
						imsi = currentCell.getStringCellValue().trim();
						showInReport = imsi.matches(imsiPattern);
						if (showInReport && !imeiListMapping.containsKey(imsi)) {
							throw new SuperException("Some of Imei not bound to ship");
						}
						shipId = imeiListMapping.containsKey(imsi) ? imeiListMapping.get(imsi) : 0;
						price1 = price1TypeMap.containsKey(shipId) ? price1TypeMap.get(shipId) : 0;
						price2 = price2TypeMap.containsKey(shipId) ? price1TypeMap.get(shipId) : 0;
						break;
					/*
					 * case 3: currentCell.setCellType(Cell.CELL_TYPE_STRING);
					 * billingPeriod = currentCell.getStringCellValue(); break;
					 * case 4: callStart = currentCell.getStringCellValue(); try
					 * { callDate = callDateFormat .format((Date)
					 * callStartFormat.parse(currentCell.getStringCellValue()));
					 * callTime = callTimeFormat .format((Date)
					 * callStartFormat.parse(currentCell.getStringCellValue()));
					 * } catch (ParseException e) { // TODO Auto-generated catch
					 * block e.printStackTrace(); } break; case 5:
					 * currentCell.setCellType(Cell.CELL_TYPE_STRING); eventId =
					 * currentCell.getStringCellValue(); break; case 6:
					 * description = currentCell.getStringCellValue(); break;
					 * case 7: currentCell.setCellType(Cell.CELL_TYPE_STRING);
					 * bNumber = currentCell.getStringCellValue(); break; case
					 * 8: bundleIndicator = currentCell.getStringCellValue();
					 * break; case 9: unitPrice =
					 * Double.valueOf(currentCell.getStringCellValue()); break;
					 * case 10: quantityCharged =
					 * Double.valueOf(currentCell.getStringCellValue());
					 * duration = getDuration((int) quantityCharged); break;
					 * case 11: quantity =
					 * Double.valueOf(currentCell.getStringCellValue()); break;
					 * case 12: callType = currentCell.getStringCellValue();
					 * break; case 13: resellerPrice =
					 * Double.valueOf(currentCell.getStringCellValue()); break;
					 */
					case 3:
						callStart = currentCell.getDateCellValue();
						try {
							callDate = callDateFormat.format((Date) callStartFormat.parse(callStart.toString()));
							callTime = callTimeFormat.format((Date) callStartFormat.parse(callStart.toString()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case 4:
						description = currentCell.getStringCellValue();
						break;
					case 5:
						currentCell.setCellType(Cell.CELL_TYPE_STRING);
						bNumber = currentCell.getStringCellValue();
						break;
					case 6:
						bundleIndicator = currentCell.getStringCellValue();
						break;
					case 7:
						unitPrice = currentCell.getNumericCellValue();
						break;
					case 8:
						quantityCharged = currentCell.getNumericCellValue();
						duration = getDuration((int) quantityCharged);
						break;
					case 9:
						quantity = currentCell.getNumericCellValue();
						break;
					case 10:
						callType = currentCell.getStringCellValue();
						break;
					case 11:
						resellerPrice = currentCell.getNumericCellValue();
						break;
					}

				}

				if ((description.trim().toUpperCase().equals("BACKGROUND IP")
						&& callType.trim().toUpperCase().equals("DATA")) || imsi.equals("")) {
					showInReport = false;
				}
				if ((description.trim().toUpperCase().equals("BACKGROUND IP")
						&& bundleIndicator.trim().toUpperCase().equals("B")) || imsi.equals("")) {
					showInReport = false;
				}

				type2RateFetch = type2ratesMap.get(description.trim());
				totalPrice = (type2RateFetch == null || type2RateFetch.getRateType() == Type2RateEnum.NoRate.getType()
						? 0
						: (type2RateFetch.getRateType() == Type2RateEnum.Price1.getType())
								? type2RateFetch.getMultiplier() * price1 : type2RateFetch.getMultiplier() * price2)
						* (quantityCharged / 60);

				/*
				 * totalPrice = (description.trim().equals(
				 * "Basic voice -FBB/PSTN") ? price1 :
				 * description.trim().equals("Basic voice -FBB/Cellular") ?
				 * price2 : description.trim().equals("Fax group 3") ? price2 *
				 * 13 : description.trim().equals("Iridium voice") ? price2 * 21
				 * : description.trim().equals("Inmarsat Mini-M") ? price2 * 6 :
				 * description.trim().equals("Basic voice - FBB/FBB") ? price2 :
				 * 0) (quantityCharged / 60);
				 */
				bigDecimal = new BigDecimal(totalPrice);
				roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
				if (!shipReportCallPrice.containsKey(shipId)) {
					shipReportCallPrice.put(shipId, 0.0);
				}
				if (showInReport) {
					shipReportCallPrice.put(shipId, shipReportCallPrice.get(shipId) + roundedWithScale.doubleValue());
				}
				type2ReportData = new Type2ReportData(0, 0, cdrId, msisdn, imsi, callStart.toString(), description,
						bNumber, bundleIndicator, unitPrice, quantityCharged, callType, resellerPrice, msisdn, bNumber,
						duration, callDate, callTime, roundedWithScale.doubleValue(), price1, price2, billingPeriod,
						eventId, showInReport ? 1 : 0, quantity, ++recordNo);
				if (!type2ReportDatas.containsKey(shipId)) {
					type2ReportDatas.put(shipId, new ArrayList<Type2ReportData>());
				}
				type2ReportDatas.get(shipId).add(type2ReportData);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Type2Parent type2Parent = new Type2Parent();
		type2ParentDao.save(type2Parent);
		if (type2ReportDatas.containsKey(0)) {
			Type2ShipModel type2ShipModel = new Type2ShipModel();
			type2ShipModel.setShip(new Ship(0, "Default", 0, 0, 0, 0, 0, 0, "", "", 0, "", 0, "", 0, 0));
			type1ProcessModel.getType1process().getShipModels().add(type2ShipModel);
		}
		for (Type2ShipModel type2ShipModel : type1ProcessModel.getType1process().getShipModels()) {
			bigDecimal = new BigDecimal(type2ShipModel.getShip().getMonthlyFee());
			roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
			double monthlyFee = roundedWithScale.doubleValue();
			bigDecimal = new BigDecimal(type2ShipModel.getShip().getStaticIpFee());
			roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
			double staticIpFee = roundedWithScale.doubleValue();
			Type1Report type1Report = new Type1Report();
			type1Report.setCreatedBy(type1ProcessModel.getUsername());
			type1Report.setMonthlyFee(monthlyFee);
			type1Report.setShipId(type2ShipModel.getShip().getId());
			type1Report.setStaticIpFee(staticIpFee);
			type1Report.setReportName(type1ProcessModel.getFileName());
			type1Report.setMonth(type1ProcessModel.getMonth());
			type1Report.setYear(type1ProcessModel.getYear());
			type1Report.setCreatedTime(Calendar.getInstance());
			type1Report.setDataRebate(type2ShipModel.getShip().getDataRebate());
			type1Report.setVoiceRebate(type2ShipModel.getShip().getVoiceRebate());
			bigDecimal = new BigDecimal(shipReportCallPrice.containsKey(type2ShipModel.getShip().getId())
					? shipReportCallPrice.get(type2ShipModel.getShip().getId()) : 0.0);
			roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
			type1Report.setBusinessLineCalls(roundedWithScale.doubleValue());
			type1Report.setType2ReportId(type2Parent.getId());
			type1ReportDao.save(type1Report);
			if (type2ReportDatas.containsKey(type2ShipModel.getShip().getId())) {
				for (Type2ReportData type2ReportData1 : type2ReportDatas.get(type2ShipModel.getShip().getId())) {
					type2ReportData1.setReportId(type1Report.getId());
					// type2ReportDataDao.save(type2ReportData1);
				}
				type2ReportDataDao.save(type2ReportDatas.get(type2ShipModel.getShip().getId()));
			}
			Type1ReportManualEntries type1ReportManualEntries = null;
			double overallTotal = 0;
			double rebate = 0;
			double rebate1 = 0;
			double rebate2 = 0;
			overallTotal = type1Report.getMonthlyFee() + type1Report.getStaticIpFee();
			if (null != type2ShipModel.getManualFields() && !type2ShipModel.getManualFields().isEmpty()) {
				for (ManualFields manualFields : type2ShipModel.getManualFields()) {
					if (manualFields.getId() == Type2EntryEnum.CrewDataMb.getId()) {
						/*
						 * rebate1 = 0.1 * manualFields.getEntryCostPerUnit();
						 */
						rebate1 = type2ShipModel.getShip().getDataRebate() * manualFields.getEntryCostPerUnit();
						bigDecimal = new BigDecimal(rebate1);
						roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
						rebate1 = roundedWithScale.doubleValue();
					} else if (manualFields.getId() == Type2EntryEnum.CrewVoipMinutes.getId()) {
						/*
						 * rebate2 = 0.08 * manualFields.getEntryCostPerUnit();
						 */
						rebate2 = type2ShipModel.getShip().getVoiceRebate() * manualFields.getEntryCostPerUnit();
						bigDecimal = new BigDecimal(rebate2);
						roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
						rebate2 = roundedWithScale.doubleValue();
					}
					type1ReportManualEntries = new Type1ReportManualEntries();
					bigDecimal = new BigDecimal(manualFields.getEntryCostPerUnit());
					roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
					type1ReportManualEntries.setManualEntryId(manualFields.getId());
					type1ReportManualEntries.setManualEntryValue(bigDecimal.doubleValue());
					type1ReportManualEntries.setReportId(type1Report.getId());
					type1ReportManualEntriesDao.save(type1ReportManualEntries);
				}
			}
			rebate = -(rebate1 + rebate2);
			bigDecimal = new BigDecimal(rebate);
			roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
			rebate = roundedWithScale.doubleValue();
			overallTotal += type1Report.getBusinessLineCalls();
			overallTotal += rebate;
			rebate = rebate1 + rebate2;
			bigDecimal = new BigDecimal(overallTotal);
			roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
			overallTotal = roundedWithScale.doubleValue();
			type1Report.setRebate(rebate);
			type1Report.setRebate1(rebate1);
			type1Report.setRebate2(rebate2);
			type1Report.setOverallTotal(overallTotal);
			type1ReportDao.save(type1Report);
		}

		String reportPath = generateType2Report(type2Parent.getId());
		if (null == reportPath) {
			throw new SuperException("Report Not found");
		}
		return reportPath;
	}

	@Override
	public String generateType2Report(int parentReportId) throws SuperException {
		Type2Parent type2Parent = type2ParentDao.findOne(parentReportId);
		if (null == type2Parent) {
			throw new SuperException("Report Not found");
		}
		List<Type1Report> type2Reports = type1ReportDao.findByType2ReportId(type2Parent.getId());
		if (null == type2Reports || type2Reports.isEmpty()) {
			throw new SuperException("Report Not found");
		}
		Ship ship = null;
		List<Type2ReportData> type2ReportDatas = null;
		List<Type1ReportManualEntries> type2ReportManualEntriesList = null;
		Map<Integer, String> manualEntriesMap = null;
		List<ManualFields> manualFieldsList = null;
		Map<Integer, String> type1ManualEntriesMap = null;
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		double totalData = 0;
		List<String> shipReportsName = new ArrayList<String>();
		Type1ReportJasper type2ReportJasper = null;
		List<Type2ReportDataJasper> type2ReportDataJaspers = null;
		Type2ReportDataJasper type2ReportDataJasper = null;
		List<Type1ReportJasper> type2ReportJaspers = null;
		List<List> dataList = null;
		List dataList2 = null;
		List<Map> parametersMap = null;
		Map<String, Object> parameterMap1 = null;
		Map<String, Object> parameterMap2 = null;

		for (Type1Report type2Report : type2Reports) {
			ship = shipDao.findById(type2Report.getShipId());
			if (null == ship) {
				continue;
			}
			type2ReportDatas = type2ReportDataDao.findByReportId(type2Report.getId());
			type2ReportManualEntriesList = type1ReportManualEntriesDao.findByReportId(type2Report.getId());
			manualFieldsList = manualFieldsDao.findByAccountType(AccountType.Type_2.getAccountCode());
			manualEntriesMap = new HashMap<Integer, String>();
			type1ManualEntriesMap = new HashMap<Integer, String>();

			for (ManualFields manualFields : manualFieldsList) {
				manualEntriesMap.put(manualFields.getId(), manualFields.getEntryName());
			}
			totalData = 0;
			for (Type1ReportManualEntries type2ReportManualEntries : type2ReportManualEntriesList) {
				if (type2ReportManualEntries.getManualEntryId() == Type2EntryEnum.CrewDataMb.getId()
						|| type2ReportManualEntries.getManualEntryId() == Type2EntryEnum.VoIPDataUseMb.getId()
						|| type2ReportManualEntries.getManualEntryId() == Type2EntryEnum.BusinessDataMb.getId()
						|| type2ReportManualEntries.getManualEntryId() == Type2EntryEnum.OtherDataUseMb.getId()) {
					totalData += type2ReportManualEntries.getManualEntryValue();
				}
				type1ManualEntriesMap.put(type2ReportManualEntries.getManualEntryId(),
						decimalFormat.format(type2ReportManualEntries.getManualEntryValue()));
			}
			/*
			 * public Type1ReportJasper(String monthName, double monthlyFee,
			 * double staticIpFee, String createdBy, String reportName, String
			 * year, double businessLineCalls, double rebate, double rebate1,
			 * double rebate2, double overallTotal, Map<String, Double>
			 * manualEntry, Map<String, String> manualEntryName) {
			 */

			type2ReportJasper = new Type1ReportJasper(type2Report.getMonth(),
					decimalFormat.format(type2Report.getMonthlyFee()),
					decimalFormat.format(type2Report.getStaticIpFee()), type2Report.getCreatedBy(),
					type2Report.getReportName(), type2Report.getYear(),
					decimalFormat.format(type2Report.getBusinessLineCalls()),
					decimalFormat.format(type2Report.getRebate()), decimalFormat.format(type2Report.getRebate1()),
					decimalFormat.format(type2Report.getRebate2()), decimalFormat.format(type2Report.getOverallTotal()),
					type1ManualEntriesMap, manualEntriesMap, decimalFormat.format(totalData));
			type2ReportDataJaspers = new ArrayList<Type2ReportDataJasper>();

			for (Type2ReportData type2ReportData : type2ReportDatas) {
				if (type2ReportData.getShowInReport() == 0) {
					continue;
				}
				type2ReportDataJasper = new Type2ReportDataJasper(type2ReportData.getCdrId(),
						type2ReportData.getMsisdn(), type2ReportData.getImsi(), type2ReportData.getCallStart(),
						type2ReportData.getDescription(), type2ReportData.getbNumber(),
						type2ReportData.getBundleIndicator(), decimalFormat.format(type2ReportData.getUnitPrice()),
						decimalFormat.format(type2ReportData.getQuantityCharged()), type2ReportData.getCallType(),
						decimalFormat.format(type2ReportData.getIspPrice()), type2ReportData.getMobileNumber(),
						type2ReportData.getOtherPartyMsisdn(), type2ReportData.getDuration(),
						type2ReportData.getCallDate(), type2ReportData.getCallTime(),
						decimalFormat.format(type2ReportData.getTotalPrice()),
						decimalFormat.format(type2ReportData.getPrice1()),
						decimalFormat.format(type2ReportData.getPrice2()));
				type2ReportDataJaspers.add(type2ReportDataJasper);
			}
			Collections.sort(type2ReportDataJaspers, new Comparator<Type2ReportDataJasper>() {
				@Override
				public int compare(Type2ReportDataJasper o1, Type2ReportDataJasper o2) {

					return o1.getImsi().compareTo(o2.getImsi());
				}
			});

			type2ReportJaspers = new ArrayList<Type1ReportJasper>();
			type2ReportJaspers.add(type2ReportJasper);
			List<String> jasperName = new ArrayList<String>();
			jasperName.add("Type2Report");
			jasperName.add("Type2Report_subreport2");
			dataList = new ArrayList<List>();
			dataList2 = new ArrayList();
			dataList.add(type2ReportJaspers);
			dataList.add(type2ReportDataJaspers);
			parametersMap = new ArrayList<Map>();
			parameterMap1 = new HashMap<String, Object>();
			parameterMap1.put("SHIP_NAME", ship.getName());
			parameterMap2 = new HashMap<String, Object>();
			parameterMap2.put("SHIP_NAME", ship.getName());
			parameterMap2.put("MONTH", type2Report.getMonth());
			parameterMap2.put("YEAR", type2Report.getYear());
			parametersMap.add(parameterMap1);
			parametersMap.add(parameterMap2);
			shipReportsName.add(
					jasperService.generateReport2PDF(jasperName, dataList, parametersMap, (ship.getName() + ".pdf")));
		}
		String reportFileName = servletContext.getRealPath("/jrxml") + File.separator + "Type2Report.zip";
		File zipFile = new File(reportFileName);
		ZipOutputStream out = null;
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

		return reportFileName;
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

	private String getDuration(int secs) {
		int minutes = secs / 60;
		int seconds = secs % 60;
		String durationStr = (minutes < 10 ? "0" + minutes : (String.valueOf(minutes))) + ":"
				+ (seconds < 10 ? "0" + seconds : (String.valueOf(seconds)));
		return durationStr;
	}

	@Override
	public String checkAllImsiExists(String filePath) throws SuperException {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new SuperException("File Not found");
		}
		FileInputStream excelFile = null;
		Workbook workbook = null;
		Sheet datatypeSheet = null;
		Iterator<Row> iterator = null;
		String imsi = null;
		List<Ship> ships = (List<Ship>) shipDao.findByAccountType(AccountType.Type_2.getAccountCode());
		if (null == ships || ships.isEmpty()) {
			throw new SuperException("Vessels Not found");
		}
		List<String> shipsImsiList = new ArrayList<String>();
		List<String> alreadyAddedImei = new ArrayList<String>();
		for (Ship ship : ships) {
			if (null != ship.getImsi1() && !ship.getImsi1().equals("")) {
				shipsImsiList.add(ship.getImsi1());
			}
			if (null != ship.getImsi2() && !ship.getImsi2().equals("")) {
				shipsImsiList.add(ship.getImsi2());
			}
		}
		StringBuffer missingImsiList = new StringBuffer();
		String imsiPattern = "\\d{15}";
		try {

			excelFile = new FileInputStream(file);
			workbook = new HSSFWorkbook(excelFile);
			datatypeSheet = workbook.getSheetAt(0);
			iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				int rowNumber = currentRow.getRowNum();
				if (rowNumber == 0) {
					continue;
				}
				Iterator<Cell> cellIterator = currentRow.iterator();
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					int columnIndex = currentCell.getColumnIndex();
					switch (columnIndex) {

					case 2:
						currentCell.setCellType(Cell.CELL_TYPE_STRING);
						imsi = currentCell.getStringCellValue();
						if (null != imsi && !imsi.equals("")) {
							if (!imsi.matches(imsiPattern)) {
								continue;
							}
							if (!shipsImsiList.contains(imsi.trim())) {
								if (!alreadyAddedImei.contains(imsi.trim())) {
									alreadyAddedImei.add(imsi.trim());
									missingImsiList.append(imsi.trim() + "\n");
								}
							}
						}

						break;
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return missingImsiList.toString();
	}

	@Override
	public String generateType1ActualReport(int reportId) throws SuperException {
		Type1Report type1Report = type1ReportDao.findOne(reportId);
		if (null == type1Report) {
			throw new SuperException("Report Not found");
		}
		List<Type1ReportHeaderData> type1ReportHeaderDatas = type1ReportHeaderDataDao.findByReportId(reportId);
		List<Type1ReportData> type1ReportDatas = type1ReportDataDao.findByReportId(reportId);
		Map<Integer, List<Type1ReportData>> type1reportDataMap = new HashMap<Integer, List<Type1ReportData>>();
		for (Type1ReportData type1ReportData : type1ReportDatas) {
			if (!type1reportDataMap.containsKey(type1ReportData.getHeaderId())) {
				type1reportDataMap.put(type1ReportData.getHeaderId(), new ArrayList<Type1ReportData>());
			}
			type1reportDataMap.get(type1ReportData.getHeaderId()).add(type1ReportData);
		}
		String filePath = servletContext.getRealPath("/jrxml") + File.separator + type1Report.getReportName();
		FileWriter fw = null;
		BufferedWriter bw = null;

		try {

			fw = new FileWriter(filePath);
			bw = new BufferedWriter(fw);
			for (Type1ReportHeaderData type1ReportHeaderData : type1ReportHeaderDatas) {
				bw.write(type1ReportHeaderData.getActualHeaderName() + "\n");
				type1ReportDatas = type1reportDataMap.get(type1ReportHeaderData.getId());
				if (null != type1ReportDatas) {
					for (Type1ReportData type1ReportData : type1ReportDatas) {
						bw.write(type1ReportData.getTime() + "  " + type1ReportData.getNetwork() + "     "
								+ type1ReportData.getFrom_msisdn() + "    " + type1ReportData.getTo_msisdn() + "   "
								+ type1ReportData.getDuration() + "    " + type1ReportData.getRate() + "\n");
					}
				}
				bw.write("\n");
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

		return filePath;
	}

	@Override
	public String generateType2ActualReport(int reportId) throws SuperException {
		Type2Parent type2Parent = type2ParentDao.findOne(reportId);
		if (null == type2Parent) {
			throw new SuperException("Report Not found");
		}
		List<Type1Report> type2Reports = type1ReportDao.findByType2ReportId(reportId);
		List<Integer> reportIds = new ArrayList<Integer>();
		for (Type1Report type2Report : type2Reports) {
			reportIds.add(type2Report.getId());
		}
		List<Type2ReportData> type2ReportDatas = type2ReportDataDao.findByReportId(reportIds);

		String filePath = servletContext.getRealPath("/jrxml") + File.separator + type2Reports.get(0).getReportName();
		FileWriter fw = null;
		BufferedWriter bw = null;

		try {
			// Create blank workbook
			HSSFWorkbook workbook = new HSSFWorkbook();
			// Create a blank sheet
			HSSFSheet spreadsheet = workbook.createSheet("CDR Info");
			// Create row object
			HSSFRow row;
			int rowid = 0;
			Map<Integer, Object[]> cdrMap = new TreeMap<Integer, Object[]>();
			/*
			 * cdrMap.put(0, new Object[] { "CDR ID", "MSISDN", "IMSI",
			 * "Billing Period", "Call Start", "Event ID", "Description",
			 * "B Number", "Bundle Indicator", "Unit Price", "Quantity Charged",
			 * "Quantity", "Type", "Reseller Price" });
			 */
			cdrMap.put(0, new Object[] { "cdr_id", "msisdn", "imsi", "call_start", "description", "b_number",
					"bundle_indicator", "unit_price", "quantity_charged", "quantity", "Type", "isp_price" });
			String callStart = null;
			for (Type2ReportData type2ReportData : type2ReportDatas) {
				/*
				 * cdrMap.put(type2ReportData.getRecordNo(), new Object[] {
				 * type2ReportData.getCdrId(), type2ReportData.getMsisdn(),
				 * type2ReportData.getImsi(),
				 * type2ReportData.getBillingPeriod(),
				 * type2ReportData.getCallStart(), type2ReportData.getEventId(),
				 * type2ReportData.getDescription(),
				 * type2ReportData.getbNumber(),
				 * type2ReportData.getBundleIndicator(),
				 * type2ReportData.getUnitPrice(),
				 * type2ReportData.getQuantityCharged(),
				 * type2ReportData.getQuantity(), type2ReportData.getCallType(),
				 * type2ReportData.getIspPrice() });
				 */
				SimpleDateFormat callStartFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/YYYY HH:mm:ss a");	
				callStart = null;
				try {
					if (null != type2ReportData.getCallStart()) {
						callStart = formatter.format(callStartFormat.parse(type2ReportData.getCallStart()));
					}
				} catch (ParseException e) {
					callStart = null;
					e.printStackTrace();
				}
				cdrMap.put(type2ReportData.getRecordNo(),
						new Object[] { type2ReportData.getCdrId(), type2ReportData.getMsisdn(),
								type2ReportData.getImsi(), callStart == null ? "" : callStart,
								type2ReportData.getDescription(), type2ReportData.getbNumber(),
								type2ReportData.getBundleIndicator(), type2ReportData.getUnitPrice(),
								type2ReportData.getQuantityCharged(), type2ReportData.getQuantity(),
								type2ReportData.getCallType(), type2ReportData.getIspPrice() });
			}
			int rowId = 0;
			for (int recordNo : cdrMap.keySet()) {
				row = spreadsheet.createRow(rowid++);
				Object[] objectArr = cdrMap.get(recordNo);
				int cellid = 0;
				for (Object obj : objectArr) {
					Cell cell = row.createCell(cellid++);
					cell.setCellValue(String.valueOf(obj));
				}
			}
			FileOutputStream out = new FileOutputStream(new File(filePath));
			workbook.write(out);
			out.close();
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

		return filePath;
	}
}
