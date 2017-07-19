package com.transform.cdr.service;

import java.util.List;

import com.transform.cdr.model.File;
import com.transform.cdr.model.FilesUploaded;
import com.transform.cdr.model.Months;
import com.transform.cdr.model.Years;

public interface FileUploadService {

	public List<String> getMonthsList();

	public List<String> getYearList();

	public List<FilesUploaded> getFileList(File file);

}
