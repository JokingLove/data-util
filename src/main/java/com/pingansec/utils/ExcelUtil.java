package com.pingansec.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/***
 * huawei
 ***/
public class ExcelUtil {
	
	public static final String XLS = ".xls";
	public static final String XLSX = ".xlsx";
	public static final String CSV = ".csv";

	@SuppressWarnings("resource")
	public static final List<Object[]> getXls(InputStream inputStream) throws Exception {
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
		int numSheet = hssfWorkbook.getNumberOfSheets();
		List<Object[]> datas = new ArrayList<Object[]>();
		for (int i = 0; i < numSheet; i++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(i);
			if (hssfSheet == null) {
				continue;
			}
			
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				 if(hssfRow == null)
					 break;
				int cellNum = hssfRow.getLastCellNum();
				Object [] objects  = new Object[cellNum];
				for (int cellIndex = 0; cellIndex < cellNum; cellIndex++) {
					objects[cellIndex] = hssfRow.getCell(cellIndex);
				}
				datas.add(objects);
			}
		}
		return datas;
	}
	
	@SuppressWarnings("resource")
	public static final List<Object[]> getXlsx(InputStream inputStream) throws Exception {
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
		List<Object[]> datas = new ArrayList<Object[]>();
		 for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			      XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			      for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
						 XSSFRow hssfRow = xssfSheet.getRow(rowNum);
						 if(hssfRow == null)
							 break;
						 int cellNum = hssfRow.getLastCellNum();
						 Object [] objects  = new Object[cellNum];
						 for(int cellIndex= 0; cellIndex < cellNum; cellIndex++){
							 objects[cellIndex] = hssfRow.getCell(cellIndex);
						 }
						 datas.add(objects);
					 }
		 }
		 return datas;
	}
	
	public static final boolean isXls(String path){
		if(path.toLowerCase().endsWith(XLS)){
			return true;
		}
		return false;
	}
	public static final boolean isXlsx(String path){
		if(path.toLowerCase().endsWith(XLSX)){
			return true;
		}
		return false;
	}
	
	public static final boolean isCsv(String path) {
		if(path.toLowerCase().endsWith(CSV)) {
			return true;
		}
		return false;
	}
	public static final List<Object[]> getDataByFilePath(String filePath)throws Exception{
		if(StringUtils.isBlank(filePath))
			return null;
		if(isXls(filePath))
			return getXls(new FileInputStream(filePath));
		if(isXlsx(filePath))
			return getXlsx(new FileInputStream(filePath));
		return null;
	}
	
	/*public static final Map<String, List<Object[]>> getDataByDirPath(String dirPath) throws Exception {
		Map<String, List<Object[]>> datas = new HashMap<String, List<Object[]>>();
		if(StringUtils.isBlank(dirPath)) 
			return null;
		File dir = new File(dirPath);
		if(!dir.isDirectory())
			throw new IllegalArgumentException("不是文件夹：" + dirPath);
		File[] fileList = dir.listFiles((FileFilter) (item) -> {
			String name = item.getName();
			return name.indexOf(XLS) > -1 || name.indexOf(XLSX) > -1;
		});
		
		
		for(File file : fileList) {
			String fileName = file.getName();
			List<Object[]> data = new ArrayList<Object[]>();
			datas.put(fileName, data);
		}
		return datas;
	}*/
}
