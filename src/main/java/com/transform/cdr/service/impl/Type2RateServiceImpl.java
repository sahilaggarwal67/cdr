package com.transform.cdr.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transform.cdr.dao.Type2RateDao;
import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.Type2Rate;
import com.transform.cdr.model.Type2RateModel;
import com.transform.cdr.model.Type2ReportData;
import com.transform.cdr.service.Type2RateService;
import com.transform.cdr.util.AccountType;
import com.transform.cdr.util.Type2RateEnum;

@Service("type2RateService")
public class Type2RateServiceImpl implements Type2RateService {

	@Autowired
	Type2RateDao type2RateDao;

	@Override
	public List<Type2RateModel> getUnlistedDescriptionRate(String reportPath) throws SuperException {
		File file = new File(reportPath);
		if (!file.exists()) {
			throw new SuperException("File Not found");
		}
		FileInputStream excelFile = null;
		Workbook workbook = null;
		Sheet datatypeSheet = null;
		Iterator<Row> iterator = null;
		String description = null;
		List<Type2Rate> type2Rates = (List<Type2Rate>) type2RateDao.findAll();
		Map<String, Type2Rate> type2ratesMap = new HashMap<String, Type2Rate>();
		for (Type2Rate type2Rate : type2Rates) {
			type2ratesMap.put(type2Rate.getRateEntryName(), type2Rate);
		}
		List<String> missingDescriptionList = new ArrayList<String>();
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

					case 6:
						description = currentCell.getStringCellValue();
						if (null != description && !description.equals("")) {
							if (!type2ratesMap.containsKey(description.trim())) {
								if (!missingDescriptionList.contains(description.trim())) {
									missingDescriptionList.add(description.trim());
								}
							}

							break;
						}
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (missingDescriptionList.isEmpty()) {
			return null;
		}
		List<Type2RateModel> type2RateDTOs = new ArrayList<Type2RateModel>();
		Type2RateModel type2RateDTO = null;
		for (String description2 : missingDescriptionList) {
			type2RateDTO = new Type2RateModel(description2, AccountType.Type_1.getAccountCode(), 1,
					Arrays.asList(Type2RateEnum.values()));
			type2RateDTOs.add(type2RateDTO);

		}
		return type2RateDTOs;
	}

	@Override
	public void saveType2Rate(List<Type2RateModel> rateList) {
		if (null == rateList || rateList.isEmpty()) {
			return;
		}
		Type2Rate type2Rate = null;
		for (Type2RateModel type2RateModel : rateList) {
			type2Rate = new Type2Rate(0, type2RateModel.getRateEntryName(), type2RateModel.getRateType(),
					type2RateModel.getMultiplier());
			type2RateDao.save(type2Rate);
		}
	}
}
