package com.transform.cdr.service;

import java.util.List;

import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.Type1ProcessModel;

public interface Type1Service {

	public List getShipManualEntries(String shipName, int accountType) throws SuperException;

	public String saveReportData(Type1ProcessModel type1ProcessModel) throws SuperException;

	public String saveReportDataType2(Type1ProcessModel type1ProcessModel) throws SuperException;

	public String generateType1Report(int reportId) throws SuperException;

	public String generateType2Report(int reportId) throws SuperException;

	public String checkAllImsiExists(String filePath) throws SuperException;

	public List getShipManualEntriesType2(int accountType) throws SuperException;

	public String generateType1ActualReport(int reportId) throws SuperException;

	public String generateType2ActualReport(int reportId) throws SuperException;
}
