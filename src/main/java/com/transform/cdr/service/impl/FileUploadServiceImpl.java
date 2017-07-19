package com.transform.cdr.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.exolab.castor.types.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transform.cdr.dao.Type1ReportDao;
import com.transform.cdr.model.File;
import com.transform.cdr.model.FilesUploaded;
import com.transform.cdr.model.Type1Report;
import com.transform.cdr.service.FileUploadService;
import com.transform.cdr.util.MonthEnum;
import com.transform.cdr.util.Type;

@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	Type1ReportDao type1ReportDao;

	@Override
	public List<String> getMonthsList() {
		List<String> months = new ArrayList<String>();
		for (MonthEnum monthEnum : MonthEnum.values()) {
			months.add(monthEnum.toString());
		}
		return months;
	}

	@Override
	public List<String> getYearList() {
		List<String> yearsList = new ArrayList<String>();
		Integer minYear = type1ReportDao.findMinYear();
		Integer maxYear = type1ReportDao.findMaxYear();
		if (null == minYear) {
			minYear = (int) new Date().getYear();
		}
		if (null == maxYear) {
			maxYear = (int) new Date().getYear();
		}
		for (int i = minYear; i <= maxYear; i++) {
			yearsList.add(String.valueOf(i));
		}
		return yearsList;
	}

	@Override
	public List<FilesUploaded> getFileList(File file) {
		List<Integer> type2ReportIds = new ArrayList<Integer>();
		List<Type1Report> type1Reports = type1ReportDao.findByYearAndMonth(file.getYear(), file.getMonth());
		if (null == type1Reports || type1Reports.isEmpty()) {
			return new ArrayList<FilesUploaded>();
		}
		List<FilesUploaded> filesUploadeds = new ArrayList<FilesUploaded>();
		SimpleDateFormat callStartFormat = new SimpleDateFormat("dd MMM");
		FilesUploaded filesUploaded = null;
		for (Type1Report type1Report : type1Reports) {
			if (type1Report.getType2ReportId() != 0) {
				if (type2ReportIds.contains(type1Report.getType2ReportId()))
					continue;
				type2ReportIds.add(type1Report.getType2ReportId());
			}
			filesUploaded = new FilesUploaded();
			filesUploaded.setReportId(
					type1Report.getType2ReportId() != 0 ? type1Report.getType2ReportId() : type1Report.getId());
			filesUploaded.setType(type1Report.getType2ReportId() != 0 ? Type.Type2.toString() : Type.Type1.toString());
			filesUploaded.setUploadedBy(type1Report.getCreatedBy());
			filesUploaded.setUploadedTime(callStartFormat.format(type1Report.getCreatedTime().getTime()));
			filesUploaded.setFileName(type1Report.getReportName());
			filesUploadeds.add(filesUploaded);
		}
		return filesUploadeds;
	}

}
