package com.transform.cdr.service;

import java.util.List;

import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.Type2RateModel;
import com.transform.cdr.model.Type2ReportData;

public interface Type2RateService {

	public List<Type2RateModel> getUnlistedDescriptionRate(String reportPath) throws SuperException;

	public void saveType2Rate(List<Type2RateModel> rateList);
}
