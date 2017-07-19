package com.transform.cdr.service;

import java.util.List;

import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.Type3Model;

public interface Type3Service {

	public List findShipList(List<String> fileNames);

	public String saveType3ReportData(Type3Model type3Model) throws SuperException;

	public String generateType3Report(int reportId) throws SuperException;

}
